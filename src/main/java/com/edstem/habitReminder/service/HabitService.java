package com.edstem.habitReminder.service;

import com.edstem.habitReminder.contract.request.CreateHabitRequest;
import com.edstem.habitReminder.contract.response.CreateHabitResponse;
import com.edstem.habitReminder.model.Habit;
import com.edstem.habitReminder.repository.HabitRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HabitService {

    private final HabitRepository habitRepository;
    private final ModelMapper modelMapper;

    public CreateHabitResponse createHabit(CreateHabitRequest request) {
        Habit newHabit = modelMapper.map(request, Habit.class);
        Habit savedHabit = habitRepository.save(newHabit);
        CreateHabitResponse response = modelMapper.map(savedHabit, CreateHabitResponse.class);
        return response;
    }

    public List<Habit> getAllHabits(int page, int size) {
        Page<Habit> habitPage = habitRepository.findAll(PageRequest.of(page, size));
        return habitPage.getContent();
    }

    public CreateHabitResponse editHabit(Long habitId, CreateHabitRequest editHabitRequest) {
        Habit habit =
                habitRepository
                        .findById(habitId)
                        .orElseThrow(() -> new EntityNotFoundException("Habit not found"));
        habit.setName(editHabitRequest.getName());
        habit.setDescription(editHabitRequest.getDescription());
        habit.setEmail(editHabitRequest.getEmail());

        LocalTime reminderTime = editHabitRequest.getReminderTime();
        if (reminderTime != null) {
            habit.setReminderTime(reminderTime);
        }
        Habit updatedHabit = habitRepository.save(habit);
        CreateHabitResponse response = modelMapper.map(updatedHabit, CreateHabitResponse.class);
        return response;
    }

    public void deleteHabitById(Long habitId) {
        Habit habit =
                habitRepository
                        .findById(habitId)
                        .orElseThrow(() -> new EntityNotFoundException("Habit not found"));

        habitRepository.delete(habit);
    }
}
