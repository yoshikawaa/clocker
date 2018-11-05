package io.github.yoshikawaa.app.config;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Component
@ConfigurationProperties("clocker")
@Validated
@Data
public class ClockerProperties {
    private Resource holidaysFile;
    @NotNull
    private LocalDate limitDate;
    private TimeProperties business;
    private TimeProperties interval;
    
    @Data
    public static class TimeProperties {
        @NotNull
        LocalTime start;
        @NotNull
        LocalTime end;
    }
}
