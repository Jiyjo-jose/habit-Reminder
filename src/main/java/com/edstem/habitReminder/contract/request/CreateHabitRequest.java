package com.edstem.habitReminder.contract.request;

import jakarta.validation.constraints.Email;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHabitRequest {
    private Long habitId;

    private String name;

    private String description;

    private LocalTime reminderTime;

    @Email private String email;
}
