package com.edstem.habitReminder.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edstem.habitReminder.contract.request.AddReminderDaysRequest;
import com.edstem.habitReminder.contract.response.AddReminderDaysResponse;
import com.edstem.habitReminder.model.ReminderDays;
import com.edstem.habitReminder.service.ReminderDaysService;
import jakarta.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ReminderDaysControllerTest {
    @Autowired private ReminderDaysController reminderDaysController;

    @MockBean private ReminderDaysService reminderDaysService;

    @Test
    void testAddReminderDaysToHabit_Success() {
        Long habitId = 1L;
        AddReminderDaysRequest reminderDaysRequest = new AddReminderDaysRequest();
        reminderDaysRequest.setReminderDays(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));

        AddReminderDaysResponse expectedResponse = new AddReminderDaysResponse();
        when(reminderDaysService.addReminderDaysToHabit(
                        habitId,
                        reminderDaysRequest.getReminderDays(),
                        reminderDaysRequest.getEndDate()))
                .thenReturn(expectedResponse);

        ResponseEntity<AddReminderDaysResponse> responseEntity =
                reminderDaysController.addReminderDaysToHabit(habitId, reminderDaysRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());

        verify(reminderDaysService, times(1))
                .addReminderDaysToHabit(
                        habitId,
                        reminderDaysRequest.getReminderDays(),
                        reminderDaysRequest.getEndDate());
    }

    @Test
    void testAddReminderDaysToHabit_NotFound() {
        // Arrange
        Long habitId = 1L;
        AddReminderDaysRequest reminderDaysRequest = new AddReminderDaysRequest();
        reminderDaysRequest.setReminderDays(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        when(reminderDaysService.addReminderDaysToHabit(
                        habitId,
                        reminderDaysRequest.getReminderDays(),
                        reminderDaysRequest.getEndDate()))
                .thenThrow(new EntityNotFoundException("Habit not found"));
        ResponseEntity<AddReminderDaysResponse> responseEntity =
                reminderDaysController.addReminderDaysToHabit(habitId, reminderDaysRequest);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(reminderDaysService, times(1))
                .addReminderDaysToHabit(
                        habitId,
                        reminderDaysRequest.getReminderDays(),
                        reminderDaysRequest.getEndDate());
    }

    @Test
    void testAddReminderDaysToHabit_InternalServerError() {
        Long habitId = 1L;
        AddReminderDaysRequest reminderDaysRequest = new AddReminderDaysRequest();
        reminderDaysRequest.setReminderDays(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
        when(reminderDaysService.addReminderDaysToHabit(
                        habitId,
                        reminderDaysRequest.getReminderDays(),
                        reminderDaysRequest.getEndDate()))
                .thenThrow(new RuntimeException("Simulated error"));

        ResponseEntity<AddReminderDaysResponse> responseEntity =
                reminderDaysController.addReminderDaysToHabit(habitId, reminderDaysRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());

        verify(reminderDaysService, times(1))
                .addReminderDaysToHabit(
                        habitId,
                        reminderDaysRequest.getReminderDays(),
                        reminderDaysRequest.getEndDate());
    }

    @Test
    void getAllReminderDays_shouldReturnListOfReminderDays() {

        Long habitId = 1L;
        List<ReminderDays> expectedReminderDays =
                Arrays.asList(new ReminderDays(), new ReminderDays());

        when(reminderDaysService.getAllReminderDays(habitId)).thenReturn(expectedReminderDays);
        ResponseEntity<List<ReminderDays>> responseEntity =
                reminderDaysController.getAllReminderDays(habitId);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(expectedReminderDays);
        verify(reminderDaysService, times(1)).getAllReminderDays(habitId);
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
        verify(reminderDaysService, times(1)).completeReminderDay(habitId, reminderDayId);
    }

    @Test
    void testGetCompletedReminderDays() {
        Long habitId = 1L;
        List<ReminderDays> completedReminderDays =
                Arrays.asList(new ReminderDays(), new ReminderDays());
        when(reminderDaysService.getCompletedReminderDays(habitId))
                .thenReturn(completedReminderDays);
        ResponseEntity<List<ReminderDays>> responseEntity =
                reminderDaysController.getCompletedReminderDays(habitId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(completedReminderDays, responseEntity.getBody());
        verify(reminderDaysService, times(1)).getCompletedReminderDays(habitId);
    }

    @Test
    void testGetIncompleteReminderDays() {

        Long habitId = 1L;
        List<ReminderDays> incompleteReminderDays =
                Arrays.asList(new ReminderDays(), new ReminderDays());
        when(reminderDaysService.getIncompleteReminderDays(habitId))
                .thenReturn(incompleteReminderDays);
        ResponseEntity<List<ReminderDays>> responseEntity =
                reminderDaysController.getIncompleteReminderDays(habitId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(incompleteReminderDays, responseEntity.getBody());
        verify(reminderDaysService, times(1)).getIncompleteReminderDays(habitId);
    }
}
//    @Test
//    void testDeleteReminderDay() {
//        Long habitId = 1L;
//        Long reminderDayId = 2L;
//        doNothing().when(reminderDaysService).deleteReminderDay(habitId, reminderDayId);
//        ResponseEntity<String> responseEntity =
//                reminderDaysController.deleteReminderDay(habitId, reminderDayId);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals("Reminder day deleted successfully", responseEntity.getBody());
//        verify(reminderDaysService, times(1)).deleteReminderDay(habitId, reminderDayId);
//    }

//    @Test
//    void testDeleteReminderDay_shouldReturnNotFoundWhenEntityNotFoundExceptionIsThrown() {
//        Long habitId = 1L;
//        Long reminderDayId = 2L;
//        doThrow(EntityNotFoundException.class)
//                .when(reminderDaysService)
//                .deleteReminderDay(habitId, reminderDayId);
//        ResponseEntity<String> responseEntity =
//                reminderDaysController.deleteReminderDay(habitId, reminderDayId);
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//        assertEquals("Reminder day not found", responseEntity.getBody());
//        verify(reminderDaysService, times(1)).deleteReminderDay(habitId, reminderDayId);
//    }
//
//    @Test
//    void testDeleteReminderDay_shouldReturnInternalServerErrorOnError() {
//
//        Long habitId = 1L;
//        Long reminderDayId = 2L;
//        doThrow(new RuntimeException("Simulated error"))
//                .when(reminderDaysService)
//                .deleteReminderDay(habitId, reminderDayId);
//        ResponseEntity<String> responseEntity =
//                reminderDaysController.deleteReminderDay(habitId, reminderDayId);
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
//        assertEquals("Error deleting reminder day", responseEntity.getBody());
//        verify(reminderDaysService, times(1)).deleteReminderDay(habitId, reminderDayId);
//    }
