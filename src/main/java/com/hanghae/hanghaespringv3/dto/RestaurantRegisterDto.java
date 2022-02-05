package com.hanghae.hanghaespringv3.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class RestaurantRegisterDto {

    private String name;

    @Range(min = 1000, max = 100000, message = "최소 주문 금액은 1000원 ~ 100000원 사이만 지정할 수 있습니다.")
    private int minOrderPrice;

    @Range(max = 10000, message = "배달비는 0원 ~ 10000원 사이만 지정할 수 있습니다.")
    private int deliveryFee;
}
