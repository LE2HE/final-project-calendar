package com.example.finalproject.api.controller;

import com.example.finalproject.api.dto.AuthUser;
import com.example.finalproject.api.dto.TaskCreateReq;
import com.example.finalproject.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.example.finalproject.api.service.LoginService.LOGIN_SESSION_KEY;

@RequiredArgsConstructor
@RequestMapping("/api/schedules")
@RestController
public class ScheduleController {

    private TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask(@RequestBody TaskCreateReq taskCreateReq,
                                           HttpSession session) {
        final Long userId = (Long) session.getAttribute(LOGIN_SESSION_KEY);
        if (userId == null) {
            throw new RuntimeException("bad request. no session");
        }
        taskService.create(taskCreateReq, AuthUser.of(userId));
        return ResponseEntity.ok().build();
    }

}
