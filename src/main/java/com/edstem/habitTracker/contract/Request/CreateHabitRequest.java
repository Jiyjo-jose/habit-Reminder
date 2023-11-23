package com.edstem.habitTracker.contract.Request;

import jakarta.validation.constraints.NotBlank;
import java.time.Duration;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Date startDate;
    private Date endDate;
}
