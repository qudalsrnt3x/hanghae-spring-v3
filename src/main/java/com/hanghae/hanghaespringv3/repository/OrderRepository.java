package com.hanghae.hanghaespringv3.repository;

import com.hanghae.hanghaespringv3.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
