package com.edstem.habitReminder.contract.response;

import java.time.DayOfWeek;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddReminderDaysResponse {

    private List<DayOfWeek> reminderDays;
}
