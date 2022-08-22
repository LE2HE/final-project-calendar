package com.example.finalproject.core.domain.entity;

import com.example.finalproject.core.domain.Event;
import com.example.finalproject.core.domain.RequestStatus;
import com.example.finalproject.core.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Table(name = "engagements")
@Entity
public class Engagement extends BaseEntity {

    @JoinColumn(name = "schedule_id")
    @ManyToOne
    private Schedule schedule;

    @JoinColumn(name = "attendee_id")
    @ManyToOne
    private User attendee;

    private RequestStatus requestStatus;
    
}
