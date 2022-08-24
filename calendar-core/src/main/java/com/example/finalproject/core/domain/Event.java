package com.example.finalproject.core.domain;

import com.example.finalproject.core.domain.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
public class Event {
    private Schedule schedule;

    public Event(Schedule schedule) {
        this.schedule = schedule;
    }

    public boolean isOverlapped(LocalDateTime startAt, LocalDateTime endAt) {
        return schedule.getStartAt().isBefore(endAt) && startAt.isBefore(schedule.getEndAt());
    }
}
