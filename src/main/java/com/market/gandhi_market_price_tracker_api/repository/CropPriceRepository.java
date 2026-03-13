package com.market.gandhi_market_price_tracker_api.repository;

import com.market.gandhi_market_price_tracker_api.entity.CropPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CropPriceRepository extends JpaRepository<CropPrice,Long> {
    @Query("""
         SELECT p
         FROM CropPrice p
         JOIN FETCH p.crop
         WHERE p.priceDate >= :date
         ORDER BY p.crop.id, p.priceDate DESC
 """)
    List<CropPrice> findByPriceDateAfter(LocalDate date);
    Optional<CropPrice> findByCropIdAndPriceDate(Long cropId, LocalDate date);
}
