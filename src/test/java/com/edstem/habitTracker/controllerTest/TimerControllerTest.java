package com.edstem.habitTracker.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.edstem.habitTracker.contract.Request.TimerRequest;
import com.edstem.habitTracker.contract.Response.TimerResponse;
import com.edstem.habitTracker.controller.TimerController;
import com.edstem.habitTracker.service.TimerService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class TimerControllerTest {

    @Mock private TimerService timerService;

    @InjectMocks private TimerController timerController;

    @Test
    void testCreateTimer() {
        TimerRequest request = new TimerRequest(1L, "test", null, "10:30");
        TimerResponse response = new TimerResponse(1L, "test", null, "10:30");
        long habitId = 1L;
        when(timerService.createTimer(habitId, request)).thenReturn(response);

        ResponseEntity<TimerResponse> result = timerController.createTimer(habitId, request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testGetAllTimers() {
        long habitId = 1L;
        List<TimerResponse> responseList = Collections.singletonList(new TimerResponse());

        when(timerService.getAllTimers(habitId)).thenReturn(responseList);

        ResponseEntity<List<TimerResponse>> result = timerController.getAllTimers(habitId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
    }

    @Test
    void testGetTimerById() {
        long timerId = 1L;
        TimerResponse response = new TimerResponse(1L, "test", null, "10:30");

        when(timerService.getTimerById(timerId)).thenReturn(response);

        ResponseEntity<TimerResponse> result = timerController.getTimerById(timerId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testUpdateTimer() {
        long habitId = 1L;
        long timerId = 1L;
        TimerRequest request = new TimerRequest(1L, "test", null, "10:30");
        TimerResponse response = new TimerResponse(1L, "test", null, "10:30");

        when(timerService.updateTimer(habitId, timerId, request)).thenReturn(response);

        ResponseEntity<TimerResponse> result =
                timerController.updateTimer(habitId, timerId, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testDeleteTimer() {
        long habitId = 1L;
        long timerId = 1L;

        doNothing().when(timerService).deleteTimer(habitId, timerId);

        ResponseEntity<String> result = timerController.deleteTimer(habitId, timerId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Timer with id " + timerId + " has been deleted", result.getBody());

        verify(timerService).deleteTimer(habitId, timerId);
    }
}
