package com.example.test_study.mock;

import com.example.test_study.common.service.ClockHolder;

public class TestClockHolder implements ClockHolder {

    private final long mills;

    public TestClockHolder(long mills) {
        this.mills = mills;
    }

    @Override
    public long millis() {
        return mills;
    }
}
