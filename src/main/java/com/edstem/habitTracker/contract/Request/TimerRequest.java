package com.edstem.habitTracker.contract.Request;

import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimerRequest {

    private Long id;
    private String name;
    private Duration interval;
    private String startTime;
}
