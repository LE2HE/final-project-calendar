package com.example.finalproject.api.dto;

import com.example.finalproject.core.domain.RequestReplyType;

public class ReplyEngagementReq {

    private RequestReplyType type;

    public ReplyEngagementReq() {}

    public ReplyEngagementReq(RequestReplyType type) {
        this.type = type;
    }

    public RequestReplyType getType() {
        return type;
    }

}
