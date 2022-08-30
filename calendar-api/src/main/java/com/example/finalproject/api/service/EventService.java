package com.example.finalproject.api.service;

import com.example.finalproject.api.dto.AuthUser;
import com.example.finalproject.api.dto.EventCreateReq;
import com.example.finalproject.core.domain.RequestStatus;
import com.example.finalproject.core.domain.entity.Engagement;
import com.example.finalproject.core.domain.entity.Schedule;
import com.example.finalproject.core.domain.entity.User;
import com.example.finalproject.core.domain.entity.repository.EngagementRepository;
import com.example.finalproject.core.domain.entity.repository.ScheduleRepository;
import com.example.finalproject.core.domain.entity.repository.UserRepository;
import com.example.finalproject.core.exception.CalendarException;
import com.example.finalproject.core.exception.ErrorCode;
import com.example.finalproject.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EmailService emailService;
    private final UserService userService;
    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;

    @Transactional
    public void create(EventCreateReq eventCreateReq, AuthUser authUser) {
        final List<Engagement> engagementList = engagementRepository.findAll(); //TODO findAll 금지 개선

        if (engagementList.stream().
                anyMatch(e -> eventCreateReq.getAttendeeIds().contains(e.getAttendee().getId())
                        && e.getRequestStatus() == RequestStatus.ACCEPTED
                        && e.getEvent().isOverlapped(eventCreateReq.getStartAt(), eventCreateReq.getEndAt()))
        ) {
            throw new CalendarException(ErrorCode.EVENT_CREATE_OVERLAPPED_PERIOD);
        }
        final Schedule eventSchedule = Schedule.event(
                eventCreateReq.getTitle(),
                eventCreateReq.getDescription(),
                eventCreateReq.getStartAt(),
                eventCreateReq.getEndAt(),
                userService.findByUserId(authUser.getId())
        );
        scheduleRepository.save(eventSchedule);
        eventCreateReq.getAttendeeIds()
                .forEach(atId -> {
                    final User attendee = userService.findByUserId(atId);
                    final Engagement engagement = Engagement.builder()
                            .schedule(eventSchedule)
                            .requestStatus(RequestStatus.REQUESTED)
                            .attendee(attendee)
                            .build();
                    engagementRepository.save(engagement);
                    emailService.sendEngagement(engagement);
                });

    }

}
