package com.example.finalproject.api.service;

import com.example.finalproject.api.dto.AuthUser;
import com.example.finalproject.core.domain.RequestReplyType;
import com.example.finalproject.core.domain.RequestStatus;
import com.example.finalproject.core.domain.entity.repository.EngagementRepository;
import com.example.finalproject.core.exception.CalendarException;
import com.example.finalproject.core.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EngagementService {

    private final EngagementRepository engagementRepository;

    @Transactional
    public RequestStatus update(AuthUser authUser, Long engagementId, RequestReplyType type) {
        return engagementRepository.findById(engagementId)
                .filter(e -> e.getRequestStatus() == RequestStatus.REQUESTED)
                .filter(e -> e.getAttendee().getId().equals(authUser.getId()))
                .map(e -> e.reply(type))
                .orElseThrow(() -> new CalendarException(ErrorCode.BAD_REQUEST))
                .getRequestStatus();
    }

}
