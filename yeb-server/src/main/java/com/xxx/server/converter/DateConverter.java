package com.xxx.server.converter;


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Keafmd
 *
 * @ClassName: DataConverter
 * @Description: 日期转换
 * @author: liuchen
 * @date: 2022/6/17 17:13
 * @Blog:
 */
@Component//这里不知道为什么没用,具体使用在EmployeeController中
public class DateConverter implements Converter<String,LocalDate> {



    @Override
    public LocalDate convert(String source) {

        try {
            return LocalDate.parse(source,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }
}
