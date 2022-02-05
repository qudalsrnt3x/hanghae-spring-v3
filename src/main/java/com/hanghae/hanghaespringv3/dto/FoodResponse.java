package com.hanghae.hanghaespringv3.dto;

import com.hanghae.hanghaespringv3.model.Food;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodResponse {

    private Long id;
    private String name;
    private int price;

    public FoodResponse toEntity(Food food) {
        return FoodResponse.builder()
                .id(food.getId())
                .name(food.getName())
                .price(food.getPrice())
                .build();

    }
}
