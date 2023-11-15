package com.edstem.habitTracker.repository;
import com.edstem.habitTracker.model.ReminderDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReminderDaysRepository extends JpaRepository<ReminderDays, Long> {

}

