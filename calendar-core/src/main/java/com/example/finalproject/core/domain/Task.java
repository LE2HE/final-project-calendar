package com.example.finalproject.core.domain;

import com.example.finalproject.core.domain.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class Task {
    private Schedule schedule;

    public Task(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getTitle() {
        return schedule.getTitle();
    }

}
