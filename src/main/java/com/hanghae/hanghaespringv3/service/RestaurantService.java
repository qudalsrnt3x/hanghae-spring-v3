package com.hanghae.hanghaespringv3.service;

import com.hanghae.hanghaespringv3.dto.RestaurantRegisterDto;
import com.hanghae.hanghaespringv3.model.Restaurant;
import com.hanghae.hanghaespringv3.model.RestaurantRepository;
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
