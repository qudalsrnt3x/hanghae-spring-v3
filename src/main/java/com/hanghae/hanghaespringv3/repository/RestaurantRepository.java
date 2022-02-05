package com.hanghae.hanghaespringv3.repository;

import com.hanghae.hanghaespringv3.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
