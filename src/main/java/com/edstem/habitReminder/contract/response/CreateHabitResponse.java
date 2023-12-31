package com.edstem.habitReminder.contract.response;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateHabitResponse {
    private Long habitId;

    private String name;

    private String description;

    private LocalTime reminderTime;
    private String email;
}
