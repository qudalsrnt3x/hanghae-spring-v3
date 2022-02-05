package com.hanghae.hanghaespringv3.service;

import com.hanghae.hanghaespringv3.dto.FoodRegisterDto;
import com.hanghae.hanghaespringv3.dto.FoodResponse;
import com.hanghae.hanghaespringv3.handler.exception.FoodNameDuplicateException;
import com.hanghae.hanghaespringv3.handler.exception.MinOrderPriceIsNot100UnitException;
import com.hanghae.hanghaespringv3.handler.exception.NotFoundException;
import com.hanghae.hanghaespringv3.handler.exception.PriceIsNot100UnitException;
import com.hanghae.hanghaespringv3.model.Food;
import com.hanghae.hanghaespringv3.model.FoodRepository;
import com.hanghae.hanghaespringv3.model.Restaurant;
import com.hanghae.hanghaespringv3.model.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public List<Food> registerFood(Long restaurantId, List<FoodRegisterDto> foodRegisterDto) {

        Restaurant findRestaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new NotFoundException("해당하는 id가 없습니다.")
        );

        List<String> savedFoodNameListByRestaurantId =
                foodRepository.findNameByRestaurantId(restaurantId);

        if(!isDuplicateFoodName(foodRegisterDto))
            throw new FoodNameDuplicateException("중복된 음식 이름입니다.");


        // 음식점에 음식 등록
        List<Food> foodList = new ArrayList<>();

        for (FoodRegisterDto registerDto : foodRegisterDto) {

            String registerDtoFoodName = registerDto.getName();

            if (!checkFoodName(savedFoodNameListByRestaurantId, registerDtoFoodName))
                throw new FoodNameDuplicateException("이미 등록되어 있는 음식입니다.");
            else if (registerDto.getPrice() % 100 != 0)
                throw new PriceIsNot100UnitException("음식 가격은 100원 단위로만 입력할 수 있습니다.");

            Food food = Food.builder()
                    .name(registerDto.getName())
                    .price(registerDto.getPrice())
                    .restaurant(findRestaurant)
                    .build();

            Food saveFood = foodRepository.save(food);

            foodList.add(saveFood);
        }

        return foodList;
    }



    @Transactional(readOnly = true)
    public List<FoodResponse> findMenuByRestaurantId(Long restaurantId) {

        // 음식점에 해당하는 음식리스트 가져오기
        List<Food> foodList = foodRepository.findAllByRestaurantId(restaurantId);


        ArrayList<FoodResponse> foodResponses = new ArrayList<>();
        for (Food food : foodList) {
            FoodResponse foodResponse = new FoodResponse().toEntity(food);
            foodResponses.add(foodResponse); // TODO 정적팩토리메서드 패턴 사용해서 리팩토링 해보자.
        }


        return foodResponses;
    }


    // 중복 음식 등록했는지 체크
    private boolean isDuplicateFoodName(List<FoodRegisterDto> foodRegisterDto) {
        return foodRegisterDto.stream()
                .map(FoodRegisterDto::getName)
                .allMatch(new HashSet<>()::add);
    }

    // 음식점에 이미 등록된 음식인지 체크
    private boolean checkFoodName(List<String> savedFoodNameListByRestaurantId,
                                  String registerDtoFoodName) {
        for (String foodName : savedFoodNameListByRestaurantId) {
            if (foodName.equals(registerDtoFoodName))
                return false;
        }
        return true;
    }
}
