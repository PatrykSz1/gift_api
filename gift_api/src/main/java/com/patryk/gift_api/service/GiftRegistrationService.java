package com.patryk.gift_api.service;

import com.patryk.gift_api.model.Gift;
import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.repository.KidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@Component
@RequiredArgsConstructor
public class GiftRegistrationService {

    private final KidRepository kidRepository;

//    @Scheduled(cron = "*/30 * * * * *")
    public void processExpensiveGifts() {
        List<Kid> kidsWithExpensiveGifts = kidRepository.findKidsWithGiftsWhichPriceOver100(new BigDecimal(100));

        kidsWithExpensiveGifts.forEach(kid -> {
            List<Gift> expensiveGifts = kid.getGifts().stream()
                    .filter(g -> g.getPrice().compareTo(new BigDecimal(100)) > 0)
                    .toList();
            log.info("Dziecko {} {} ma drogie prezenty: {}",
                    kid.getFirstName(),
                    kid.getLastName(),
                    expensiveGifts);
        });
    }
}
