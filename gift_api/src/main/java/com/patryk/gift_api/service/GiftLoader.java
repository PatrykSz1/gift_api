package com.patryk.gift_api.service;

import com.patryk.gift_api.model.Kid;
import com.patryk.gift_api.repository.KidRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class GiftLoader {

    private final KidRepository kidRepository;
    private final JdbcTemplate jdbcTemplate;

    private static final int BATCH_SIZE = 1000;
    private static final String INSERT_QUERY = "INSERT INTO gift (name, price, kid_id, version) VALUES %s";
    private static final String SINGLE_GIFT_PARAMS = "('%s', %.2f, %d,%d),";


    public void loadGifts(int totalGifts) {
        List<Kid> kids = kidRepository.findAll();
        if (kids.isEmpty()) {
            throw new EntityNotFoundException("Brak dzieci w bazie! Najpierw dodaj Kid, aby można było przypisać prezenty.");
        }

        Random random = new Random();
        StringBuilder params = new StringBuilder();
        long totalStart = System.currentTimeMillis();
        long batchStart = System.currentTimeMillis();
        int counter = 0;

        for (int i = 1; i <= totalGifts; i++) {
            Kid randomKid = kids.get(random.nextInt(kids.size()));
            int kidId = randomKid.getId();

            String giftName = "RandomGift_" + i;
            double price = 50 + (500 * random.nextDouble());
            int defaultVersion = 0;


            params.append(String.format(Locale.US, SINGLE_GIFT_PARAMS, giftName, price, kidId, defaultVersion));
            counter++;

            if (counter % BATCH_SIZE == 0) {
                params.deleteCharAt(params.length() - 1);
                String sql = String.format(INSERT_QUERY, params.toString());
                jdbcTemplate.update(sql);


                long now = System.currentTimeMillis();
                log.info("Processed {} records in: {} ms", BATCH_SIZE, now - batchStart);
                params.setLength(0);
                batchStart = System.currentTimeMillis();
            }
        }

        if (params.length() > 0) {
            params.deleteCharAt(params.length() - 1);
            String sql = String.format(INSERT_QUERY, params.toString());
            jdbcTemplate.update(sql);
            int leftover = counter % BATCH_SIZE;
            if (leftover == 0) leftover = BATCH_SIZE;
            log.info("Processed leftover {} records", leftover);
        }

        long totalTime = System.currentTimeMillis() - totalStart;
        log.info("✅ Finished inserting {} gifts in {} ms", counter, totalTime);
    }
}