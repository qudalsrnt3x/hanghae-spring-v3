package com.hanghae.hanghaespringv3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodOrderResponse {

    private String name;
    private int quantity;
    private int price;
}
