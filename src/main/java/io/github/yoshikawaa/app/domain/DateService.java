package io.github.yoshikawaa.app.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.yoshikawaa.app.config.ClockerProperties;
import io.github.yoshikawaa.app.domain.data.HolidaysMap;

@Service
public class DateService {

    @Autowired
    private ClockerProperties clocker;

    @Autowired(required = false)
    private HolidaysMap holidaysMap;

    public long getRemainingTime() {

        LocalDateTime now = LocalDateTime.now();

        long remainingTimeOfToday = getRemainingTimeOfToday(now.toLocalTime());
        long remainingTimeAfterTomorrow = getRemainingTimeAfterTomorrow(now.toLocalDate(), clocker.getLimitDate());

        return remainingTimeOfToday + remainingTimeAfterTomorrow;
    }

    private long getRemainingTimeOfToday(LocalTime now) {

        LocalTime businessStart = clocker.getBusiness().getStart();
        LocalTime businessEnd = clocker.getBusiness().getEnd();
        LocalTime intervalStart = clocker.getInterval().getStart();
        LocalTime intervalEnd = clocker.getInterval().getEnd();

        long interval = ChronoUnit.SECONDS.between(intervalStart, intervalEnd);

        return now.isBefore(businessStart) ? ChronoUnit.SECONDS.between(businessStart, businessEnd) - interval
                : now.isBefore(intervalStart) ? ChronoUnit.SECONDS.between(now, businessEnd) - interval
                        : now.isBefore(intervalEnd) ? ChronoUnit.SECONDS.between(intervalEnd, businessEnd)
                                : now.isBefore(businessEnd) ? ChronoUnit.SECONDS.between(now, businessEnd) : 0;
    }

    private long getRemainingTimeAfterTomorrow(LocalDate now, LocalDate due) {

        long businessDays = LongStream.rangeClosed(1, ChronoUnit.DAYS.between(now, due))
                .mapToObj(d -> now.plusDays(d))
                .filter(d -> isBusinessDay(d))
                .count();
        return ChronoUnit.SECONDS.between(now.atStartOfDay(), now.plusDays(businessDays).atStartOfDay());
    }

    private boolean isBusinessDay(LocalDate date) {

        DayOfWeek dow = date.getDayOfWeek();
        return dow != DayOfWeek.SATURDAY && dow != DayOfWeek.SUNDAY
                && (holidaysMap == null || !holidaysMap.containsKey(date));
    }

}
