package com.hanghae.hanghaespringv3.repository;

import com.hanghae.hanghaespringv3.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findAllByRestaurantId(Long restaurantId);

    @Query("select name from Food where restaurant.id = :restaurantId")
    List<String> findNameByRestaurantId(Long restaurantId);
}
