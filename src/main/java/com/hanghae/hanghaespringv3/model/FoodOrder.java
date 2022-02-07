package com.hanghae.hanghaespringv3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "foodId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Food food;

    @JoinColumn(name = "order_table_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    private int quantity;
}
