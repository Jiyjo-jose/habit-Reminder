package com.edstem.habitTracker.controller;

import com.edstem.habitTracker.contract.Request.AddReminderDaysRequest;
import com.edstem.habitTracker.model.ReminderDays;
import com.edstem.habitTracker.service.ReminderDaysService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/habit")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReminderDaysController {

    private final ReminderDaysService reminderDaysService;

    @PostMapping("/{habitId}/createReminderDays")
    public ResponseEntity<?> addReminderDaysToHabit(
            @PathVariable Long habitId,
            @RequestBody AddReminderDaysRequest addReminderDaysRequest) {
        try {
            reminderDaysService.addReminderDaysToHabit(
                    habitId, addReminderDaysRequest.getReminderDays());
            return ResponseEntity.ok("Reminder days added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding reminder days");
        }
    }

    @PutMapping("/{habitId}/reminderDays/{reminderDayId}/complete")
    public ResponseEntity<String> completeReminderDay(
            @PathVariable Long habitId, @PathVariable Long reminderDayId) {
        reminderDaysService.completeReminderDay(habitId, reminderDayId);
        return ResponseEntity.ok("Reminder day marked as completed successfully");
    }

    @GetMapping("/{habitId}/completedReminderDays")
    public ResponseEntity<List<ReminderDays>> getCompletedReminderDays(@PathVariable Long habitId) {
        List<ReminderDays> completedReminderDays =
                reminderDaysService.getCompletedReminderDays(habitId);
        return ResponseEntity.ok(completedReminderDays);
    }

    @GetMapping("/{habitId}/incompleteReminderDays")
    public ResponseEntity<List<ReminderDays>> getIncompleteReminderDays(
            @PathVariable Long habitId) {
        List<ReminderDays> incompleteReminderDays =
                reminderDaysService.getIncompleteReminderDays(habitId);
        return ResponseEntity.ok(incompleteReminderDays);
    }

    @DeleteMapping("/{habitId}/reminderDays/{reminderDayId}")
    public ResponseEntity<String> deleteReminderDay(
            @PathVariable Long habitId, @PathVariable Long reminderDayId) {
        try {
            reminderDaysService.deleteReminderDay(habitId, reminderDayId);
            return ResponseEntity.ok("Reminder day deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reminder day not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting reminder day");
        }
    }
}
