package com.edstem.habitTracker.repository;

import com.edstem.habitTracker.model.Habit;
import com.edstem.habitTracker.model.Timer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimerRepository extends JpaRepository<Timer, Long> {
    List<Timer> findByHabit(Habit habit);
}
