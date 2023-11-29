package com.edstem.habitReminder.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long habitId;

    private String name;

    private String description;

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL)
    private List<ReminderDays> reminderDays;

    private LocalTime reminderTime;

    private String email;
}
