package com.edstem.habitReminder.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edstem.habitReminder.contract.response.AddReminderDaysResponse;
import com.edstem.habitReminder.model.Habit;
import com.edstem.habitReminder.model.ReminderDays;
import com.edstem.habitReminder.repository.HabitRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ReminderDaysServiceTest {
    @MockBean private HabitRepository habitRepository;

    @Autowired private ReminderDaysService reminderDaysService;

    @Test
    void testAddReminderDaysToHabit() {

        Long habitId = 1L;
        List<DayOfWeek> reminderDays = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
        LocalDate endDate = LocalDate.now().plusDays(7);

        Habit existingHabit = new Habit();
        existingHabit.setHabitId(1L);

        when(habitRepository.findById(habitId)).thenReturn(Optional.of(existingHabit));
        when(habitRepository.save(any())).thenReturn(existingHabit);

        AddReminderDaysResponse response =
                reminderDaysService.addReminderDaysToHabit(habitId, reminderDays, endDate);

        assertThat(response).isNotNull();
        assertThat(response.getReminderDays())
                .containsExactlyInAnyOrder(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
        assertThat(existingHabit.getReminderDays())
                .hasSize(2); // Assuming no existing reminder days
        verify(habitRepository, times(1)).findById(habitId);
        verify(habitRepository, times(1)).save(existingHabit);
    }

    @Test
    void testAddReminderDaysToHabitWhenHabitNotFound() {
        // Arrange
        Long habitId = 1L;
        List<DayOfWeek> reminderDays = Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
        LocalDate endDate = LocalDate.now().plusDays(7);

        when(habitRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(
                EntityNotFoundException.class,
                () -> {
                    reminderDaysService.addReminderDaysToHabit(habitId, reminderDays, endDate);
                });

        verify(habitRepository, times(1)).findById(habitId);
        verify(habitRepository, never()).save(any(Habit.class));
    }

    @Test
    public void testCompleteReminderDay() {
        Long habitId = 1L;
        Long reminderDayId = 2L;

        Habit habit = new Habit();
        habit.setReminderDays(new ArrayList<>());

        ReminderDays reminderDay = new ReminderDays();
        reminderDay.setReminderDayId(reminderDayId);
        reminderDay.setCompleted(false);

        habit.getReminderDays().add(reminderDay);

        when(habitRepository.findById(habitId)).thenReturn(Optional.of(habit));

        reminderDaysService.completeReminderDay(habitId, reminderDayId);

        verify(habitRepository).save(habit);

        assertTrue(reminderDay.isCompleted());
    }

    @Test
    void testCompleteReminderDayHabitNotFound() {
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
    void completeReminderDayReminderDayNotFound() {
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
    void getAllReminderDays() {

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
    void getAllReminderDaysHabitNotFound() {

        Long habitId = 1L;
        when(habitRepository.findById(habitId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> reminderDaysService.getAllReminderDays(habitId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Habit not found");
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
    void testGetCompletedReminderDaysHabitNotFound() {
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
    void testGetIncompleteReminderDaysHabitNotFound() {
        Long habitId = 1L;
        when(habitRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(
                EntityNotFoundException.class,
                () -> {
                    reminderDaysService.getIncompleteReminderDays(habitId);
                });
        verify(habitRepository, times(1)).findById(habitId);
    }
}

//    @Test
//    void testDeleteReminderDay() {
//        Long habitId = 1L;
//        Long reminderDayId = 1L;
//        reminderDaysService.deleteReminderDay(habitId, reminderDayId);
//        verify(habitRepository, times(1)).deleteReminderDay(habitId, reminderDayId);
//    }
// }
