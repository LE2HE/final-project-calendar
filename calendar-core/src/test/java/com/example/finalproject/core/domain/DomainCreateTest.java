package com.example.finalproject.core.domain;

import com.example.finalproject.core.domain.entity.Schedule;
import com.example.finalproject.core.domain.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomainCreateTest {

    @Test
    void eventCreate() {
        final User me = new User("name", "email", "pw", LocalDate.now());
        final Schedule taskSchedule = Schedule.task("할일", "청소하기", LocalDateTime.now(), me);
        assertEquals(taskSchedule.getScheduleType(), ScheduleType.TASK);
        assertEquals(taskSchedule.toTask().getTitle(), "할일");
    }

}
