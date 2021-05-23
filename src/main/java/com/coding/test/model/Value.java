package com.coding.test.model;

import java.time.Instant;

public class Value {

    private Integer timesRetrieved;
    private final Instant creationTimestamp;

    public Value() {
        timesRetrieved = 0;
        creationTimestamp = Instant.now();
    }

    public int getTimesRetrieved() {
        return timesRetrieved;
    }

    public synchronized void increaseTimesRetrieved() {
        timesRetrieved++;
    }

    public Instant getCreationTimestamp() {
        return creationTimestamp;
    }
}
