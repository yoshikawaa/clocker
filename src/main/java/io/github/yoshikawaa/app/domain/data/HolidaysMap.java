package io.github.yoshikawaa.app.domain.data;

import static java.util.stream.Collectors.toMap;

import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.HashMap;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import io.github.yoshikawaa.app.config.ClockerProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Component
@ConditionalOnProperty("clocker.holidays-file")
@Slf4j
public class HolidaysMap extends HashMap<LocalDate, String> implements InitializingBean {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ClockerProperties properties;

    @Override
    public void afterPropertiesSet() throws Exception {

        Resource resource = properties.getHolidaysFile();
        if (resource.exists()) {
            log.info("Reading Holidays File: '{}'", resource.getURI());
            try (Reader reader = new InputStreamReader(resource.getInputStream())) {
                CsvToBean<Holiday> csvToBean = new CsvToBeanBuilder<Holiday>(reader).withType(Holiday.class)
                        .withSkipLines(1)
                        .build();
                putAll(csvToBean.parse().stream().collect(toMap(Holiday::getDate, Holiday::getName)));
            }
        }
    }

    @Data
    public static class Holiday {
        @CsvCustomBindByPosition(position = 0, required = true, converter = LocalDateBeanField.class)
        private LocalDate date;
        @CsvBindByPosition(position = 1, required = true)
        private String name;
    }
}
