package com.example.finalproject.api.service;

import com.example.finalproject.api.dto.AuthUser;
import com.example.finalproject.api.dto.NotificationCreateReq;
import com.example.finalproject.core.domain.entity.Schedule;
import com.example.finalproject.core.domain.entity.User;
import com.example.finalproject.core.domain.entity.repository.ScheduleRepository;
import com.example.finalproject.core.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;

    public void create(NotificationCreateReq notificationCreateReq, AuthUser authUser) {
        final User user = userService.findByUserId(authUser.getId());
        final List<LocalDateTime> notifyAtList = notificationCreateReq.getRepeatTimes();
        notifyAtList.forEach(notifyAt -> {
            final Schedule notificationSchedule =
                    Schedule.notification(
                            notificationCreateReq.getTitle(),
                            notifyAt,
                            user
                    );
            scheduleRepository.save(notificationSchedule);
        });
    }

}
