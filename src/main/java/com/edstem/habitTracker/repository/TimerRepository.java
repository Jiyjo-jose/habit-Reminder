package com.edstem.habitTracker.repository;

import com.edstem.habitTracker.model.Habit;
import com.edstem.habitTracker.model.Timer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimerRepository extends JpaRepository<Timer, Long> {
    List<Timer> findByHabit(Habit habit);
}



