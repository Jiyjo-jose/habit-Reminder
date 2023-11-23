package com.edstem.habitReminder.service;

import com.edstem.habitReminder.model.Habit;
import com.edstem.habitReminder.model.ReminderDays;
import com.edstem.habitReminder.repository.HabitRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ReminderDaysService {

    private final HabitRepository habitRepository;

    public void addReminderDaysToHabit(Long habitId, List<DayOfWeek> reminderDays) {
        habitRepository
                .findById(habitId)
                .ifPresent(
                        habit -> {
                            List<ReminderDays> reminderDaysList =
                                    reminderDays.stream()
                                            .map(
                                                    day ->
                                                            ReminderDays.builder()
                                                                    .habit(habit)
                                                                    .day(day)
                                                                    .completed(false)
                                                                    .build())
                                            .collect(Collectors.toList());
                            habit.getReminderDays().addAll(reminderDaysList);
                            habitRepository.save(habit);
                        });
    }

    public List<ReminderDays> getAllReminderDays(Long habitId) {
        Habit habit =
                habitRepository
                        .findById(habitId)
                        .orElseThrow(() -> new EntityNotFoundException("Habit not found"));

        return habit.getReminderDays();
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

    public void deleteReminderDay(Long habitId, Long reminderDayId) {
        habitRepository.deleteReminderDay(habitId, reminderDayId);
    }
}
