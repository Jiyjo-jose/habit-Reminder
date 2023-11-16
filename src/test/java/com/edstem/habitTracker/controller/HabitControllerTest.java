package com.edstem.habitTracker.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edstem.habitTracker.contract.Request.CreateHabitRequest;
import com.edstem.habitTracker.model.Habit;
import com.edstem.habitTracker.service.HabitService;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {HabitController.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
class HabitControllerTest {
    @Autowired private HabitController habitController;

    @MockBean private HabitService habitService;


    @Test
    void testCreateHabit() {
        CreateHabitRequest createHabitRequest = new CreateHabitRequest();
        createHabitRequest.setName("Exercise");
        createHabitRequest.setDescription("Daily workout");
        createHabitRequest.setReminderTime("08:00");
        createHabitRequest.setInterval(null);
        createHabitRequest.setEndDate(new Date());

        Habit createdHabit = new Habit();
        createdHabit.setHabitId(1L);
        createdHabit.setName(createHabitRequest.getName());
        createdHabit.setDescription(createHabitRequest.getDescription());
        createdHabit.setReminderTime(createHabitRequest.getReminderTime());
        createdHabit.setEndDate(createHabitRequest.getEndDate());
        when(habitService.createHabit(createHabitRequest)).thenReturn(createdHabit);
        ResponseEntity<Habit> responseEntity = habitController.createHabit(createHabitRequest);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createdHabit, responseEntity.getBody());
        verify(habitService, Mockito.times(1)).createHabit(createHabitRequest);
    }

    @Test
    void testGetAllHabits() {
        Habit habit1 = new Habit();
        habit1.setHabitId(1L);
        habit1.setName("Exercise");
        habit1.setDescription("Daily workout");

        Habit habit2 = new Habit();
        habit2.setHabitId(2L);
        habit2.setName("Reading");
        habit2.setDescription("Read a book");

        List<Habit> habits = Arrays.asList(habit1, habit2);
        when(habitService.getAllHabits()).thenReturn(habits);
        ResponseEntity<List<Habit>> responseEntity = habitController.getAllHabits();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(habits, responseEntity.getBody());
        Mockito.verify(habitService, Mockito.times(1)).getAllHabits();
    }

    @Test
    void testEditHabit_shouldReturnOkWhenEditedSuccessfully() {
        Long habitId = 1L;
        CreateHabitRequest editHabitRequest = new CreateHabitRequest();
        editHabitRequest.setName("New Name");
        editHabitRequest.setDescription("Updated description");
        doNothing().when(habitService).editHabit(habitId, editHabitRequest);
        ResponseEntity<String> responseEntity =
                habitController.editHabit(habitId, editHabitRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Habit updated successfully", responseEntity.getBody());
        Mockito.verify(habitService, Mockito.times(1)).editHabit(habitId, editHabitRequest);
    }

    @Test
    void testEditHabit_shouldReturnInternalServerErrorOnError() {
        Long habitId = 1L;
        CreateHabitRequest editHabitRequest = new CreateHabitRequest();
        editHabitRequest.setName("New Name");
        editHabitRequest.setDescription("Updated description");
        doThrow(new RuntimeException("Simulated error"))
                .when(habitService)
                .editHabit(habitId, editHabitRequest);
        ResponseEntity<String> responseEntity =
                habitController.editHabit(habitId, editHabitRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Error updating habit", responseEntity.getBody());
        Mockito.verify(habitService, Mockito.times(1)).editHabit(habitId, editHabitRequest);
    }

    @Test
    void testDeleteHabitById() {
        Long habitId = 1L;
        doNothing().when(habitService).deleteHabitById(habitId);
        ResponseEntity<String> responseEntity = habitController.deleteHabitById(habitId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Habit with ID 1 has been deleted", responseEntity.getBody());
        Mockito.verify(habitService, Mockito.times(1)).deleteHabitById(habitId);
    }
}
