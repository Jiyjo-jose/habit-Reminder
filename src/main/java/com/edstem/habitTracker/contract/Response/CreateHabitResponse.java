package com.edstem.habitTracker.contract.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateHabitResponse {
    private Long habitId;
    private String description;
}
