package com.edstem.habitReminder.repository;

import com.edstem.habitReminder.model.Habit;
import com.edstem.habitReminder.model.ReminderDays;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {

    @Query(
            "SELECT rd FROM Habit h JOIN h.reminderDays rd WHERE rd.completed = false AND"
                    + " rd.endDate >= :currentDate")
    List<ReminderDays> findActiveReminders(@Param("currentDate") LocalDate currentDate);
}
