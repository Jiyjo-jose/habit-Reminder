//package com.edstem.habitTracker.serviceTest;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.edstem.habitTracker.contract.Request.TimerRequest;
//import com.edstem.habitTracker.contract.Response.TimerResponse;
//import com.edstem.habitTracker.model.Habit;
//import com.edstem.habitTracker.model.Timer;
//import com.edstem.habitTracker.repository.HabitRepository;
//import com.edstem.habitTracker.repository.TimerRepository;
//import com.edstem.habitTracker.service.TimerService;
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.modelmapper.ModelMapper;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class TimerServiceTest {
//    @Mock private HabitRepository habitRepository;
//
//    @Mock private TimerRepository timerRepository;
//
//    @Mock private ModelMapper modelMapper;
//
//    @InjectMocks private TimerService timerService;
//
//    @Test
//    void testCreateTimer() {
//        Habit habit = new Habit();
//        habit.setDescription("Test");
//        habit.setDone(true);
//        habit.setHabitId(1L);
//        habit.setTimers(new ArrayList<>());
//
//        Timer timer = new Timer();
//        timer.setHabit(habit);
//        timer.setId(1L);
//        timer.setName("test");
//        timer.setStartTime("Start Time");
//        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Timer>>any())).thenReturn(timer);
//        when(timerRepository.save(Mockito.<Timer>any()))
//                .thenThrow(new RuntimeException("exception"));
//
//        Habit habit2 = new Habit();
//        habit2.setDescription("test2");
//        habit2.setDone(true);
//        habit2.setHabitId(1L);
//        habit2.setTimers(new ArrayList<>());
//        Optional<Habit> ofResult = Optional.of(habit2);
//        when(habitRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
//        assertThrows(
//                RuntimeException.class,
//                () ->
//                        timerService.createTimer(
//                                1L, new TimerRequest(1L, "test", null, "Start Time")));
//        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<Timer>>any());
//        verify(habitRepository).findById(Mockito.<Long>any());
//        verify(timerRepository).save(Mockito.<Timer>any());
//    }
//
//    @Test
//    void testGetAllTimers() {
//        when(timerRepository.findByHabit(Mockito.<Habit>any())).thenReturn(new ArrayList<>());
//
//        Habit habit = new Habit();
//        habit.setDescription("Test");
//        habit.setDone(true);
//        habit.setHabitId(1L);
//        habit.setTimers(new ArrayList<>());
//        Optional<Habit> ofResult = Optional.of(habit);
//        when(habitRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
//        List<TimerResponse> actualAllTimers = timerService.getAllTimers(1L);
//        verify(timerRepository).findByHabit(Mockito.<Habit>any());
//        verify(habitRepository).findById(Mockito.<Long>any());
//        assertTrue(actualAllTimers.isEmpty());
//    }
//
//    @Test
//    void testGetAllTimerHabitNotFound() {
//
//        Long habitId = 1L;
//        when(habitRepository.findById(habitId)).thenReturn(Optional.empty());
//        assertThrows(RuntimeException.class, () -> timerService.getAllTimers(habitId));
//    }
//
//    @Test
//    void testGetTimerById() {
//        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<TimerResponse>>any()))
//                .thenReturn(
//                        TimerResponse.builder()
//                                .id(1L)
//                                .interval(null)
//                                .name("test")
//                                .startTime("10:30")
//                                .build());
//
//        Habit habit = new Habit();
//        habit.setDescription("Test");
//        habit.setDone(true);
//        habit.setHabitId(1L);
//        habit.setTimers(new ArrayList<>());
//
//        Timer timer = new Timer();
//        timer.setHabit(habit);
//        timer.setId(1L);
//        timer.setName("test");
//        timer.setStartTime("10:30");
//        Optional<Timer> ofResult = Optional.of(timer);
//        when(timerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
//        timerService.getTimerById(1L);
//        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<TimerResponse>>any());
//        verify(timerRepository).findById(Mockito.<Long>any());
//    }
//
//    @Test
//    void testGetTimerByIdTimerNotFound() {
//
//        Long timerId = 1L;
//        when(timerRepository.findById(timerId)).thenReturn(Optional.empty());
//        assertThrows(RuntimeException.class, () -> timerService.getTimerById(timerId));
//    }
//
//    @Test
//    void testUpdateTimer() {
//        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<TimerResponse>>any()))
//                .thenReturn(
//                        TimerResponse.builder()
//                                .id(1L)
//                                .interval(null)
//                                .name("test")
//                                .startTime("10:30")
//                                .build());
//
//        Habit habit = new Habit();
//        habit.setDescription("Test");
//        habit.setDone(true);
//        habit.setHabitId(1L);
//        habit.setTimers(new ArrayList<>());
//
//        Timer timer = new Timer();
//        timer.setHabit(habit);
//        timer.setId(1L);
//        timer.setName("test");
//        timer.setStartTime("10:30");
//        Optional<Timer> ofResult = Optional.of(timer);
//
//        Habit habit2 = new Habit();
//        habit2.setDescription("Test");
//        habit2.setDone(true);
//        habit2.setHabitId(1L);
//        habit2.setTimers(new ArrayList<>());
//
//        Timer timer2 = new Timer();
//        timer2.setHabit(habit2);
//        timer2.setId(1L);
//        timer2.setName("test");
//        timer2.setStartTime("10:30");
//        when(timerRepository.save(Mockito.<Timer>any())).thenReturn(timer2);
//        when(timerRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
//
//        Habit habit3 = new Habit();
//        habit3.setDescription("Test");
//        habit3.setDone(true);
//        habit3.setHabitId(1L);
//        habit3.setTimers(new ArrayList<>());
//        Optional<Habit> ofResult2 = Optional.of(habit3);
//        when(habitRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
//        timerService.updateTimer(1L, 1L, new TimerRequest(1L, "Name", null, "10:30"));
//        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<TimerResponse>>any());
//        verify(habitRepository).findById(Mockito.<Long>any());
//        verify(timerRepository).findById(Mockito.<Long>any());
//        verify(timerRepository).save(Mockito.<Timer>any());
//    }
//
//    @Test
//    void testUpdateTimerExceptions() {
//        Long habitId = 1L;
//        Long timerId = 1L;
//        TimerRequest timerRequest =
//                new TimerRequest(1L, "updatedName", Duration.ofHours(5), "12:00");
//        when(habitRepository.findById(habitId))
//                .thenThrow(new RuntimeException("Habit not found with id: " + habitId));
//        when(timerRepository.findById(timerId))
//                .thenThrow(new RuntimeException("Timer not found with id: " + timerId));
//        assertThrows(
//                RuntimeException.class,
//                () -> timerService.updateTimer(habitId, timerId, timerRequest));
//    }
//
//    @Test
//    void testDeleteTimer() {
//        long habitId = 1L;
//        long timerId = 1L;
//        Habit habit = new Habit(1L, "test", false, new ArrayList<>(1));
//        Timer timer = new Timer(1L, "test", null, "10:30", habit);
//        when(habitRepository.findById(habitId)).thenReturn(Optional.of(habit));
//        when(timerRepository.findById(timerId)).thenReturn(Optional.of(timer));
//        assertDoesNotThrow(() -> timerService.deleteTimer(habitId, timerId));
//    }
//}
