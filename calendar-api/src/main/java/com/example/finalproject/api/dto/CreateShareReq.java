package com.example.finalproject.api.dto;

import com.example.finalproject.core.domain.entity.Share;
import lombok.Data;

@Data
public class CreateShareReq {
    private final Long toUserId;
    private final Share.Direction direction;
}
