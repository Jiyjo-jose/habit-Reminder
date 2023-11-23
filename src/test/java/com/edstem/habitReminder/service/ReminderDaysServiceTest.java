package com.edstem.habitReminder.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edstem.habitReminder.model.Habit;
import com.edstem.habitReminder.model.ReminderDays;
import com.edstem.habitReminder.repository.HabitRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ReminderDaysService.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ReminderDaysServiceTest {
    @MockBean private HabitRepository habitRepository;
    @InjectMocks private HabitService habitService;

    @Autowired private ReminderDaysService reminderDaysService;

    @Test
    void testAddReminderDaysToHabit() {
        Long habitId = 1L;
        List<DayOfWeek> reminderDays =
                List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);

        Habit existingHabit = new Habit();
        existingHabit.setHabitId(habitId);
        existingHabit.setReminderDays(new ArrayList<>());
        Mockito.when(habitRepository.findById(anyLong())).thenReturn(Optional.of(existingHabit));
        Mockito.when(habitRepository.save(any(Habit.class))).thenReturn(existingHabit);
        reminderDaysService.addReminderDaysToHabit(habitId, reminderDays);
        assertEquals(3, existingHabit.getReminderDays().size());
        Mockito.verify(habitRepository, times(1)).findById(habitId);
        Mockito.verify(habitRepository, times(1)).save(existingHabit);
    }

    @Test
    void getAllReminderDays_shouldReturnListOfReminderDays() {

        Long habitId = 1L;
        ReminderDays reminderDay1 = new ReminderDays();
        ReminderDays reminderDay2 = new ReminderDays();
        Habit habit = new Habit();
        habit.setReminderDays(Arrays.asList(reminderDay1, reminderDay2));
        when(habitRepository.findById(habitId)).thenReturn(Optional.of(habit));
        List<ReminderDays> result = reminderDaysService.getAllReminderDays(habitId);
        assertThat(result).containsExactly(reminderDay1, reminderDay2);
        verify(habitRepository, times(1)).findById(habitId);
    }

    @Test
    void getAllReminderDays_shouldThrowEntityNotFoundExceptionWhenHabitNotFound() {

        Long habitId = 1L;
        when(habitRepository.findById(habitId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> reminderDaysService.getAllReminderDays(habitId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Habit not found");
    }

    @Test
    void testCompleteReminderDay() {
        Long habitId = 1L;
        Long reminderDayId = 1L;
        Habit existingHabit = new Habit();
        existingHabit.setHabitId(habitId);
        ReminderDays reminderDay = new ReminderDays();
        reminderDay.setReminderDayId(reminderDayId);
        reminderDay.setCompleted(false);
        existingHabit.setReminderDays(Collections.singletonList(reminderDay));
        when(habitRepository.findById(anyLong())).thenReturn(Optional.of(existingHabit));
        when(habitRepository.save(Mockito.any(Habit.class))).thenReturn(existingHabit);
        reminderDaysService.completeReminderDay(habitId, reminderDayId);
        assertTrue(reminderDay.isCompleted());
        verify(habitRepository, times(1)).findById(habitId);
        verify(habitRepository, times(1)).save(existingHabit);
    }

    @Test
    void testCompleteReminderDay_whenHabitNotFound_shouldThrowEntityNotFoundException() {
        Long habitId = 1L;
        Long reminderDayId = 1L;
        when(habitRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                () -> {
                    reminderDaysService.completeReminderDay(habitId, reminderDayId);
                });
        verify(habitRepository, times(1)).findById(habitId);
        verify(habitRepository, Mockito.never()).save(Mockito.any(Habit.class));
    }

    @Test
    void completeReminderDay_whenReminderDayNotFound_shouldThrowEntityNotFoundException() {
        Long habitId = 1L;
        Long reminderDayId = 1L;
        Habit existingHabit = new Habit();
        existingHabit.setHabitId(habitId);
        existingHabit.setReminderDays(Collections.emptyList());
        when(habitRepository.findById(anyLong())).thenReturn(Optional.of(existingHabit));
        assertThrows(
                EntityNotFoundException.class,
                () -> {
                    reminderDaysService.completeReminderDay(habitId, reminderDayId);
                });
        verify(habitRepository, times(1)).findById(habitId);
        verify(habitRepository, Mockito.never()).save(Mockito.any(Habit.class));
    }

    @Test
    void testGetCompletedReminderDays() {
        Long habitId = 1L;
        Habit existingHabit = new Habit();
        existingHabit.setHabitId(habitId);
        ReminderDays reminderDay1 = new ReminderDays();
        reminderDay1.setReminderDayId(1L);
        reminderDay1.setCompleted(true);
        ReminderDays reminderDay2 = new ReminderDays();
        reminderDay2.setReminderDayId(2L);
        reminderDay2.setCompleted(false);

        existingHabit.setReminderDays(List.of(reminderDay1, reminderDay2));
        when(habitRepository.findById(anyLong())).thenReturn(Optional.of(existingHabit));
        List<ReminderDays> completedReminderDays =
                reminderDaysService.getCompletedReminderDays(habitId);
        assertEquals(1, completedReminderDays.size());
        assertEquals(reminderDay1, completedReminderDays.get(0));
        verify(habitRepository, times(1)).findById(habitId);
    }

    @Test
    void testGetCompletedReminderDays_whenHabitNotFound_shouldThrowEntityNotFoundException() {
        Long habitId = 1L;
        when(habitRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                () -> {
                    reminderDaysService.getCompletedReminderDays(habitId);
                });
        verify(habitRepository, times(1)).findById(habitId);
    }

    @Test
    void testGetIncompleteReminderDays5() {
        Long habitId = 1L;
        Habit existingHabit = new Habit();
        existingHabit.setHabitId(habitId);
        ReminderDays reminderDay1 = new ReminderDays();
        reminderDay1.setReminderDayId(1L);
        reminderDay1.setCompleted(true);
        ReminderDays reminderDay2 = new ReminderDays();
        reminderDay2.setReminderDayId(2L);
        reminderDay2.setCompleted(false);
        existingHabit.setReminderDays(List.of(reminderDay1, reminderDay2));
        when(habitRepository.findById(anyLong())).thenReturn(Optional.of(existingHabit));
        List<ReminderDays> incompleteReminderDays =
                reminderDaysService.getIncompleteReminderDays(habitId);
        assertEquals(1, incompleteReminderDays.size());
        assertEquals(reminderDay2, incompleteReminderDays.get(0));
        verify(habitRepository, times(1)).findById(habitId);
    }

    @Test
    void testGetIncompleteReminderDays_whenHabitNotFound_shouldThrowEntityNotFoundException() {
        Long habitId = 1L;
        when(habitRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                () -> {
                    reminderDaysService.getIncompleteReminderDays(habitId);
                });
        verify(habitRepository, times(1)).findById(habitId);
    }

    @Test
    void testDeleteReminderDay() {
        Long habitId = 1L;
        Long reminderDayId = 1L;
        reminderDaysService.deleteReminderDay(habitId, reminderDayId);
        verify(habitRepository, times(1)).deleteReminderDay(habitId, reminderDayId);
    }
}
