package com.edstem.habitReminder.controller;

import com.edstem.habitReminder.contract.request.CreateHabitRequest;
import com.edstem.habitReminder.contract.response.CreateHabitResponse;
import com.edstem.habitReminder.model.Habit;
import com.edstem.habitReminder.service.HabitService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class HabitController {

    private final HabitService habitService;

    @PostMapping("/createHabit")
    public ResponseEntity<CreateHabitResponse> createHabit(
            @RequestBody @Valid CreateHabitRequest request) {
        CreateHabitResponse createdHabit = habitService.createHabit(request);
        return new ResponseEntity<>(createdHabit, HttpStatus.CREATED);
    }

    @GetMapping("/viewHabit")
    public List<Habit> getAllHabits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return habitService.getAllHabits(page, size);
    }

    @PutMapping("/{habitId}/editHabit")
    public ResponseEntity<CreateHabitResponse> editHabit(
            @PathVariable Long habitId, @RequestBody @Valid CreateHabitRequest editHabitRequest) {
        CreateHabitResponse updatedHabit = habitService.editHabit(habitId, editHabitRequest);
        return ResponseEntity.ok(updatedHabit);
    }

    @DeleteMapping("/delete/{habitId}")
    public ResponseEntity<String> deleteHabit(@PathVariable Long habitId) {
        try {
            habitService.deleteHabitById(habitId);
            return ResponseEntity.ok("Habit deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Habit not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting habit");
        }
    }
}
