package com.edstem.habitTracker.serviceTest;

import com.edstem.habitTracker.contract.Request.CreateHabitRequest;
import com.edstem.habitTracker.model.Habit;
import com.edstem.habitTracker.repository.HabitRepository;
import com.edstem.habitTracker.service.HabitService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HabitServiceTest {


    private  HabitRepository habitRepository;
    private  ModelMapper modelMapper ;
    private  HabitService habitService ;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        habitRepository = Mockito.mock(HabitRepository.class);
        modelMapper = new ModelMapper();
        habitService = new HabitService(modelMapper, habitRepository);
    }
    @Test

    void testCreateHabit() {

        CreateHabitRequest createHabitRequest = new CreateHabitRequest(1L,"test");
        Habit savedHabit = new Habit(1L,"test",false,new ArrayList<>(1));
        when(habitRepository.save(any(Habit.class))).thenReturn(savedHabit);
        Habit actualResponse = habitService.createHabit(createHabitRequest);
        assertNotNull(actualResponse);
        assertEquals(1L, actualResponse.getHabitId());
        assertEquals("test", actualResponse.getDescription());
        assertFalse(actualResponse.isDone());
    }
    @Test
    void testGetAllHabits() {
        Habit habit1 = new Habit(1L, "test 1", false,new ArrayList<>());
        Habit habit2 = new Habit(2L, "test 2", false,new ArrayList<>());
        List<Habit> habits = Arrays.asList(habit1, habit2);

        when(habitRepository.findAll()).thenReturn(habits);

        List<Habit> result = habitService.getAllHabits();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("test 1", result.get(0).getDescription());
        assertEquals("test 2", result.get(1).getDescription());
    }

    @Test
    void testGetHabitById() {
        long habitId = 1L;
        Habit habit = new Habit(1L, "test 1", false,new ArrayList<>());

        when(habitRepository.findById(habitId)).thenReturn(Optional.of(habit));

        Habit result = habitService.getHabitById(habitId);

        assertNotNull(result);
        assertEquals("test 1", result.getDescription());
    }
    @Test
    void testGetHabitById_WhenHabitNotFound() {
        long habitId = 1L;

        when(habitRepository.findById(habitId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            habitService.getHabitById(habitId);
        });
    }
    @Test
    void testUpdateHabit() {
        long habitId = 1L;
        String updatedDescription = "test";

        CreateHabitRequest habitRequest = new CreateHabitRequest(habitId, updatedDescription);
        Habit habit = new Habit(1L, "test", false, new ArrayList<>(2));

        when(habitRepository.findById(habitId)).thenReturn(Optional.of(habit));
        when(habitRepository.save(habit)).thenReturn(habit);

        Habit updatedHabit = habitService.updateHabit(habitId, habitRequest);

        assertNotNull(updatedHabit);
        assertEquals(habitId, updatedHabit.getHabitId());
        assertEquals(updatedDescription, updatedHabit.getDescription());
        assertFalse(updatedHabit.isDone());
    }
    @Test
    void testUpdateHabit_WhenHabitDoesNotExist() {
        long habitId = 1L;
        String updatedDescription = "test";

        CreateHabitRequest habitRequest = new CreateHabitRequest(habitId, updatedDescription);

        when(habitRepository.findById(habitId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> habitService.updateHabit(habitId, habitRequest));
    }
    @Test
    void testDeleteHabitById() {
        long habitId = 1L;
        Habit habit = new Habit();
        when(habitRepository.findById(habitId)).thenReturn(Optional.of(habit));
        habitService.deleteHabitById(habitId);
        verify(habitRepository, times(1)).deleteById(habitId);
    }


    @Test
    void testDeleteHabitById_WhenHabitDoesNotExist() {
        long habitId = 1L;
        when(habitRepository.findById(habitId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> habitService.deleteHabitById(habitId));
    }
    @Test
    void testMarkHabitAsDone() {
        long habitId = 1L;
        Habit habit = new Habit(1L,"test",false,new ArrayList<>(1));
        when(habitRepository.findById(habitId)).thenReturn(Optional.of(habit));
        habitService.markHabitAsDone(habitId);
        assertTrue(habit.isDone());
        verify(habitRepository, times(1)).save(habit);
    }
    @Test
    void testMarkHabitAsDoneWhenHabitNotFound() {
        long habitId = 1L;
        when(habitRepository.findById(habitId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> habitService.markHabitAsDone(habitId));
    }


}

