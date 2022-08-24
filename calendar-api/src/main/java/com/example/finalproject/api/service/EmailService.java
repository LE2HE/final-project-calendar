package com.example.finalproject.api.service;

import com.example.finalproject.core.domain.entity.Engagement;

public interface EmailService {
    void sendEngagement(Engagement engagement);
}
