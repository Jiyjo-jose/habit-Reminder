package com.edstem.habitReminder.contract.response;

import java.time.DayOfWeek;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AddReminderDaysResponse {

    private List<DayOfWeek> reminderDays;
}
