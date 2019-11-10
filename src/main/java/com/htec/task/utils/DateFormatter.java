package com.htec.task.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date formatter util
 */

@Slf4j
public class DateFormatter {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

    public static Date parseDate(String date) throws ParseException {
        return formatter.parse(date);
    }
}
