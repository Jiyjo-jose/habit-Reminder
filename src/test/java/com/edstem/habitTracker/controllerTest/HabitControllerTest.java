package com.edstem.habitTracker.controllerTest;

import com.edstem.habitTracker.contract.Request.CreateHabitRequest;
import com.edstem.habitTracker.contract.Response.CreateHabitResponse;
import com.edstem.habitTracker.controller.HabitController;
import com.edstem.habitTracker.model.Habit;
import com.edstem.habitTracker.service.HabitService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HabitControllerTest {

    @Mock
    private HabitService habitService;

    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private HabitController habitController;
    @Test
    void testCreateHabit() {
        CreateHabitRequest request = new CreateHabitRequest(1L, "test");
        Habit habit = new Habit(1L,"test",false,new ArrayList<>(1));
        CreateHabitResponse response = new CreateHabitResponse(1L, "Test Habit");

        when(habitService.createHabit(request)).thenReturn(habit);
        when(modelMapper.map(habit, CreateHabitResponse.class)).thenReturn(response);

        ResponseEntity<CreateHabitResponse> result = habitController.createHabit(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }
    @Test
    void testGetAllHabits() {
        List<Habit> habits = new ArrayList<>();
        habits.add(new Habit(1L,"test",false,new ArrayList<>(1)));
        habits.add(new Habit(1L,"test",false,new ArrayList<>(1)));

        List<CreateHabitResponse> habitResponses = new ArrayList<>();
        habitResponses.add(new CreateHabitResponse(1L, "Test Habit"));
        habitResponses.add(new CreateHabitResponse(1L, "Test Habit"));

        Type listType = new TypeToken<List<CreateHabitResponse>>() {}.getType();

        when(habitService.getAllHabits()).thenReturn(habits);
        when(modelMapper.map(habits, listType)).thenReturn(habitResponses);

        ResponseEntity<List<CreateHabitResponse>> result = habitController.getAllHabits();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(habitResponses, result.getBody());
    }
    @Test
    void testGetHabitById() {
        long habitId = 1L;
        Habit habit = new Habit(1L,"test",false,new ArrayList<>(1));
        CreateHabitResponse habitResponse = new CreateHabitResponse(1L, "Test Habit");

        when(habitService.getHabitById(habitId)).thenReturn(habit);
        when(modelMapper.map(habit, CreateHabitResponse.class)).thenReturn(habitResponse);

        ResponseEntity<CreateHabitResponse> result = habitController.getHabitById(habitId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(habitResponse, result.getBody());
    }
    @Test
    void testUpdateHabit() {
        long habitId = 1L;
        CreateHabitRequest habitRequest = new CreateHabitRequest(1L, "test");
        Habit habit = new Habit(1L,"test",false,new ArrayList<>(1));
        CreateHabitResponse habitResponse = new CreateHabitResponse(1L, "Test Habit");

        when(habitService.updateHabit(habitId, habitRequest)).thenReturn(habit);
        when(modelMapper.map(habit, CreateHabitResponse.class)).thenReturn(habitResponse);

        ResponseEntity<CreateHabitResponse> result = habitController.updateHabit(habitId, habitRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(habitResponse, result.getBody());
    }
    @Test
    void testDeleteHabitById() {
        long habitId = 1L;

        doNothing().when(habitService).deleteHabitById(habitId);

        ResponseEntity<String> result = habitController.deleteHabitById(habitId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Habit with ID " + habitId + " has been deleted", result.getBody());

        verify(habitService).deleteHabitById(habitId);
    }
    @Test
    void testMarkHabitAsDone() {
        long habitId = 1L;

        doNothing().when(habitService).markHabitAsDone(habitId);

        ResponseEntity<String> result = habitController.markHabitAsDone(habitId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Habit with ID " + habitId + " has been marked as done", result.getBody());

        verify(habitService).markHabitAsDone(habitId);
    }
    }
