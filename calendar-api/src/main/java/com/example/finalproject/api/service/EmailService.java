package com.example.finalproject.api.service;

import com.example.finalproject.api.controller.BatchController;
import com.example.finalproject.api.dto.EngagementEmailStuff;
import com.example.finalproject.core.domain.entity.Share;

public interface EmailService {
    void sendEngagement(EngagementEmailStuff stuff);
    void sendAlarmMail(BatchController.SendMailBatchReq req);
    void sendShareRequestMail(String email, String name, Share.Direction direction);
}
