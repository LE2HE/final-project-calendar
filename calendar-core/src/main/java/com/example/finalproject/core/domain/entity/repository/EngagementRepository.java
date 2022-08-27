package com.example.finalproject.core.domain.entity.repository;

import com.example.finalproject.core.domain.entity.Engagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngagementRepository extends JpaRepository<Engagement, Long> {

    public List<Engagement> findAllByAttendee_Id(Long userId);

}
