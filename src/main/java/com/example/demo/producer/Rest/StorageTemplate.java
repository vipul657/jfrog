package com.example.demo.producer.Rest;

import com.example.demo.producer.entity.StorageEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class StorageTemplate {

    private RestTemplate restTemplate = new RestTemplate();

    public Optional<StorageEntity> getRepositorySummary(String url) {
        ResponseEntity<StorageEntity> responseEntity = restTemplate.getForEntity(url, StorageEntity.class);
        if (responseEntity.getStatusCodeValue() == 200) {
            return Optional.ofNullable(responseEntity.getBody());
        } else {
            return Optional.empty();
        }
    }

}
