package com.example.finalproject.api.service;

import com.example.finalproject.api.dto.AuthUser;
import com.example.finalproject.api.dto.ScheduleDto;
import com.example.finalproject.api.dto.SharedScheduleDto;
import com.example.finalproject.api.util.DtoConverter;
import com.example.finalproject.core.domain.entity.Engagement;
import com.example.finalproject.core.domain.entity.Schedule;
import com.example.finalproject.core.domain.entity.repository.EngagementRepository;
import com.example.finalproject.core.domain.entity.repository.ScheduleRepository;
import com.example.finalproject.core.service.UserService;
import com.example.finalproject.core.util.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScheduleQueryService {

    private final UserService userService;
    private final ShareService shareService;
    private final ScheduleRepository scheduleRepository;
    private final EngagementRepository engagementRepository;

    public List<ScheduleDto> getScheduleByDay(AuthUser authUser, LocalDate date) {
        return getScheduleByPeriod(authUser, Period.of(date, date));
    }

    public List<ScheduleDto> getScheduleByWeek(AuthUser authUser, LocalDate startOfWeek) {
        return getScheduleByPeriod(authUser,
                Period.of(startOfWeek, startOfWeek.plusDays(6)));
    }

    public List<ScheduleDto> getScheduleByMonth(AuthUser authUser, YearMonth yearMonth) {
        return getScheduleByPeriod(authUser,
                Period.of(yearMonth.atDay(1), yearMonth.atEndOfMonth()));
    }

    private List<ScheduleDto> getScheduleByPeriod(AuthUser authUser, Period period) {
        return Stream.concat(
                        scheduleRepository.findAllByWriter_Id(authUser.getId())
                                .stream()
                                .filter(schedule -> schedule.isOverlapped(period))
                                .map(DtoConverter::fromSchedule),
                        engagementRepository.findAllByAttendee_Id(authUser.getId())
                                .stream()
                                .filter(engagement -> engagement.isOverlapped(period))
                                .map(engagement -> DtoConverter.fromSchedule(engagement.getSchedule())))
                .collect(Collectors.toList());
    }

    public List<SharedScheduleDto> getSharedScheduleByDay(AuthUser authUser, LocalDate date) {
        return getSharedScheduleFunction(
                authUser,
                (Long userId) -> getScheduleByDay(AuthUser.of(userId), date));
    }

    public List<SharedScheduleDto> getSharedScheduleByWeek(AuthUser authUser, LocalDate startOfWeek) {
        return getSharedScheduleFunction(
                authUser,
                (Long userId) -> getScheduleByWeek(AuthUser.of(userId), startOfWeek));
    }

    public List<SharedScheduleDto> getSharedScheduleByMonth(AuthUser authUser, YearMonth yearMonth) {
        return getSharedScheduleFunction(
                authUser,
                (Long userId) -> getScheduleByMonth(AuthUser.of(userId), yearMonth));
    }

    public List<SharedScheduleDto> getSharedScheduleFunction(
            AuthUser authUser,
            Function<Long, List<ScheduleDto>> function
    ) {
        return Stream.concat(shareService.findSharedUserIdByUser(authUser).stream(), Stream.of(authUser.getId()))
                .map(userId -> SharedScheduleDto.builder()
                        .userId(userId)
                        .name(userService.findByUserId(userId).getName())
                        .me(userId.equals(authUser.getId()))
                        .schedules(function.apply(userId))
                        .build())
                .collect(Collectors.toList());
    }

}
