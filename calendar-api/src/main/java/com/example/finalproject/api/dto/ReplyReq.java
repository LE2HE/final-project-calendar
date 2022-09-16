package com.example.finalproject.api.dto;

import com.example.finalproject.core.domain.RequestReplyType;

public class ReplyReq {

    private RequestReplyType type;

    public ReplyReq() {}

    public ReplyReq(RequestReplyType type) {
        this.type = type;
    }

    public RequestReplyType getType() {
        return type;
    }

}
