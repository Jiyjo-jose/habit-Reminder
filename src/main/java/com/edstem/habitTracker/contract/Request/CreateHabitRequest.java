package com.edstem.habitTracker.contract.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHabitRequest {
    private Long habitId;
    private String description;
}
