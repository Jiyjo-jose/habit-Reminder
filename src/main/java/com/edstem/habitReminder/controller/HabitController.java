package com.edstem.habitReminder.controller;

import com.edstem.habitReminder.contract.Request.CreateHabitRequest;
import com.edstem.habitReminder.model.Habit;
import com.edstem.habitReminder.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/habit")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HabitController {

    private final HabitService habitService;

    @PostMapping("/createHabit")
    public ResponseEntity<Habit> createHabit(
            @RequestBody @Validated CreateHabitRequest createHabitRequest) {
        Habit createdHabit = habitService.createHabit(createHabitRequest);
        return new ResponseEntity<>(createdHabit, HttpStatus.CREATED);
    }

    @GetMapping("/viewHabit")
    public ResponseEntity<Page<Habit>> getAllHabits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Habit> habits = habitService.getAllHabits(page, size);
        return ResponseEntity.ok(habits);
    }

    @PutMapping("/{habitId}/editHabit")
    public ResponseEntity<String> editHabit(
            @PathVariable Long habitId, @RequestBody CreateHabitRequest editHabitRequest) {
        try {
            habitService.editHabit(habitId, editHabitRequest);
            return ResponseEntity.ok("Habit updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating habit");
        }
    }

    @DeleteMapping("/delete/{habitId}")
    public ResponseEntity<String> deleteHabitById(@PathVariable Long habitId) {
        habitService.deleteHabitById(habitId);
        return new ResponseEntity<>(
                "Habit with ID " + habitId + " has been deleted", HttpStatus.OK);
    }
}
