package io.github.yoshikawaa.app.domain.data;

import java.time.LocalDate;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class LocalDateBeanField extends AbstractBeanField<LocalDate> {

    @Override
    protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        return value == null ? null : LocalDate.parse(value);
    }

}
