package com.edstem.habitTracker.contract.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Duration;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimerResponse {

    private Long id;
    private String name;
    private Duration interval;
    private String startTime;
}

