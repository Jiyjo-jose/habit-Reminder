package com.edstem.habitReminder.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edstem.habitReminder.contract.request.CreateHabitRequest;
import com.edstem.habitReminder.contract.response.CreateHabitResponse;
import com.edstem.habitReminder.model.Habit;
import com.edstem.habitReminder.service.HabitService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class HabitControllerTest {
    @Autowired private HabitController habitController;

    @MockBean private HabitService habitService;

    @Test
    void testCreateHabit() {
        CreateHabitRequest request = new CreateHabitRequest();

        CreateHabitResponse createdHabit = new CreateHabitResponse();

        when(habitService.createHabit(request)).thenReturn(createdHabit);

        ResponseEntity<CreateHabitResponse> responseEntity = habitController.createHabit(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdHabit, responseEntity.getBody());

        verify(habitService, times(1)).createHabit(request);
    }

    @Test
    void testGetAllHabits() {
        int page = 0;
        int size = 10;

        List<Habit> habits = Arrays.asList(new Habit(), new Habit());
        when(habitService.getAllHabits(page, size)).thenReturn(habits);
        List<Habit> result = habitController.getAllHabits(page, size);

        assertEquals(habits, result);

        verify(habitService, times(1)).getAllHabits(page, size);
    }

    @Test
    void testEditHabit() {
        Long habitId = 1L;
        CreateHabitRequest editHabitRequest = new CreateHabitRequest();

        CreateHabitResponse updatedHabit = new CreateHabitResponse();
        when(habitService.editHabit(habitId, editHabitRequest)).thenReturn(updatedHabit);
        ResponseEntity<CreateHabitResponse> responseEntity =
                habitController.editHabit(habitId, editHabitRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedHabit, responseEntity.getBody());

        verify(habitService, times(1)).editHabit(habitId, editHabitRequest);
    }

    @Test
    void testDeleteHabit_Success() {
        Long habitId = 1L;
        doNothing().when(habitService).deleteHabitById(habitId);
        ResponseEntity<String> responseEntity = habitController.deleteHabit(habitId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Habit deleted successfully", responseEntity.getBody());

        verify(habitService, times(1)).deleteHabitById(habitId);
    }

    @Test
    void testDeleteHabit_NotFound() {
        Long habitId = 1L;
        doThrow(new EntityNotFoundException("Habit not found"))
                .when(habitService)
                .deleteHabitById(habitId);

        ResponseEntity<String> responseEntity = habitController.deleteHabit(habitId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Habit not found", responseEntity.getBody());

        verify(habitService, times(1)).deleteHabitById(habitId);
    }

    @Test
    void testDeleteHabit_InternalServerError() {
        Long habitId = 1L;
        doThrow(new RuntimeException("Simulated error"))
                .when(habitService)
                .deleteHabitById(habitId);
        ResponseEntity<String> responseEntity = habitController.deleteHabit(habitId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Error deleting habit", responseEntity.getBody());
        verify(habitService, times(1)).deleteHabitById(habitId);
    }
}
