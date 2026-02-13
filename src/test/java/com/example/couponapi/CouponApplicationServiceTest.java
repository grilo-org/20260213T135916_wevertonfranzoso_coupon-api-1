package com.example.couponapi;

import com.example.couponapi.domain.Coupon;
import com.example.couponapi.repository.CouponRepository;
import com.example.couponapi.service.CouponApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CouponApplicationServiceTest {

    private CouponRepository repository;
    private CouponApplicationService service;

    @BeforeEach
    void setUp() {
        repository = mock(CouponRepository.class);
        service = new CouponApplicationService(repository);
    }

    @Test
    void create_shouldSaveAndReturnCoupon() {
        Coupon coupon = new Coupon(
                "ABC123",
                "Teste",
                BigDecimal.valueOf(10),
                LocalDate.now().plusDays(1),
                true
        );

        when(repository.save(any(Coupon.class))).thenReturn(coupon);

        Coupon result = service.create(coupon);

        assertEquals(coupon, result);
        verify(repository, times(1)).save(coupon);
    }

    @Test
    void delete_shouldMarkCouponAsDeletedAndSave() {
        Coupon coupon = new Coupon(
                "ABC123",
                "Teste",
                BigDecimal.valueOf(10),
                LocalDate.now().plusDays(1),
                true
        );

        when(repository.findById(1L)).thenReturn(Optional.of(coupon));
        when(repository.save(any(Coupon.class))).thenReturn(coupon);

        service.delete(1L);

        assertTrue(coupon.isDeleted());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(coupon);
    }

    @Test
    void delete_shouldThrow_whenCouponNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.delete(1L));

        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    void delete_shouldThrow_whenCouponAlreadyDeleted() {
        Coupon coupon = new Coupon(
                "ABC123",
                "Teste",
                BigDecimal.valueOf(10),
                LocalDate.now().plusDays(1),
                true
        );

        coupon.delete();

        when(repository.findById(1L)).thenReturn(Optional.of(coupon));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> service.delete(1L));
        assertEquals("Coupon already deleted", exception.getMessage());

        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any());
    }
}
