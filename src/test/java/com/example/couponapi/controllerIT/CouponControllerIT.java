package com.example.couponapi.controllerIT;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CouponControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateCouponSuccessfully() throws Exception {
        String requestBody = """
            {
              "code": "DESC10",
              "discountValue": 10.0,
              "expirationDate": "2030-12-31"
            }
            """;

        mockMvc.perform(
                        post("/coupons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isCreated());
    }
}