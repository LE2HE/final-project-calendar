package com.example.finalproject.api.service;

import com.example.finalproject.api.dto.AuthUser;
import com.example.finalproject.api.dto.CreateShareReq;
import com.example.finalproject.core.domain.RequestReplyType;
import com.example.finalproject.core.domain.RequestStatus;
import com.example.finalproject.core.domain.entity.Share;
import com.example.finalproject.core.domain.entity.User;
import com.example.finalproject.core.domain.entity.repository.ShareRepository;
import com.example.finalproject.core.exception.CalendarException;
import com.example.finalproject.core.exception.ErrorCode;
import com.example.finalproject.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ShareService {

    private final UserService userService;
    private final ShareRepository shareRepository;
    private final EmailService emailService;

    @Transactional
    public void createShare(AuthUser authUser, CreateShareReq req) {
        final User fromUser = userService.findByUserId(authUser.getId());
        final User toUser = userService.findByUserId(req.getToUserId());
        shareRepository.save(Share.builder()
                        .fromUserId(fromUser.getId())
                        .toUserId(toUser.getId())
                        .direction(req.getDirection())
                        .requestStatus(RequestStatus.REQUESTED)
                        .build());
        emailService.sendShareRequestMail(toUser.getEmail(), fromUser.getName(), req.getDirection());
    }

    @Transactional
    public void replyToShareRequest(Long shareId, AuthUser toAuthUser, RequestReplyType type) {
        shareRepository.findById(shareId)
                .filter(share -> share.getToUserId().equals(toAuthUser.getId()))
                .filter(share -> share.getRequestStatus() == RequestStatus.REQUESTED)
                .map(share -> share.reply(type))
                .orElseThrow(() -> new CalendarException(ErrorCode.BAD_REQUEST));
    }

    @Transactional
    public List<Long> findSharedUserIdByUser(AuthUser authUser) {
        final Stream<Long> biDirectionShares =
                shareRepository.findAllByBiDirection(
                        authUser.getId(),
                        RequestStatus.ACCEPTED,
                        Share.Direction.BI_DIRECTION
                ).stream().map(s -> s.getToUserId().equals(authUser.getId()) ? s.getFromUserId() : s.getToUserId());
        final Stream<Long> uniDirectionShares =
                shareRepository.findAllByToUserIdAndRequestStatusAndDirection(
                        authUser.getId(),
                        RequestStatus.ACCEPTED,
                        Share.Direction.UNI_DIRECTION
                ).stream().map(Share::getFromUserId);
        return Stream.concat(biDirectionShares, uniDirectionShares).collect(Collectors.toList());
    }
}
