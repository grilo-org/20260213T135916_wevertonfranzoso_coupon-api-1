package com.example.couponapi.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateCouponRequest(
        String code,
        String description,
        BigDecimal discountValue,
        LocalDate expirationDate,
        boolean published
) {
}
