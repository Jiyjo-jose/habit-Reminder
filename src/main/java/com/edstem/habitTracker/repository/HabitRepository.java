package com.edstem.habitTracker.repository;

import com.edstem.habitTracker.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    @Modifying
    @Query(
            "DELETE FROM ReminderDays r WHERE r.habit.habitId = :habitId AND r.reminderDayId ="
                    + " :reminderDayId")
    void deleteReminderDay(
            @Param("habitId") Long habitId, @Param("reminderDayId") Long reminderDayId);
}
