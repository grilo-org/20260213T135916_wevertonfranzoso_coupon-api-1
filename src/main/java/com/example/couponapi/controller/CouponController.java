package com.example.couponapi.controller;

import com.example.couponapi.service.CouponApplicationService;
import com.example.couponapi.domain.Coupon;
import com.example.couponapi.request.CreateCouponRequest;
import com.example.couponapi.response.CouponResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponApplicationService service;

    public CouponController(CouponApplicationService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new coupon")
    @PostMapping
    public ResponseEntity<CouponResponse> create(@RequestBody CreateCouponRequest request) {
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

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @Operation(summary = "Delete a coupon")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}