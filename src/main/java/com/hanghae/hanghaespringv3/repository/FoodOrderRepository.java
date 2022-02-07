package com.hanghae.hanghaespringv3.repository;

import com.hanghae.hanghaespringv3.model.FoodOrder;
import com.hanghae.hanghaespringv3.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
    List<FoodOrder> findAllByOrder(Order order);
}
