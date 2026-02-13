
package com.example.couponapi.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String description;
    private BigDecimal discountValue;
    private LocalDate expirationDate;
    private boolean published;
    private boolean deleted = false;

    protected Coupon() {
    }

    public Coupon(
            String code,
            String description,
            BigDecimal discountValue,
            LocalDate expirationDate,
            boolean published
    ) {
        validate(code, discountValue, expirationDate);
        this.code = normalizeCode(code);
        this.description = description;
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        this.published = published;
    }

    private void validate(String code, BigDecimal discountValue, LocalDate expirationDate) {
        if (expirationDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expiration date cannot be in the past");
        }

        if (discountValue.compareTo(BigDecimal.valueOf(0.5)) < 0) {
            throw new IllegalArgumentException("Discount must be at least 0.5");
        }

        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code is required");
        }
    }

    private String normalizeCode(String code) {
        return code.replaceAll("[^a-zA-Z0-9]", "")
                .substring(0, Math.min(6, code.length()))
                .toUpperCase();
    }

    public void delete() {
        if (this.deleted) {
            throw new IllegalStateException("Coupon already deleted");
        }
        this.deleted = true;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public boolean isPublished() {
        return published;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
