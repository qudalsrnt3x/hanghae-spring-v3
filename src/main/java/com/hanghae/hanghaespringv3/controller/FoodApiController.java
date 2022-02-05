package com.hanghae.hanghaespringv3.controller;

import com.hanghae.hanghaespringv3.dto.FoodRegisterDto;
import com.hanghae.hanghaespringv3.dto.FoodResponse;
import com.hanghae.hanghaespringv3.model.Food;
import com.hanghae.hanghaespringv3.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FoodApiController {

    private final FoodService foodService;

    /**
     * 음식 등록
     */
    @PostMapping("/restaurant/{restaurantId}/food/register")
    public ResponseEntity<Void> registerFood(@PathVariable Long restaurantId,
                                              @RequestBody @Valid List<FoodRegisterDto> foodRegisterDto) {

        foodService.registerFood(restaurantId, foodRegisterDto);

        return ResponseEntity.ok().build();
    }

    /**
     * 메뉴판 조회
     */
    @GetMapping("/restaurant/{restaurantId}/foods")
    public ResponseEntity<List<FoodResponse>> findMenu(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(foodService.findMenuByRestaurantId(restaurantId));
    }
}
