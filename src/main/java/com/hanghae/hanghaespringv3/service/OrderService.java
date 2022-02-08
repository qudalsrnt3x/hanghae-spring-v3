package com.hanghae.hanghaespringv3.service;

import com.hanghae.hanghaespringv3.dto.*;
import com.hanghae.hanghaespringv3.handler.exception.NotFoundException;
import com.hanghae.hanghaespringv3.handler.exception.TotalPriceIsNotValidationException;
import com.hanghae.hanghaespringv3.model.Food;
import com.hanghae.hanghaespringv3.model.FoodOrder;
import com.hanghae.hanghaespringv3.model.Order;
import com.hanghae.hanghaespringv3.model.Restaurant;
import com.hanghae.hanghaespringv3.repository.FoodOrderRepository;
import com.hanghae.hanghaespringv3.repository.FoodRepository;
import com.hanghae.hanghaespringv3.repository.OrderRepository;
import com.hanghae.hanghaespringv3.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository repository;
    private final FoodRepository foodRepository;
    private final FoodOrderRepository foodOrderRepository;

    @Transactional
    public OrderResponse createOrder(OrderRequestDto orderRequestDto) {

        // 음식점 entity 가져오기
        Restaurant restaurantEntity = repository.findById(orderRequestDto.getRestaurantId()).orElseThrow(
                () -> new NotFoundException("해당 레스토랑이 존재하지 않습니다.")
        );

        // 주문 테이블에 음식점 entity 넣기
        Order order = Order.builder()
                .restaurant(restaurantEntity)
                .build();

        // 저장
        orderRepository.save(order);

        List<FoodOrderResponse> foodOrderResponses = new ArrayList<>();
        int totalPrice = 0;

        // 음식 주문 정보 리스트 돌면서
        for (FoodOrderRequestDto food : orderRequestDto.getFoods()) {
            Food findFood = foodRepository.findById(food.getId()).orElseThrow(
                    () -> new NotFoundException("해당하는 음식이 없습니다.")
            );

            // FoodOrder 생성
            FoodOrder foodOrder = FoodOrder.builder()
                    .food(findFood)
                    .order(order)
                    .quantity(food.getQuantity())
                    .build();

            foodOrderRepository.save(foodOrder);

            // 주문 음식 가격 계산
            int price = food.getQuantity() * findFood.getPrice();

            // 응답 response에 담아주기
            FoodOrderResponse foodOrderResponse = FoodOrderResponse.builder()
                    .name(findFood.getName())
                    .quantity(foodOrder.getQuantity())
                    .price(price)
                    .build();

            foodOrderResponses.add(foodOrderResponse);

            totalPrice += price;
        }

        if (restaurantEntity.getMinOrderPrice() > totalPrice)
            throw new TotalPriceIsNotValidationException("주문 음식 가격의 합이 최소주문 가격보다 높아야 합니다.");

        // 거리별 배달비 산정 후 총 배달비 구하기
        int distanceFee = 0;
        if (orderRequestDto.getLocationDto() != null)
            distanceFee = getDistanceFee(orderRequestDto.getLocationDto(), restaurantEntity);
        //System.out.println("distanceFee = " + distanceFee);

        int totalDeliveryFee = restaurantEntity.getDeliveryFee() + distanceFee;

        order.setTotalPrice(totalPrice + totalDeliveryFee);

        return OrderResponse.builder()
                .restaurantName(restaurantEntity.getName())
                .foods(foodOrderResponses)
                .deliveryFee(restaurantEntity.getDeliveryFee())
                .totalPrice(totalPrice+ totalDeliveryFee)
                .build();
    }


    @Transactional(readOnly = true)
    public List<OrderResponse> getOrders() {

        List<OrderResponse> orderResponseList = new ArrayList<>();
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            Restaurant restaurant = order.getRestaurant();

            List<FoodOrder> foodOrderList = foodOrderRepository.findAllByOrder(order);
            List<FoodOrderResponse> foodOrderResponses = createFoodOrder(foodOrderList);

            OrderResponse orderResponse = OrderResponse.builder()
                    .restaurantName(restaurant.getName())
                    .foods(foodOrderResponses)
                    .deliveryFee(restaurant.getDeliveryFee())
                    .totalPrice(order.getTotalPrice())
                    .build();

            orderResponseList.add(orderResponse);
        }

        return orderResponseList;
    }

    private List<FoodOrderResponse> createFoodOrder(List<FoodOrder> foodOrderList) {

        ArrayList<FoodOrderResponse> foodOrderResponses = new ArrayList<>();

        for (FoodOrder foodOrder : foodOrderList) {

            FoodOrderResponse foodOrderResponse = FoodOrderResponse.builder()
                    .name(foodOrder.getFood().getName())
                    .quantity(foodOrder.getQuantity())
                    .price(foodOrder.getFood().getPrice() * foodOrder.getQuantity())
                    .build();

            foodOrderResponses.add(foodOrderResponse);
        }

        return foodOrderResponses;
    }


    private int getDistanceFee(LocationDto locationDto, Restaurant restaurantEntity) {

        List<Point> pointList = new ArrayList<>();

        int distance = 3;
        int distanceFee = 0;

        // 배달 받을 주소 기준 1~3키로 내에 음식점이 있으면 해당 음식점과의 거리 산정 후 리턴
        // 음식점의 좌표
        Point restaurantLocation =
                new Point(restaurantEntity.getX(), restaurantEntity.getY());

        // 1키로 범위
        getDistance(locationDto.getX(), locationDto.getY(), distance - 2, pointList);

        if (pointList.contains(restaurantLocation)) {
            distanceFee = 500;
            return distanceFee;
        }

        // 2키로 범위
        getDistance(locationDto.getX(), locationDto.getY(), distance - 1, pointList);

        if (pointList.contains(restaurantLocation)) {
            distanceFee = 1000;
            return distanceFee;
        }

        // 3키로 범위
        getDistance(locationDto.getX(), locationDto.getY(), distance, pointList);

        if (pointList.contains(restaurantLocation)) {
            distanceFee = 1500;
            return distanceFee;
        }

        return distanceFee;
    }


    private void getDistance(int x, int y, int distance, List<Point> pointList) {

        for (int i = 1; i <= distance; i++) {

            // 1. 위에서 왼쪽으로
            pointList.add(new Point(x - i, y - distance + i));

            // 2. 왼쪽에서 아래쪽으로
            pointList.add(new Point(x - distance + i, y + i));

            // 3. 아래쪽에서 오른쪽으로
            pointList.add(new Point(x + i, y + distance - i));

            // 4. 오른쪽에서 윈쪽으로
            pointList.add(new Point(x + distance - i, y - i));

        }
    }
}
