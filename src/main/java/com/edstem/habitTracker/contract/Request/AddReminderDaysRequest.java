package com.edstem.habitTracker.contract.Request;

import java.time.DayOfWeek;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddReminderDaysRequest {
    private List<DayOfWeek> reminderDays;
}
