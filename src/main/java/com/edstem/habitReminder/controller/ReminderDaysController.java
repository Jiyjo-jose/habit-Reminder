package com.edstem.habitReminder.controller;

import com.edstem.habitReminder.contract.request.AddReminderDaysRequest;
import com.edstem.habitReminder.contract.response.AddReminderDaysResponse;
import com.edstem.habitReminder.model.ReminderDays;
import com.edstem.habitReminder.service.ReminderDaysService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class ReminderDaysController {

    private final ReminderDaysService reminderDaysService;

    @PostMapping("/{habitId}/createReminderDays")
    public ResponseEntity<AddReminderDaysResponse> addReminderDaysToHabit(
            @PathVariable Long habitId,
            @RequestBody @Valid AddReminderDaysRequest reminderDaysRequest) {

        try {
            AddReminderDaysResponse response =
                    reminderDaysService.addReminderDaysToHabit(
                            habitId,
                            reminderDaysRequest.getReminderDays(),
                            reminderDaysRequest.getEndDate());

            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{habitId}/reminderDays/{reminderDayId}/complete")
    public ResponseEntity<String> completeReminderDay(
            @PathVariable Long habitId, @PathVariable Long reminderDayId) {
        reminderDaysService.completeReminderDay(habitId, reminderDayId);
        return ResponseEntity.ok("Reminder day marked as completed successfully");
    }

    @GetMapping("/{habitId}/allReminderDays")
    public ResponseEntity<List<ReminderDays>> getAllReminderDays(@PathVariable Long habitId) {
        List<ReminderDays> allReminderDays = reminderDaysService.getAllReminderDays(habitId);
        return ResponseEntity.ok(allReminderDays);
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
}
