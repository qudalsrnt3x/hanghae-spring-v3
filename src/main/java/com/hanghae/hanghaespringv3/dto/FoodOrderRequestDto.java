package com.hanghae.hanghaespringv3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class FoodOrderRequestDto {

    private Long id;

    @Range(min = 1, max = 100, message = "수량은 1개 ~ 100개 사이만 가능합니다.")
    private int quantity;
}
