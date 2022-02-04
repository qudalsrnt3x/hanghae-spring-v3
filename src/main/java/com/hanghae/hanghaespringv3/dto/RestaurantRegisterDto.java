package com.hanghae.hanghaespringv3.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RestaurantRegisterDto {

    private String name;

    private int minOrderPrice;

    private int deliveryFee;
}
