package com.edstem.habitReminder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edstem.habitReminder.model.Habit;
import com.edstem.habitReminder.model.ReminderDays;
import com.edstem.habitReminder.repository.HabitRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ReminderEmailServiceTest {

    @MockBean private EmailService emailService;

    @MockBean private HabitRepository habitRepository;

    @Autowired private ReminderEmailService reminderEmailService;

    @Test
    void testSendReminderEmails() {
        when(habitRepository.findActiveReminders(Mockito.<LocalDate>any()))
                .thenReturn(new ArrayList<>());
        reminderEmailService.sendReminderEmails();
        verify(habitRepository).findActiveReminders(Mockito.<LocalDate>any());
    }

    @Test
    void testScheduleEmail() {
        Habit habit = new Habit();
        habit.setDescription("Test");
        habit.setEmail("test");
        habit.setHabitId(1L);
        habit.setName("Name");
        habit.setReminderDays(new ArrayList<>());
        habit.setReminderTime(LocalTime.now());

        ReminderDays reminder = new ReminderDays();
        reminder.setCompleted(true);
        reminder.setDay(DayOfWeek.MONDAY);
        reminder.setEndDate(LocalDate.of(2024, 1, 1));
        reminder.setHabit(habit);
        reminder.setReminderDayId(1L);
        reminder.setStartDate(LocalDate.of(2024, 1, 1));
        reminderEmailService.scheduleEmail(reminder);
    }

    @Test
    void testSendEmail() {
        doNothing()
                .when(emailService)
                .sendEmail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

        Habit habit = new Habit();
        habit.setDescription("Test");
        habit.setEmail("test");
        habit.setHabitId(1L);
        habit.setName("Name");
        habit.setReminderDays(new ArrayList<>());
        habit.setReminderTime(LocalTime.now());

        ReminderDays reminder = new ReminderDays();
        reminder.setCompleted(true);
        reminder.setDay(DayOfWeek.MONDAY);
        reminder.setEndDate(LocalDate.of(2024, 1, 1));
        reminder.setHabit(habit);
        reminder.setReminderDayId(1L);
        reminder.setStartDate(LocalDate.of(2024, 1, 1));
        reminderEmailService.sendEmail(reminder);
        verify(emailService)
                .sendEmail(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
        assertEquals("2024-01-01", reminder.getEndDate().toString());
        assertEquals("2024-01-01", reminder.getStartDate().toString());
        assertEquals(1L, reminder.getReminderDayId().longValue());
        assertEquals(DayOfWeek.MONDAY, reminder.getDay());
        assertTrue(reminder.isCompleted());
        assertSame(habit, reminder.getHabit());
    }
}
