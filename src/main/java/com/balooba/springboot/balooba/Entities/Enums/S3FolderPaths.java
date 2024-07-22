package com.balooba.springboot.balooba.Entities.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum S3FolderPaths {
    PROPERTIES("properties");

    private final String path;

    private S3FolderPaths(String path) {
        this.path = path;
    }

    @JsonValue
    public String getPath() {
        return path;
    }

    @JsonCreator
    public static S3FolderPaths forValue(String value) {
        for (S3FolderPaths s3FolderPaths : values()) {
            if (s3FolderPaths.getPath().equalsIgnoreCase(value)) {
                return s3FolderPaths;
            }
        }
        throw new IllegalArgumentException("Unknown enum value");
    }
}
