package com.edstem.habitTracker.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.edstem.habitTracker.contract.Request.AddReminderDaysRequest;
import com.edstem.habitTracker.model.ReminderDays;
import com.edstem.habitTracker.service.ReminderDaysService;
import jakarta.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ReminderDaysController.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ReminderDaysControllerTest {
    @Autowired private ReminderDaysController reminderDaysController;

    @MockBean private ReminderDaysService reminderDaysService;

    @Test
    void testAddReminderDaysToHabit() {
        Long habitId = 1L;
        AddReminderDaysRequest addReminderDaysRequest = new AddReminderDaysRequest();
        addReminderDaysRequest.setReminderDays(
                Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        doNothing()
                .when(reminderDaysService)
                .addReminderDaysToHabit(habitId, addReminderDaysRequest.getReminderDays());
        ResponseEntity<?> responseEntity =
                reminderDaysController.addReminderDaysToHabit(habitId, addReminderDaysRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Reminder days added successfully", responseEntity.getBody());
        Mockito.verify(reminderDaysService, Mockito.times(1))
                .addReminderDaysToHabit(habitId, addReminderDaysRequest.getReminderDays());
    }

    @Test
    void testAddReminderDaysToHabit_shouldReturnInternalServerErrorOnError() {
        Long habitId = 1L;
        AddReminderDaysRequest addReminderDaysRequest = new AddReminderDaysRequest();
        addReminderDaysRequest.setReminderDays(
                Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        doThrow(new RuntimeException("Simulated error"))
                .when(reminderDaysService)
                .addReminderDaysToHabit(habitId, addReminderDaysRequest.getReminderDays());
        ResponseEntity<?> responseEntity =
                reminderDaysController.addReminderDaysToHabit(habitId, addReminderDaysRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Error adding reminder days", responseEntity.getBody());
        Mockito.verify(reminderDaysService, Mockito.times(1))
                .addReminderDaysToHabit(habitId, addReminderDaysRequest.getReminderDays());
    }

    @Test
    void testCompleteReminderDay() {
        Long habitId = 1L;
        Long reminderDayId = 2L;
        doNothing().when(reminderDaysService).completeReminderDay(habitId, reminderDayId);
        ResponseEntity<String> responseEntity =
                reminderDaysController.completeReminderDay(habitId, reminderDayId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Reminder day marked as completed successfully", responseEntity.getBody());
        Mockito.verify(reminderDaysService, Mockito.times(1))
                .completeReminderDay(habitId, reminderDayId);
    }

    @Test
    void testGetCompletedReminderDays() {
        Long habitId = 1L;
        List<ReminderDays> completedReminderDays =
                Arrays.asList(
                        new ReminderDays(1L, null, DayOfWeek.MONDAY, true),
                        new ReminderDays(2L, null, DayOfWeek.WEDNESDAY, true));
        when(reminderDaysService.getCompletedReminderDays(habitId))
                .thenReturn(completedReminderDays);
        ResponseEntity<List<ReminderDays>> responseEntity =
                reminderDaysController.getCompletedReminderDays(habitId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(completedReminderDays, responseEntity.getBody());
        Mockito.verify(reminderDaysService, Mockito.times(1)).getCompletedReminderDays(habitId);
    }

    @Test
    void testGetIncompleteReminderDays() {

        Long habitId = 1L;
        List<ReminderDays> incompleteReminderDays =
                Arrays.asList(
                        new ReminderDays(1L, null, DayOfWeek.MONDAY, false),
                        new ReminderDays(2L, null, DayOfWeek.WEDNESDAY, false));
        when(reminderDaysService.getIncompleteReminderDays(habitId))
                .thenReturn(incompleteReminderDays);
        ResponseEntity<List<ReminderDays>> responseEntity =
                reminderDaysController.getIncompleteReminderDays(habitId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(incompleteReminderDays, responseEntity.getBody());
        Mockito.verify(reminderDaysService, Mockito.times(1)).getIncompleteReminderDays(habitId);
    }

    @Test
    void testDeleteReminderDay() {
        Long habitId = 1L;
        Long reminderDayId = 2L;
        doNothing().when(reminderDaysService).deleteReminderDay(habitId, reminderDayId);
        ResponseEntity<String> responseEntity =
                reminderDaysController.deleteReminderDay(habitId, reminderDayId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Reminder day deleted successfully", responseEntity.getBody());
        Mockito.verify(reminderDaysService, Mockito.times(1))
                .deleteReminderDay(habitId, reminderDayId);
    }

    @Test
    void testDeleteReminderDay_shouldReturnNotFoundWhenEntityNotFoundExceptionIsThrown() {
        Long habitId = 1L;
        Long reminderDayId = 2L;
        doThrow(EntityNotFoundException.class)
                .when(reminderDaysService)
                .deleteReminderDay(habitId, reminderDayId);
        ResponseEntity<String> responseEntity =
                reminderDaysController.deleteReminderDay(habitId, reminderDayId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Reminder day not found", responseEntity.getBody());
        Mockito.verify(reminderDaysService, Mockito.times(1))
                .deleteReminderDay(habitId, reminderDayId);
    }

    @Test
    void testDeleteReminderDay_shouldReturnInternalServerErrorOnError() {

        Long habitId = 1L;
        Long reminderDayId = 2L;
        doThrow(new RuntimeException("Simulated error"))
                .when(reminderDaysService)
                .deleteReminderDay(habitId, reminderDayId);
        ResponseEntity<String> responseEntity =
                reminderDaysController.deleteReminderDay(habitId, reminderDayId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Error deleting reminder day", responseEntity.getBody());
        Mockito.verify(reminderDaysService, Mockito.times(1))
                .deleteReminderDay(habitId, reminderDayId);
    }
}
