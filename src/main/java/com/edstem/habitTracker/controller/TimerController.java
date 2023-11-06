package com.edstem.habitTracker.controller;

import com.edstem.habitTracker.contract.Request.TimerRequest;
import com.edstem.habitTracker.contract.Response.TimerResponse;
import com.edstem.habitTracker.service.TimerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
    @RequestMapping("/timer")
    @RequiredArgsConstructor
    public class TimerController {

        private final TimerService timerService;
        private final ModelMapper modelMapper;
        @PostMapping("/{habitId}/timers")
        public ResponseEntity<TimerResponse> createTimer(@PathVariable Long habitId, @RequestBody TimerRequest timerRequest) {
            TimerResponse timerResponse = timerService.createTimer(habitId, timerRequest);
            return new ResponseEntity<>(timerResponse, HttpStatus.CREATED);
        }

    @GetMapping("{habitId}/timers")
    public ResponseEntity<List<TimerResponse>> getAllTimers(@PathVariable Long habitId) {
        List<TimerResponse> timerResponses = timerService.getAllTimers(habitId);
        return new ResponseEntity<>(timerResponses, HttpStatus.OK);
    }
    @GetMapping("/{habitId}/timers/{timerId}")
    public ResponseEntity<TimerResponse> getTimerById(@PathVariable Long timerId) {
        TimerResponse timerResponse = timerService.getTimerById(timerId);
        return new ResponseEntity<>(timerResponse, HttpStatus.OK);
    }
    @PatchMapping("/{habitId}/timers/{timerId}")
    public ResponseEntity<TimerResponse> updateTimer(
            @PathVariable Long habitId,
            @PathVariable Long timerId,
            @RequestBody TimerRequest timerRequest
    ) {
        TimerResponse updatedTimer = timerService.updateTimer(habitId, timerId, timerRequest);
        return new ResponseEntity<>(updatedTimer, HttpStatus.OK);
    }
    @DeleteMapping("/{habitId}/timers/{timerId}")
    public ResponseEntity<String> deleteTimer(@PathVariable Long habitId, @PathVariable Long timerId) {
        timerService.deleteTimer(habitId, timerId);
        return new ResponseEntity<>("Timer with id " + timerId + " has been deleted", HttpStatus.OK);
    }
    }




