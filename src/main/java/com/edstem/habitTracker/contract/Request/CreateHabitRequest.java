package com.edstem.habitTracker.contract.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHabitRequest {
    private Long habitId;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Description cannot be blank")
    private String description;

    private String reminderTime;

    private Duration interval;
}
