package com.example.finalproject.core.domain;

import com.example.finalproject.core.domain.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class Notification {
    private Schedule schedule;

    public Notification(Schedule schedule) {
        this.schedule = schedule;
    }

}
