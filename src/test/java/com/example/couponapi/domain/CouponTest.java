package com.example.couponapi.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CouponTest {

    @Test
    void constructor_shouldCreateCoupon_whenValidData() {
        Coupon coupon = new Coupon(
                "AB@C123!",
                "Desconto de teste",
                BigDecimal.valueOf(10),
                LocalDate.now().plusDays(1),
                true
        );

        assertEquals("ABC123", coupon.getCode());
        assertEquals("Desconto de teste", coupon.getDescription());
        assertEquals(BigDecimal.valueOf(10), coupon.getDiscountValue());
        assertEquals(LocalDate.now().plusDays(1), coupon.getExpirationDate());
        assertTrue(coupon.isPublished());
        assertFalse(coupon.isDeleted());
    }

    @Test
    void constructor_shouldThrow_whenExpirationDateInPast() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Coupon(
                    "ABC123",
                    "Teste",
                    BigDecimal.valueOf(10),
                    LocalDate.now().minusDays(1),
                    true
            );
        });

        assertEquals("Expiration date cannot be in the past", exception.getMessage());
    }

    @Test
    void constructor_shouldThrow_whenDiscountLessThanMinimum() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Coupon(
                    "ABC123",
                    "Teste",
                    BigDecimal.valueOf(0.1),
                    LocalDate.now().plusDays(1),
                    true
            );
        });

        assertEquals("Discount must be at least 0.5", exception.getMessage());
    }

    @Test
    void constructor_shouldThrow_whenCodeIsNullOrBlank() {
        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
            new Coupon(
                    null,
                    "Teste",
                    BigDecimal.valueOf(1),
                    LocalDate.now().plusDays(1),
                    true
            );
        });
        assertEquals("Code is required", ex1.getMessage());

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> {
            new Coupon(
                    "   ",
                    "Teste",
                    BigDecimal.valueOf(1),
                    LocalDate.now().plusDays(1),
                    true
            );
        });
        assertEquals("Code is required", ex2.getMessage());
    }

    @Test
    void delete_shouldMarkCouponAsDeleted() {
        Coupon coupon = new Coupon(
                "ABC123",
                "Teste",
                BigDecimal.valueOf(10),
                LocalDate.now().plusDays(1),
                true
        );

        coupon.delete();
        assertTrue(coupon.isDeleted());
    }

    @Test
    void delete_shouldThrow_whenAlreadyDeleted() {
        Coupon coupon = new Coupon(
                "ABC123",
                "Teste",
                BigDecimal.valueOf(10),
                LocalDate.now().plusDays(1),
                true
        );

        coupon.delete();

        IllegalStateException exception = assertThrows(IllegalStateException.class, coupon::delete);
        assertEquals("Coupon already deleted", exception.getMessage());
    }

    @Test
    void normalizeCode_shouldUpperCaseAndTrimTo6() {
        Coupon coupon = new Coupon(
                "a!b@c#1$2%3",
                "Teste",
                BigDecimal.valueOf(10),
                LocalDate.now().plusDays(1),
                true
        );

        assertEquals("ABC123", coupon.getCode());
    }
}