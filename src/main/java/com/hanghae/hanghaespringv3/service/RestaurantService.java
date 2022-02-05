package com.hanghae.hanghaespringv3.service;

import com.hanghae.hanghaespringv3.dto.RestaurantRegisterDto;
import com.hanghae.hanghaespringv3.handler.exception.DeliveryFeeIsNot500UnitException;
import com.hanghae.hanghaespringv3.handler.exception.MinOrderPriceIsNot100UnitException;
import com.hanghae.hanghaespringv3.model.Restaurant;
import com.hanghae.hanghaespringv3.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Restaurant register(RestaurantRegisterDto registerDto) {

        if (registerDto.getMinOrderPrice() % 100 != 0)
            throw new MinOrderPriceIsNot100UnitException("주문 가격은 100원 단위로만 입력할 수 있습니다.");
        else if (registerDto.getDeliveryFee() % 500 != 0)
            throw new DeliveryFeeIsNot500UnitException("배달비는 500원 단위로만 입력할 수 있습니다.");


        Restaurant restaurant = Restaurant.builder()
                .name(registerDto.getName())
                .minOrderPrice(registerDto.getMinOrderPrice())
                .deliveryFee(registerDto.getDeliveryFee())
                .build();

        return restaurantRepository.save(restaurant);
    }

    @Transactional(readOnly = true)
    public List<Restaurant> getRestaurants() {

        return restaurantRepository.findAll();
    }
}
