package com.edstem.habitReminder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edstem.habitReminder.contract.request.CreateHabitRequest;
import com.edstem.habitReminder.contract.response.CreateHabitResponse;
import com.edstem.habitReminder.model.Habit;
import com.edstem.habitReminder.repository.HabitRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class HabitServiceTest {
    @MockBean private HabitRepository habitRepository;
    @MockBean private ModelMapper modelMapper;

    @Autowired private HabitService habitService;

    @Test
    void testCreateHabit() {
        CreateHabitRequest request = new CreateHabitRequest();
        Habit newHabit = new Habit();

        Habit savedHabit = new Habit();

        CreateHabitResponse expectedResponse = new CreateHabitResponse();

        when(modelMapper.map(request, Habit.class)).thenReturn(newHabit);
        when(habitRepository.save(newHabit)).thenReturn(savedHabit);
        when(modelMapper.map(savedHabit, CreateHabitResponse.class)).thenReturn(expectedResponse);

        CreateHabitResponse actualResponse = habitService.createHabit(request);

        assertEquals(expectedResponse, actualResponse);

        verify(modelMapper, times(1)).map(request, Habit.class);
        verify(modelMapper, times(1)).map(savedHabit, CreateHabitResponse.class);

        verify(habitRepository, times(1)).save(newHabit);
    }

    @Test
    void testGetAllHabits() {
        int page = 0;
        int size = 10;

        List<Habit> habits = Arrays.asList(new Habit(), new Habit());
        Page<Habit> habitPage = new PageImpl<>(habits);

        when(habitRepository.findAll(PageRequest.of(page, size))).thenReturn(habitPage);

        List<Habit> result = habitService.getAllHabits(page, size);

        assertEquals(habits, result);

        verify(habitRepository, times(1)).findAll(PageRequest.of(page, size));
    }

    @Test
    void testEditHabit() {

        Long habitId = 1L;
        CreateHabitRequest editHabitRequest = new CreateHabitRequest();
        Habit existingHabit = new Habit();
        Habit updatedHabit = new Habit();
        CreateHabitResponse expectedResponse = new CreateHabitResponse();
        when(habitRepository.findById(habitId)).thenReturn(Optional.of(existingHabit));
        when(modelMapper.map(updatedHabit, CreateHabitResponse.class)).thenReturn(expectedResponse);
        when(habitRepository.save(existingHabit)).thenReturn(updatedHabit);
        CreateHabitResponse actualResponse = habitService.editHabit(habitId, editHabitRequest);
        assertEquals(expectedResponse, actualResponse);
        verify(habitRepository, times(1)).findById(habitId);
        verify(habitRepository, times(1)).save(existingHabit);
        verify(modelMapper, times(1)).map(updatedHabit, CreateHabitResponse.class);
    }

    @Test
    void testEditHabit_HabitNotFound() {
        Long habitId = 1L;
        CreateHabitRequest editHabitRequest = new CreateHabitRequest();
        when(habitRepository.findById(habitId)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> habitService.editHabit(habitId, editHabitRequest));

        verify(habitRepository, times(1)).findById(habitId);

        verify(habitRepository, never()).save(any());
        verify(modelMapper, never()).map(any(), eq(CreateHabitResponse.class));
    }

    @Test
    void testDeleteHabitById() {
        Long habitId = 1L;
        Habit existingHabit = new Habit();
        existingHabit.setHabitId(habitId);

        Mockito.when(habitRepository.findById(habitId)).thenReturn(Optional.of(existingHabit));

        habitService.deleteHabitById(habitId);

        Mockito.verify(habitRepository, Mockito.times(1)).delete(existingHabit);
    }

    @Test
    void testDeleteHabitByIdNotFound() {

        Long habitId = 1L;
        Mockito.when(habitRepository.findById(habitId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> habitService.deleteHabitById(habitId));
    }
}
