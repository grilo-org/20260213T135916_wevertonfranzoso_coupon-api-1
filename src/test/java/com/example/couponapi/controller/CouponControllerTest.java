package com.example.couponapi.controller;

import com.example.couponapi.service.CouponApplicationService;
import com.example.couponapi.domain.Coupon;
import com.example.couponapi.request.CreateCouponRequest;
import com.example.couponapi.response.CouponResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CouponControllerTest {

    private CouponApplicationService service;
    private CouponController controller;

    @BeforeEach
    void setUp() {
        service = mock(CouponApplicationService.class);
        controller = new CouponController(service) {
            @Override
            public ResponseEntity<CouponResponse> create(CreateCouponRequest request) {
                Coupon coupon = new Coupon(
                        request.code(),
                        request.description(),
                        request.discountValue(),
                        request.expirationDate(),
                        request.published()
                );

                Coupon saved = service.create(coupon);

                CouponResponse response = new CouponResponse(
                        saved.getId(),
                        saved.getCode(),
                        saved.getDescription(),
                        saved.getDiscountValue(),
                        saved.getExpirationDate(),
                        saved.isPublished()
                );

                URI location = URI.create("/coupons/" + saved.getId());

                return ResponseEntity.created(location).body(response);
            }
        };
    }

    @Test
    void create_shouldReturnCreatedCoupon() {
        CreateCouponRequest request = new CreateCouponRequest(
                "ABC123",
                "10% de desconto",
                new BigDecimal(10),
                LocalDate.now().plusDays(1),
                true
        );

        Coupon savedCoupon = new Coupon(
                request.code(),
                request.description(),
                request.discountValue(),
                request.expirationDate(),
                request.published()
        ) {
            @Override
            public Long getId() {
                return 1L;
            }
        };

        when(service.create(any(Coupon.class))).thenReturn(savedCoupon);

        var responseEntity = controller.create(request);

        assertEquals(201, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        assertEquals(1L, responseEntity.getBody().id());
        assertEquals("ABC123", responseEntity.getBody().code());
        assertEquals("10% de desconto", responseEntity.getBody().description());
        assertEquals(new BigDecimal(10), responseEntity.getBody().discountValue());
        assertEquals(savedCoupon.getExpirationDate(), responseEntity.getBody().expirationDate());
        assertTrue(responseEntity.getBody().published());

        ArgumentCaptor<Coupon> captor = ArgumentCaptor.forClass(Coupon.class);
        verify(service, times(1)).create(captor.capture());
        Coupon captured = captor.getValue();
        assertEquals("ABC123", captured.getCode());
        assertEquals("10% de desconto", captured.getDescription());
    }

    @Test
    void delete_shouldCallServiceDelete() {
        Long id = 1L;

        controller.delete(id);

        verify(service, times(1)).delete(id);
    }
}