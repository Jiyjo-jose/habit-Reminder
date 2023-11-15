package com.edstem.habitTracker.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Duration;
import java.util.List;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(style = "HH:mm ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm ")
    private String reminderTime;

    private Duration interval;

}



