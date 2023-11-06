package com.edstem.habitTracker.service;

import com.edstem.habitTracker.contract.Request.CreateHabitRequest;
import com.edstem.habitTracker.model.Habit;
import com.edstem.habitTracker.repository.HabitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitService {

    private final ModelMapper modelMapper;
    private final HabitRepository habitRepository;



    public Habit createHabit(CreateHabitRequest createHabitRequest) {
        Habit habit = modelMapper.map(createHabitRequest, Habit.class);
        return habitRepository.save(habit);
    }

    public List<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

    public Habit getHabitById(Long habitId) {
        return habitRepository.findById(habitId)
                .orElseThrow(() -> new EntityNotFoundException("Habit not found with id: " + habitId));
    }

    public Habit updateHabit(Long habitId, CreateHabitRequest habitRequest) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new EntityNotFoundException("Habit not found with id: " + habitId));
        modelMapper.map(habitRequest, habit);
        return habitRepository.save(habit);
    }

    public void deleteHabitById(Long habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new EntityNotFoundException("Habit not found with id: " + habitId));
        habitRepository.deleteById(habitId);
    }

    public void markHabitAsDone(Long habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found with ID: " + habitId));
        habit.setDone(true);
        habitRepository.save(habit);
    }

}


