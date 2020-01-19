package com.example.demo.producer.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositorySummary {

    @JsonProperty("repoKey")
    private String repoKey;

    @JsonProperty("filesCount")
    private Integer filesCount;

    @JsonProperty("foldersCount")
    private Integer foldersCount;

    @JsonProperty("itemsCount")
    private Integer itemsCount;

    @JsonProperty("usedSpace")
    private String usedSpace;

    public String getRepoKey() {
        return repoKey;
    }

    public void setRepoKey(String repoKey) {
        this.repoKey = repoKey;
    }

    public Integer getFilesCount() {
        return filesCount;
    }

    public void setFilesCount(Integer filesCount) {
        this.filesCount = filesCount;
    }

    public Integer getFoldersCount() {
        return foldersCount;
    }

    public void setFoldersCount(Integer foldersCount) {
        this.foldersCount = foldersCount;
    }

    public Integer getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(Integer itemsCount) {
        this.itemsCount = itemsCount;
    }

    public String getUsedSpace() {
        return usedSpace;
    }

    public void setUsedSpace(String usedSpace) {
        this.usedSpace = usedSpace;
    }
}
