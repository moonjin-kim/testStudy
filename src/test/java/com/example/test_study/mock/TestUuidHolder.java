package com.example.test_study.mock;

import com.example.test_study.common.service.UuidHolder;


public class TestUuidHolder implements UuidHolder {

    private final String uuid;

    public TestUuidHolder(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String random() {
        return uuid;
    }
}
