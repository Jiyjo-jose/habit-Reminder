// package com.edstem.habitReminder.service;
//
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertSame;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyLong;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;
//
// import com.edstem.habitReminder.contract.request.CreateHabitRequest;
// import com.edstem.habitReminder.model.Habit;
// import com.edstem.habitReminder.repository.HabitRepository;
// import jakarta.persistence.EntityNotFoundException;
// import java.time.LocalDate;
// import java.time.ZoneOffset;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Date;
// import java.util.List;
// import java.util.Optional;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.Pageable;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
//
// @ContextConfiguration(classes = {HabitService.class})
// @ExtendWith(SpringExtension.class)
// @SpringBootTest
// class HabitServiceTest {
//    @MockBean private HabitRepository habitRepository;
//
//    @Autowired private HabitService habitService;
//
//    @Test
//    void testCreateHabit() {
//        Habit habit = new Habit();
//        habit.setDescription("The characteristics of someone or something");
//        habit.setEndDate(
//                Date.from(
//                        LocalDate.of(1970, 1, 1)
//                                .atStartOfDay()
//                                .atZone(ZoneOffset.UTC)
//                                .toInstant()));
//        habit.setHabitId(1L);
//        habit.setName("Name");
//        habit.setReminderDays(new ArrayList<>());
//        habit.setReminderTime("Reminder Time");
//        when(habitRepository.save(Mockito.<Habit>any())).thenReturn(habit);
//        Habit actualCreateHabitResult = habitService.createHabit(new CreateHabitRequest());
//        verify(habitRepository).save(Mockito.<Habit>any());
//        assertSame(habit, actualCreateHabitResult);
//    }
//
//    @Test
//    void getAllHabits_ReturnsPageOfHabits() {
//
//        List<Habit> habits = Arrays.asList(new Habit(), new Habit());
//
//        Page<Habit> habitPage = new PageImpl<>(habits);
//
//        when(habitRepository.findAll(any(Pageable.class))).thenReturn(habitPage);
//        Page<Habit> result = habitService.getAllHabits(0, 10);
//        assertEquals(habitPage, result);
//        verify(habitRepository, times(1)).findAll(any(Pageable.class));
//    }
//
//    @Test
//    void testEditHabit() {
//
//        Long habitId = 1L;
//        CreateHabitRequest editHabitRequest = new CreateHabitRequest();
//        Habit existingHabit = new Habit();
//        Mockito.when(habitRepository.findById(anyLong())).thenReturn(Optional.of(existingHabit));
//        habitService.editHabit(habitId, editHabitRequest);
//        Mockito.verify(habitRepository, Mockito.times(1)).findById(habitId);
//        Mockito.verify(habitRepository, Mockito.times(1)).save(existingHabit);
//        assertEquals(editHabitRequest.getName(), existingHabit.getName());
//        assertEquals(editHabitRequest.getDescription(), existingHabit.getDescription());
//        assertEquals(editHabitRequest.getReminderTime(), existingHabit.getReminderTime());
//        assertEquals(editHabitRequest.getInterval(), existingHabit.getInterval());
//        assertEquals(editHabitRequest.getEndDate(), existingHabit.getEndDate());
//    }
//
//    @Test
//    void testEditHabit_whenHabitNotFound_shouldThrowEntityNotFoundException() {
//        Long habitId = 1L;
//        CreateHabitRequest editHabitRequest = new CreateHabitRequest();
//        Mockito.when(habitRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        assertThrows(
//                EntityNotFoundException.class,
//                () -> {
//                    habitService.editHabit(habitId, editHabitRequest);
//                });
//        Mockito.verify(habitRepository, Mockito.times(1)).findById(habitId);
//
//        Mockito.verify(habitRepository, Mockito.never()).save(Mockito.any(Habit.class));
//    }
//
//    @Test
//    void testDeleteHabitById_whenHabitNotFound_shouldThrowEntityNotFoundException() {
//
//        Long habitId = 1L;
//        Mockito.when(habitRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        assertThrows(
//                EntityNotFoundException.class,
//                () -> {
//                    habitService.deleteHabitById(habitId);
//                });
//        Mockito.verify(habitRepository, Mockito.times(1)).findById(habitId);
//        Mockito.verify(habitRepository, Mockito.never()).deleteById(habitId);
//    }
//
//    @Test
//    void testDeleteHabitById_whenHabitFound_shouldDeleteHabit() {
//
//        Long habitId = 1L;
//        Habit existingHabit = new Habit();
//        Mockito.when(habitRepository.findById(anyLong())).thenReturn(Optional.of(existingHabit));
//        habitService.deleteHabitById(habitId);
//        Mockito.verify(habitRepository, Mockito.times(1)).findById(habitId);
//        Mockito.verify(habitRepository, Mockito.times(1)).deleteById(habitId);
//    }
// }
