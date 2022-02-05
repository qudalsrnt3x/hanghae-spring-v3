package com.hanghae.hanghaespringv3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Range(min = 100, max = 1000000, message = "가격은 100원 ~ 1000000원 사이만 입력할 수 있습니다.")
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;
}
