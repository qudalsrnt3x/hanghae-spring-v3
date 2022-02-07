package com.hanghae.hanghaespringv3.controller;

import com.hanghae.hanghaespringv3.dto.RestaurantRegisterDto;
import com.hanghae.hanghaespringv3.model.Restaurant;
import com.hanghae.hanghaespringv3.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RestaurantApiController {

    private final RestaurantService restaurantService;

    /**
     * 음식점 등록
     */
    @PostMapping("/restaurant/register")
    public ResponseEntity<Restaurant> registerRestaurant(
            @RequestBody @Valid RestaurantRegisterDto registerDto) {

        Restaurant restaurant = restaurantService.register(registerDto);

        return ResponseEntity.ok(restaurant);
    }

    /**
     * 음식점 조회
     */
    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getRestaurants() {

        List<Restaurant> restaurants = restaurantService.getRestaurants();

        return ResponseEntity.ok(restaurants);
    }
}
