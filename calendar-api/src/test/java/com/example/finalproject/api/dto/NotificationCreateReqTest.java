package com.example.finalproject.api.dto;

import com.example.finalproject.core.util.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotificationCreateReqTest {

    @Test
    @DisplayName("RepeatTime")
    void test() {
        final LocalDateTime startAt = LocalDateTime.of(2020, 7, 1, 12, 0, 0);

        final NotificationCreateReq notificationCreateReq = new NotificationCreateReq(
                "title",
                startAt,
                new NotificationCreateReq.RepeatInfo(new NotificationCreateReq.Interval(1, TimeUnit.MONTH), 3)
        );

        final LocalDateTime endAt = LocalDateTime.of(2020, 9, 1, 12, 0, 0);
        final LocalDateTime midAt = LocalDateTime.of(2020, 8, 1, 12, 0, 0);

        final List<LocalDateTime> times = new ArrayList<>();

        times.add(startAt);
        times.add(midAt);
        times.add(endAt);

        assertEquals(times, notificationCreateReq.getRepeatTimes());
    }

}