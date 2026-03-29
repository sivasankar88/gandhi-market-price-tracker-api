package com.market.gandhi_market_price_tracker_api.service;

import com.market.gandhi_market_price_tracker_api.entity.Crop;
import com.market.gandhi_market_price_tracker_api.repository.CropPriceRepository;
import com.market.gandhi_market_price_tracker_api.repository.CropRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderScheduler {
    private EmailService emailService;
    private CropRepository cropRepository;
    private CropPriceRepository cropPriceRepository;
    public ReminderScheduler(EmailService emailService,CropRepository cropRepository,CropPriceRepository cropPriceRepository) {
        this.emailService = emailService;
        this.cropPriceRepository = cropPriceRepository;
        this.cropRepository = cropRepository;
    }

    @Scheduled(cron = "0 0 19 * * *", zone="Asia/Kolkata")
    public void sendDailyReminder() {
        emailService.remainderMail();
        System.out.println("Mail sent");
    }

    @Scheduled(cron = "0 0 19 * * *", zone="Asia/Kolkata")
    public void notifyMissingPrices() {
        LocalDate today = LocalDate.now();
        List<Crop> allCrops = cropRepository.findAll();
        List<Long> updatedCropsID = cropPriceRepository.findCropIdsWithPriceForDate(today);
        List<String> missingCrops = allCrops.stream()
                .filter(crop -> !updatedCropsID.contains(crop.getId()))
                .map(Crop::getName)
                .collect(Collectors.toList());
        if(missingCrops.isEmpty()) {
            System.out.println("All prices updated for today. No emial sent.");
            return;
        }
        emailService.sendMissingPriceAlert(missingCrops);
        System.out.println("Alert sent for "+missingCrops.size()+" missing crops.");
    }
}
