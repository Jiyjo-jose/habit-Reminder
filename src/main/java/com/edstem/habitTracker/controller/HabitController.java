package com.edstem.habitTracker.controller;

import com.edstem.habitTracker.contract.Request.CreateHabitRequest;
import com.edstem.habitTracker.contract.Response.CreateHabitResponse;
import com.edstem.habitTracker.model.Habit;
import com.edstem.habitTracker.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/habit")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;
    private final ModelMapper modelMapper;
    @PostMapping("/create")
    public ResponseEntity<CreateHabitResponse> createHabit(@RequestBody CreateHabitRequest createHabitRequest) {
        Habit habit = habitService.createHabit(createHabitRequest);
        CreateHabitResponse habitResponse = modelMapper.map(habit, CreateHabitResponse.class);
        return ResponseEntity.ok(habitResponse);
    }

    @GetMapping("/view")
    public ResponseEntity<List<CreateHabitResponse>> getAllHabits() {
        List<Habit> habits = habitService.getAllHabits();
        Type listType = new TypeToken<List<CreateHabitResponse>>() {}.getType();
        List<CreateHabitResponse> habitResponses = modelMapper.map(habits, listType);
        return ResponseEntity.ok(habitResponses);
    }

    @GetMapping("/{habitId}")
    public ResponseEntity<CreateHabitResponse> getHabitById(@PathVariable Long habitId) {
        Habit habit = habitService.getHabitById(habitId);
        CreateHabitResponse habitResponse = modelMapper.map(habit, CreateHabitResponse.class);
        return new ResponseEntity<>(habitResponse, HttpStatus.OK);
    }

    @PatchMapping("/{habitId}")
    public ResponseEntity<CreateHabitResponse> updateHabit(@PathVariable Long habitId, @RequestBody CreateHabitRequest habitRequest) {
        Habit habit = habitService.updateHabit(habitId, habitRequest);
        CreateHabitResponse habitResponse = modelMapper.map(habit, CreateHabitResponse.class);
        return new ResponseEntity<>(habitResponse, HttpStatus.OK);
    }
    @DeleteMapping("/{habitId}")
    public ResponseEntity<String> deleteHabitById(@PathVariable Long habitId) {
        habitService.deleteHabitById(habitId);
        return new ResponseEntity<>("Habit with ID " + habitId + " has been deleted", HttpStatus.OK);
    }
    @PutMapping("/{habitId}/done")
    public ResponseEntity<String> markHabitAsDone(@PathVariable Long habitId) {
        habitService.markHabitAsDone(habitId);
        return new ResponseEntity<>("Habit with ID " + habitId + " has been marked as done", HttpStatus.OK);
    }


}
