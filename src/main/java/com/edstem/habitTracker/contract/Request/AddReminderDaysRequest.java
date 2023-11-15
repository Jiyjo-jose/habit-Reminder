package com.edstem.habitTracker.contract.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class AddReminderDaysRequest {
        private List<DayOfWeek> reminderDays;
    }


