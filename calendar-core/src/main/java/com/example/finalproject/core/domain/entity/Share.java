package com.example.finalproject.core.domain.entity;

import com.example.finalproject.core.domain.Event;
import com.example.finalproject.core.domain.RequestReplyType;
import com.example.finalproject.core.domain.RequestStatus;
import com.example.finalproject.core.util.Period;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "shares")
@Entity
public class Share extends BaseEntity {

    private Long fromUserId;
    private Long toUserId;

    @Enumerated(value = EnumType.STRING)
    private RequestStatus requestStatus;

    @Enumerated(value = EnumType.STRING)
    private Direction direction;


    public Share reply(RequestReplyType type) {
        switch (type) {
            case ACCEPT:
                this.requestStatus = RequestStatus.ACCEPTED;
                break;
            case REJECT:
                this.requestStatus = RequestStatus.REJECTED;
                break;
        }
        return this;
    }

    public enum Direction {
        BI_DIRECTION, UNI_DIRECTION
    }

}
