package org.jessusthread.campusfeedapi.core.domain;

import lombok.Getter;

@Getter
public enum MongoNameCollection {
    POSITION("position");

    private final String collectionName;

    MongoNameCollection(String collectionName) {
        this.collectionName = collectionName;
    }
}
