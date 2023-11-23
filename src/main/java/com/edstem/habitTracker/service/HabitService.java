package com.edstem.habitTracker.service;

import com.edstem.habitTracker.contract.Request.CreateHabitRequest;
import com.edstem.habitTracker.model.Habit;
import com.edstem.habitTracker.repository.HabitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;

    public Habit createHabit(CreateHabitRequest createHabitRequest) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper
                .typeMap(CreateHabitRequest.class, Habit.class)
                .addMappings(mapper -> mapper.map(src -> src.getInterval(), Habit::setInterval));

        modelMapper
                .typeMap(CreateHabitRequest.class, Habit.class)
                .addMappings(mapper -> mapper.map(src -> src.getEndDate(), Habit::setEndDate));

        Habit habit = modelMapper.map(createHabitRequest, Habit.class);
        return habitRepository.save(habit);
    }

    public Page<Habit> getAllHabits(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return habitRepository.findAll(pageable);
    }

    public void editHabit(Long habitId, CreateHabitRequest editHabitRequest) {
        Habit habit =
                habitRepository
                        .findById(habitId)
                        .orElseThrow(() -> new EntityNotFoundException("Habit not found"));

        habit.setName(editHabitRequest.getName());
        habit.setDescription(editHabitRequest.getDescription());
        habit.setReminderTime(editHabitRequest.getReminderTime());
        habit.setInterval(editHabitRequest.getInterval());
        habit.setEndDate(editHabitRequest.getEndDate());
        habitRepository.save(habit);
    }

    public void deleteHabitById(Long habitId) {
        Habit habit =
                habitRepository
                        .findById(habitId)
                        .orElseThrow(
                                () ->
                                        new EntityNotFoundException(
                                                "Habit not found with id: " + habitId));
        habitRepository.deleteById(habitId);
    }
}
