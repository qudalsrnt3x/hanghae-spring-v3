package com.hanghae.hanghaespringv3.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

@Getter @Setter
public class OrderRequestDto {

    private Long restaurantId;

    @Valid
    private List<FoodOrderRequestDto> foods;
}
