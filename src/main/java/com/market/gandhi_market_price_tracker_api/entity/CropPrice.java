package com.market.gandhi_market_price_tracker_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="crop_prices")
public class CropPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate priceDate;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer avgPrice;

    @ManyToOne
    @JoinColumn(name="crop_id")
    private Crop crop;
}
