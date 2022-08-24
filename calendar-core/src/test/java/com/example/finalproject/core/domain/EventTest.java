package com.example.finalproject.core.domain;

import com.example.finalproject.core.domain.entity.Schedule;
import com.example.finalproject.core.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventTest {

    @Test
    @DisplayName("OverlappedTest")
    void test1() {
        final LocalDateTime startAt = LocalDateTime.of(2020, 7, 1, 0, 0, 0);
        final LocalDateTime endAt = LocalDateTime.of(2020, 7, 1, 0, 0, 10);

        final Event event = Schedule.event(
                        "title",
                        "desc",
                        startAt,
                        endAt,
                        User.builder().build())
                .toEvent();

        // 과거
        final LocalDateTime testStartAt1 = LocalDateTime.of(2020, 6, 1, 0, 0, 1);
        final LocalDateTime testEndAt1 = LocalDateTime.of(2020, 6, 1, 0, 0, 10);

        // end
        final LocalDateTime testStartAt2 = LocalDateTime.of(2020, 6, 1, 0, 0, 0);
        final LocalDateTime testEndAt2 = LocalDateTime.of(2020, 7, 1, 0, 0, 10);

        // start
        final LocalDateTime testStartAt3 = LocalDateTime.of(2020, 7, 1, 0, 0, 0);
        final LocalDateTime testEndAt3 = LocalDateTime.of(2020, 8, 1, 0, 0, 10);

        // 미래
        final LocalDateTime testStartAt4 = LocalDateTime.of(2020, 8, 1, 0, 0, 0);
        final LocalDateTime testEndAt4 = LocalDateTime.of(2020, 8, 1, 0, 0, 10);

        // 감싸짐
        final LocalDateTime testStartAt5 = LocalDateTime.of(2020, 6, 1, 0, 0, 0);
        final LocalDateTime testEndAt5 = LocalDateTime.of(2020, 8, 1, 0, 0, 10);

        // 안에 포함
        final LocalDateTime testStartAt6 = LocalDateTime.of(2020, 7, 1, 0, 0, 0);
        final LocalDateTime testEndAt6 = LocalDateTime.of(2020, 7, 1, 0, 0, 10);

        assertFalse(event.isOverlapped(testStartAt1, testEndAt1));
        assertTrue(event.isOverlapped(testStartAt2, testEndAt2));
        assertTrue(event.isOverlapped(testStartAt3, testEndAt3));
        assertFalse(event.isOverlapped(testStartAt4, testEndAt4));
        assertTrue(event.isOverlapped(testStartAt5, testEndAt5));
        assertTrue(event.isOverlapped(testStartAt6, testEndAt6));

    }

}
