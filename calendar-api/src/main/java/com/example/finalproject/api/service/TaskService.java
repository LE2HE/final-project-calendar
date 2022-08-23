package com.example.finalproject.api.service;

import com.example.finalproject.api.dto.AuthUser;
import com.example.finalproject.api.dto.TaskCreateReq;
import com.example.finalproject.core.domain.entity.Schedule;
import com.example.finalproject.core.domain.entity.repository.ScheduleRepository;
import com.example.finalproject.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final UserService userService;
    private final ScheduleRepository scheduleRepository;

    public void create(TaskCreateReq taskCreateReq, AuthUser authUser) {
        final Schedule taskSchedule = Schedule.task(
                taskCreateReq.getTitle(),
                taskCreateReq.getDescription(),
                taskCreateReq.getTaskAt(),
                userService.findByUserId(authUser.getId())
        );
        scheduleRepository.save(taskSchedule);
    }

}
