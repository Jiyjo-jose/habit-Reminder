package com.edstem.habitReminder.contract.request;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddReminderDaysRequest {
    private List<DayOfWeek> reminderDays;
    private LocalDate endDate;
}
