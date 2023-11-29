package com.edstem.habitReminder.service;

import com.edstem.habitReminder.contract.response.AddReminderDaysResponse;
import com.edstem.habitReminder.model.Habit;
import com.edstem.habitReminder.model.ReminderDays;
import com.edstem.habitReminder.repository.HabitRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReminderDaysService {

    private final HabitRepository habitRepository;

    public AddReminderDaysResponse addReminderDaysToHabit(
            Long habitId, List<DayOfWeek> reminderDays, LocalDate endDate) {
        Habit habit =
                habitRepository
                        .findById(habitId)
                        .orElseThrow(() -> new EntityNotFoundException("Habit not found"));

        List<ReminderDays> reminderDaysList =
                reminderDays.stream()
                        .map(
                                day ->
                                        ReminderDays.builder()
                                                .habit(habit)
                                                .day(day)
                                                .startDate(LocalDate.now())
                                                .endDate(endDate)
                                                .completed(false)
                                                .build())
                        .collect(Collectors.toList());
        habit.getReminderDays().addAll(reminderDaysList);
        habitRepository.save(habit);
        List<DayOfWeek> savedReminderDays =
                reminderDaysList.stream().map(ReminderDays::getDay).collect(Collectors.toList());
        AddReminderDaysResponse response = new AddReminderDaysResponse();
        response.setReminderDays(savedReminderDays);
        return response;
    }

    public void completeReminderDay(Long habitId, Long reminderDayId) {
        Habit habit =
                habitRepository
                        .findById(habitId)
                        .orElseThrow(() -> new EntityNotFoundException("Habit not found"));

        ReminderDays reminderDay =
                habit.getReminderDays().stream()
                        .filter(day -> day.getReminderDayId().equals(reminderDayId))
                        .findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("Reminder day not found"));

        reminderDay.setCompleted(true);
        habitRepository.save(habit);
    }

    public List<ReminderDays> getAllReminderDays(Long habitId) {
        Habit habit =
                habitRepository
                        .findById(habitId)
                        .orElseThrow(() -> new EntityNotFoundException("Habit not found"));

        return habit.getReminderDays();
    }

    public List<ReminderDays> getCompletedReminderDays(Long habitId) {
        Habit habit =
                habitRepository
                        .findById(habitId)
                        .orElseThrow(() -> new EntityNotFoundException("Habit not found"));

        return habit.getReminderDays().stream()
                .filter(ReminderDays::isCompleted)
                .collect(Collectors.toList());
    }

    public List<ReminderDays> getIncompleteReminderDays(Long habitId) {
        Habit habit =
                habitRepository
                        .findById(habitId)
                        .orElseThrow(() -> new EntityNotFoundException("Habit not found"));

        return habit.getReminderDays().stream()
                .filter(day -> !day.isCompleted())
                .collect(Collectors.toList());
    }
}
