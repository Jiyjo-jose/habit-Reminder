package com.edstem.habitReminder.service;

import com.edstem.habitReminder.model.Habit;
import com.edstem.habitReminder.model.ReminderDays;
import com.edstem.habitReminder.repository.HabitRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReminderEmailService {

    private final HabitRepository habitRepository;
    private final EmailService emailService;
    private final ThreadPoolTaskScheduler taskScheduler;

    @Scheduled(fixedRate = 50000)
    public void sendReminderEmails() {
        LocalDate currentDate = LocalDate.now();
        List<ReminderDays> reminders = habitRepository.findActiveReminders(currentDate);

        for (ReminderDays reminder : reminders) {
            scheduleEmail(reminder);
        }
    }

    void scheduleEmail(ReminderDays reminder) {
        Habit habit = reminder.getHabit();
        if (habit != null) {
            LocalTime reminderTime = habit.getReminderTime();

            String cronExpression =
                    String.format(
                            " %d %d %d * * ?",
                            reminderTime.getSecond(),
                            reminderTime.getMinute(),
                            reminderTime.getHour());
            taskScheduler.schedule(() -> sendEmail(reminder), new CronTrigger(cronExpression));
        } else {
            System.out.println("Cannot schedule email. Habit is null.");
        }
    }

    void sendEmail(ReminderDays reminder) {
        String userEmail = reminder.getHabit().getEmail();
        String habitDescription = reminder.getHabit().getDescription();
        String emailSubject = "Reminder: " + reminder.getHabit().getName();
        String emailBody = "Dear user,\n\nThis is a reminder for your habit: " + habitDescription;

        emailService.sendEmail(userEmail, emailSubject, emailBody);
    }
}
