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
    List<CropPrice> findByCropIdAndPriceDateBetweenOrderByPriceDate(Long cropId, LocalDate start, LocalDate end);
    @Query("""
        SELECT EXTRACT(MONTH FROM cp.priceDate), AVG(cp.avgPrice)
        FROM CropPrice cp
        WHERE cp.crop.id = :cropId
        AND EXTRACT(YEAR FROM cp.priceDate) = EXTRACT(YEAR FROM CURRENT_DATE)
        GROUP BY EXTRACT(MONTH FROM cp.priceDate)
        ORDER BY EXTRACT(MONTH FROM cp.priceDate)
    """)
    List<Object[]> getMonthlyAverage(Long cropId);


    @Query("""
        SELECT EXTRACT(YEAR FROM cp.priceDate), AVG(cp.avgPrice)
        FROM CropPrice cp
        WHERE cp.crop.id = :cropId
        AND cp.priceDate >= CURRENT_DATE - 5 YEAR
        GROUP BY EXTRACT(YEAR FROM cp.priceDate)
        ORDER BY EXTRACT(YEAR FROM cp.priceDate)
    """)
    List<Object[]> getYearlyAverage(Long cropId);
}
