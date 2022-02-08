package com.hanghae.hanghaespringv3.service;

import com.hanghae.hanghaespringv3.dto.LocationDto;
import com.hanghae.hanghaespringv3.dto.RestaurantRegisterDto;
import com.hanghae.hanghaespringv3.handler.exception.DeliveryFeeIsNot500UnitException;
import com.hanghae.hanghaespringv3.handler.exception.MinOrderPriceIsNot100UnitException;
import com.hanghae.hanghaespringv3.model.Restaurant;
import com.hanghae.hanghaespringv3.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
                .x(registerDto.getX())
                .y(registerDto.getY())
                .build();

        return restaurantRepository.save(restaurant);
    }

    @Transactional(readOnly = true)
    public List<Restaurant> getRestaurants(LocationDto locationDto) {

        List<Restaurant> restaurants = restaurantRepository.findAll();

        // "배달 받을 주소"에서 최대 3km 내에 있는 음식점들만 조회되어야 한다. (ex. [4,3])
        int distance = 3;

        if (locationDto == null)
            return restaurants;

        List<Point> pointList = new ArrayList<>();

        // 입력한 좌표 포함
        pointList.add(new Point(locationDto.getX(), locationDto.getY()));

        // 1키로 범위
        getDistance(locationDto.getX(), locationDto.getY(), distance - 2, pointList);

        // 2키로 범위
        getDistance(locationDto.getX(), locationDto.getY(), distance - 1, pointList);

        // 3키로 범위
        getDistance(locationDto.getX(), locationDto.getY(), distance, pointList);

        List<Restaurant> findRestaurants = new ArrayList<>();

        // 배달 가능한 음식점만 리스트에 추가
        for (Restaurant restaurant : restaurants) {
            if (pointList.contains(new Point(restaurant.getX(), restaurant.getY()))) {
                findRestaurants.add(restaurant);
            }
        }

        return findRestaurants;
    }

    private void getDistance(int x, int y, int distance, List<Point> pointList) {
        for (int i = 1; i <= distance; i++) {

            // 1. 위에서 왼쪽으로
            pointList.add(new Point(x - i, y - distance + i));

            // 2. 왼쪽에서 아래쪽으로
            pointList.add(new Point(x - distance + i, y + i));

            // 3. 아래쪽에서 오른쪽으로
            pointList.add(new Point(x + i, y + distance - i));

            // 4. 오른쪽에서 윈쪽으로
            pointList.add(new Point(x + distance - i, y - i));

        }
    }
}
