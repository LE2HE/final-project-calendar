package com.example.finalproject.api.util;

import com.example.finalproject.api.dto.EventDto;
import com.example.finalproject.api.dto.NotificationDto;
import com.example.finalproject.api.dto.ScheduleDto;
import com.example.finalproject.api.dto.TaskDto;
import com.example.finalproject.core.domain.entity.Schedule;
import com.example.finalproject.core.exception.CalendarException;
import com.example.finalproject.core.exception.ErrorCode;

public class DtoConverter {

    public static ScheduleDto fromSchedule(Schedule schedule) {
        switch (schedule.getScheduleType()) {
            case TASK:
                return TaskDto.builder()
                        .scheduleId(schedule.getId())
                        .taskAt(schedule.getStartAt())
                        .title(schedule.getTitle())
                        .description(schedule.getDescription())
                        .writerId(schedule.getWriter().getId())
                        .build();
            case EVENT:
                return EventDto.builder()
                        .scheduleId(schedule.getId())
                        .startAt(schedule.getStartAt())
                        .endAt(schedule.getEndAt())
                        .title(schedule.getTitle())
                        .description(schedule.getDescription())
                        .writerId(schedule.getWriter().getId())
                        .build();
            case NOTIFICATION:
                return NotificationDto.builder()
                        .scheduleId(schedule.getId())
                        .notifyAt(schedule.getStartAt())
                        .title(schedule.getTitle())
                        .writerId(schedule.getWriter().getId())
                        .build();
            default:
                throw new CalendarException(ErrorCode.BAD_REQUEST);
        }
    }

}