package com.edstem.habitTracker.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;



    @Getter
    @Entity
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Setter
    public class Timer {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private Duration interval;

        @Temporal(TemporalType.TIME)
        @DateTimeFormat(style = "HH:mm ")
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm ")
        private String startTime;

        @ManyToOne
        @JoinColumn(name = "habit_id", referencedColumnName = "habitId")
        private Habit habit;
    }

