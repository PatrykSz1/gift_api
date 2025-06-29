package com.patryk.gift_api.service;

import com.patryk.gift_api.service.operations.KidOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportService {

    //1k/s = x8

    private static final int BATCH_SIZE = 10_000;
    //    private static final String INSERT_QUERY = "INSERT INTO kid(DTYPE, FIRST_NAME, LAST_NAME, BIRTH_DATE) VALUES %s";
//    private static final String SINGLE_KID_PARAMS = "(\"%s\", \"%s\", \"%s\", \"%s\"),";
    private final JdbcTemplate jdbcTemplate;
    private final Map<String, KidOperations> kidOperationsMap;


    @Transactional
    public void upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty. Send it once again!");
        }

        Map<String, List<String[]>> childrenData = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            StringBuilder params = new StringBuilder();
            int counter = 0;
            long start = System.currentTimeMillis();

            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                String dtype = data[0].toUpperCase();
                List<String[]> elements = childrenData.computeIfAbsent(dtype, e -> new ArrayList<>());
                elements.add(data);
                if (elements.size() % BATCH_SIZE == 0) {
                    KidOperations kidOperations = kidOperationsMap.get(dtype);
                    if (kidOperations == null) {
                        throw new IllegalArgumentException("Unknown dtype: " + dtype);
                    }
                    for (String[] element : elements) {
                        params.append(kidOperations.formatParams(element));
                    }
                    int paramsLength = params.length();
                    params.delete(paramsLength - 1, paramsLength);
                    jdbcTemplate.batchUpdate(String.format(kidOperations.getInsertQuery(), params));
                    long now = System.currentTimeMillis();
                    log.info("Processed {} records in: {} ms", counter, now - start);
                    elements.clear();
                    params.setLength(0);
                    counter = 0;
                    start = now;
                }
            }

            for (String dtype : childrenData.keySet()) {
                if (!childrenData.get(dtype).isEmpty()) {
                    KidOperations kidOperations = kidOperationsMap.get(dtype);
                    if (kidOperations == null) {
                        throw new IllegalArgumentException("Unknown dtype: " + dtype);
                    }
                    for (String[] element : childrenData.get(dtype)) {
                        params.append(kidOperations.formatParams(element));
                    }
                    int paramsLength = params.length();
                    params.delete(paramsLength - 1, paramsLength);
                    jdbcTemplate.batchUpdate(String.format(kidOperations.getInsertQuery(), params));
                    long now = System.currentTimeMillis();
                    log.info("Processed {} records in: {} ms", counter, now - start);
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to upload file", e);
        }
    }
}


//    @Transactional
//    public void upload(MultipartFile file) {
//        if (file.isEmpty()) {
//            throw new IllegalArgumentException("File is empty");
//        }
//
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
//            String header = br.readLine();
//            String line;
//            int counter = 0;
//            long batchStart = System.currentTimeMillis();
//
//            String currentInsertQuery = null;
//            StringBuilder params = new StringBuilder();
//
//            while ((line = br.readLine()) != null) {
//                String[] cols = line.split(",");
//                String dtype = cols[0].toUpperCase();
//
//                KidOperations kidOperations = kidOperationsMap.get(dtype);
//                if (kidOperations == null) {
//                    throw new IllegalArgumentException("Unknown dtype: " + dtype);
//                }
//
//                if (currentInsertQuery == null) {
//                    currentInsertQuery = kidOperations.getInsertQuery();
//                    params.setLength(0);
//                    counter = 0;
//                }
//
//                if (!kidOperations.getInsertQuery().equals(currentInsertQuery)) {
//                    flushBatch(currentInsertQuery, params, counter, batchStart);
//                    currentInsertQuery = kidOperations.getInsertQuery();
//                    params.setLength(0);
//                    counter = 0;
//                    batchStart = System.currentTimeMillis();
//                }
//
//                params.append(kidOperations.formatParams(cols));
//                counter++;
//
//                if (counter % BATCH_SIZE == 0) {
//                    flushBatch(currentInsertQuery, params, counter, batchStart);
//                    params.setLength(0);
//                    counter = 0;
//                    batchStart = System.currentTimeMillis();
//                }
//            }
//
//            if (counter > 0) {
//                flushBatch(currentInsertQuery, params, counter, batchStart);
//            }
//
//        } catch (IOException e) {
//            throw new IllegalArgumentException("Upload failed", e);
//        }
//    }
//
//    private void flushBatch(String insertSqlTemplate,
//                            StringBuilder params,
//                            int count,
//                            long startTime) {
//        params.deleteCharAt(params.length() - 1);
//        String sql = String.format(insertSqlTemplate, params);
//        jdbcTemplate.update(sql);
//        long took = System.currentTimeMillis() - startTime;
//        log.info("Inserted {} rows in {} ms", count, took);
//    }

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


