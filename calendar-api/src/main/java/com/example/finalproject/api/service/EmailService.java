package com.example.finalproject.api.service;

import com.example.finalproject.api.controller.BatchController;
import com.example.finalproject.api.dto.EngagementEmailStuff;

public interface EmailService {
    void sendEngagement(EngagementEmailStuff stuff);
    void sendAlarmMail(BatchController.SendMailBatchReq req);
}
