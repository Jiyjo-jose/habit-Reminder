package com.edstem.habitTracker.contract.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Duration;

    @Data
    @AllArgsConstructor
    public class TimerRequest {

    private Long id;
    private String name;
    private Duration interval;
    private String startTime;
    }


