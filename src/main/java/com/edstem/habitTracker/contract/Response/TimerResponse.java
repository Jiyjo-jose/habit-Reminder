package com.edstem.habitTracker.contract.Response;

import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
