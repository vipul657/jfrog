package com.example.jfrog.producer.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = false)
public class StorageEntity {

    @JsonProperty("repositoriesSummaryList")
    private RepositorySummary[] repositorySummaries;

    public RepositorySummary[] getRepositorySummaries() {
        return repositorySummaries;
    }

    public void setRepositorySummaries(RepositorySummary[] repositorySummaries) {
        this.repositorySummaries = repositorySummaries;
    }
}
