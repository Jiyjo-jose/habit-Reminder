package com.edstem.habitTracker.service;

import com.edstem.habitTracker.contract.Request.TimerRequest;
import com.edstem.habitTracker.contract.Response.TimerResponse;
import com.edstem.habitTracker.model.Habit;
import com.edstem.habitTracker.model.Timer;
import com.edstem.habitTracker.repository.HabitRepository;
import com.edstem.habitTracker.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimerService {

    private final ModelMapper modelMapper;
    private final TimerRepository timerRepository;
    private final HabitRepository habitRepository;
    public TimerResponse createTimer(Long habitId, TimerRequest timerRequest) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found with id: " + habitId));
        Timer timer = modelMapper.map(timerRequest, Timer.class);
        timer.setHabit(habit);
        Timer savedTimer = timerRepository.save(timer);
        return modelMapper.map(savedTimer,TimerResponse.class);
    }
    public List<TimerResponse> getAllTimers(Long habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found with id: " + habitId));
        List<Timer> timers = timerRepository.findByHabit(habit);
        return timers.stream()
                .map(timer -> modelMapper.map(timer, TimerResponse.class))
                .collect(Collectors.toList());
    }

    public TimerResponse getTimerById(Long id) {
        Timer timer = timerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Timer not found with id: " + id));
        return modelMapper.map(timer, TimerResponse.class);
    }

    public TimerResponse updateTimer(Long habitId, Long timerId, TimerRequest timerRequest) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found with id: " + habitId));

        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new RuntimeException("Timer not found with id: " + timerId));
        timer.setName(timerRequest.getName());
        timer.setInterval(timerRequest.getInterval());
        timer.setStartTime(timerRequest.getStartTime());
        Timer updatedTimer = timerRepository.save(timer);

        return modelMapper.map(updatedTimer, TimerResponse.class);
    }
    public void deleteTimer(Long habitId, Long timerId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Habit not found with id: " + habitId));

        Timer timer = timerRepository.findById(timerId)
                .orElseThrow(() -> new RuntimeException("Timer not found with id: " + timerId));

        timerRepository.delete(timer);
    }


}

