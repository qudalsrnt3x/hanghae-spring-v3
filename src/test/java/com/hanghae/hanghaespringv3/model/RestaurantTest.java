package com.hanghae.hanghaespringv3.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestaurantTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("배달 주소 입력 - 정상")
    void registerXY() throws JsonProcessingException {
        // given
        RestaurantDto restaurantDto = RestaurantDto.builder()
                .id(null)
                .name("버거킹")
                .minOrderPrice(1_000)
                .deliveryFee(0)
                .x(4)
                .y(3)
                .build();

        String requestBody = mapper.writeValueAsString(restaurantDto);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        // when
        ResponseEntity<RestaurantDto> response = restTemplate.postForEntity(
                "/restaurant/register",
                request,
                RestaurantDto.class);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RestaurantDto restaurantResponse = response.getBody();
        assertNotNull(restaurantResponse); // 널이 아니면 밑에 테스트에 널체크 필요 없는듯
        assertEquals(4, restaurantResponse.x);
        assertEquals(3, restaurantResponse.y);

    }


    @Getter
    @Setter
    @Builder
    static class RestaurantDto {
        private Long id;
        private String name;
        private int minOrderPrice;
        private int deliveryFee;
        private int x;
        private int y;
    }

}