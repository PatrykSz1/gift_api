package com.patryk.gift_api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportService {

    //1k/s = x8

    private static final int BATCH_SIZE = 10_000;
    private static final String INSERT_QUERY = "INSERT INTO kid(FIRST_NAME, LAST_NAME, BIRTH_DATE, DTYPE) VALUES %s";
    private static final String SINGLE_KID_PARAMS = "(\"%s\", \"%s\", \"%s\", \"%s\"),";
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty. Send it once again!");
        }

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            StringBuilder params = new StringBuilder();
            int counter = 0;
            long start = System.currentTimeMillis();

            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                params.append(String.format(SINGLE_KID_PARAMS, data[0], data[1], data[2], "BOY"));

                if (++counter % BATCH_SIZE == 0) {
                    int paramsLength = params.length();
                    params.delete(paramsLength - 1, paramsLength);
                    jdbcTemplate.batchUpdate(String.format(INSERT_QUERY, params));
                    long now = System.currentTimeMillis();
                    log.info("Processed {} records in: {} ms", counter, now - start);
                    params.setLength(0);
                    counter = 0;
                    start = now;
                }
            }

            if (!params.isEmpty()) {
                int paramsLength = params.length();
                params.delete(paramsLength - 1, paramsLength);
                jdbcTemplate.batchUpdate(String.format(INSERT_QUERY, params));
                long now = System.currentTimeMillis();
                log.info("Processed {} records in: {} ms", counter, now - start);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to upload file", e);
        }
    }
}


// Caly proces !!! wywolanie musi byc przez proxy
//    @Transactional
//    public void upload(MultipartFile file) {
//        List<Kid> kidsBatch = new ArrayList<>();
//
//        if (file.isEmpty()) {
//            throw new IllegalArgumentException("File is empty. Send it once again!");
//        }
//        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
//            String line = null;
//            int count = 0;
//            bufferedReader.readLine();
//            long start = System.currentTimeMillis();
//
//            while ((line = bufferedReader.readLine()) != null) {
//                String[] data = line.split(",");
//                Kid kid = Kid.builder()
//                        .firstName(data[0])
//                        .lastName(data[1])
//                        .birthDate(LocalDate.parse(data[2]))
//                        .build();
//                kidsBatch.add(kid);
//                saveAll(kidsBatch);
//
//                if (kidsBatch.size() % BATCH_SIZE == 0) {
//                    saveAll(kidsBatch);
//                    kidsBatch.clear();
//                }
//
//                if (++count % BATCH_SIZE == 0) {
//                    long now = System.currentTimeMillis();
//                    log.info("Processed {} records in: {} ms", BATCH_SIZE, now - start);
//                    count = 0;
//                    start = now;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void saveAll(List<Kid> kids) {
//        jdbcTemplate.batchUpdate("INSERT INTO kid(FIRST_NAME, LAST_NAME, BIRTH_DATE) VALUES (?,?,?)", kids, 1000,
//                (PreparedStatement ps, Kid kid) -> {
//                    ps.setString(1, kid.getFirstName());
//                    ps.setString(2, kid.getLastName());
//                    ps.setTimestamp(3, Timestamp.valueOf(kid.getBirthDate().atStartOfDay()));
//                });
//    }


