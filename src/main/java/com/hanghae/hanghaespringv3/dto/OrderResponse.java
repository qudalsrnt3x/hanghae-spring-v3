package com.hanghae.hanghaespringv3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OrderResponse {

    private String restaurantName;

    private List<FoodOrderResponse> foods;

    private int deliveryFee;

    private int totalPrice;
}
