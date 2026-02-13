package com.example.couponapi.service;

import com.example.couponapi.domain.Coupon;
import com.example.couponapi.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class CouponApplicationService {

    private final CouponRepository repository;

    public CouponApplicationService(CouponRepository repository) {
        this.repository = repository;
    }

    public Coupon create(Coupon coupon) {
        return repository.save(coupon);
    }

    public void delete(Long id) {
        Coupon coupon = repository.findById(id).orElseThrow();
        coupon.delete();
        repository.save(coupon);
    }
}

