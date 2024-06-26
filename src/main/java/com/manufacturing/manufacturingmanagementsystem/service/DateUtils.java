package com.manufacturing.manufacturingmanagementsystem.service;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
// Author: Pham Van Cao
// this class is used to define the methods that will be used to manipulate date
@Slf4j
public class DateUtils {
    private static DateUtils instance;
    public static final String FORMAT_DATE_DAY_MONTH_YEAR = "dd-MM-yyyy HH:mm:ss";
    public static final String FORMAT_DATE_DAY_MONTH_YEAR_ONLY = "dd-MM-yyyy";

    private DateUtils() {
    }
    // this method is used to get the instance of the DateUtils class
    public static DateUtils getInstance() {
        if (instance == null) {
            instance = new DateUtils();
        }
        return instance;
    }
    // this method is used to convert a local date to a date
    public static Date convertToDateViaInstant(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
    // this method is used to convert a date to a local date
    public static String formatDate(Date date) {
        return formatDate(date, FORMAT_DATE_DAY_MONTH_YEAR);
    }
    // this method is used to format a date
    public static String formatDate(Date date, String format) {
        SimpleDateFormat fm = new SimpleDateFormat(format);
        return fm.format(date);
    }
    // this method is used to convert a string to a date
    public static Date converDate(String date, String format) {

        try {
            SimpleDateFormat fm = new SimpleDateFormat(format);
            return fm.parse(date);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
    // this method is used to convert a string to a date
    public static Date converDate(String date) {

        try {
            SimpleDateFormat format = new SimpleDateFormat(SocialNetworkingConstant.DATE_TIME_FORMAT);
            return format.parse(date);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
    // this method is used to get the current date
    public static boolean isInRangeXMinutesAgo(Date date, int minutes) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        Instant minutesAgo = Instant.now().minus(Duration.ofMinutes(minutes));

        try {
            return minutesAgo.isBefore(instant);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
    // this method is used to check if the date is at least x seconds ago
    public static boolean isAtLeastXSecondsAgo(Date date, int seconds) {
        Instant instant = Instant.ofEpochMilli(date.getTime());
        Instant secondsAgo = Instant.now().minus(Duration.ofSeconds(seconds));

        try {
            return instant.isBefore(secondsAgo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
    // this method is used to get the current date
    public static Date startOfDay(Date date) {
        OffsetDateTime offsetDateTime = date.toInstant()
                .atOffset(ZoneOffset.UTC);
        OffsetDateTime reallyStartOfDay = offsetDateTime.withHour(0).withMinute(0).withSecond(0).withNano(000000000);

        return Date.from(reallyStartOfDay.toLocalDateTime().toInstant(ZoneOffset.UTC));

    }
    // this method is used to convert a local date to a date
    public static Date convertLocalDate2Date(LocalDate localDate) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }
    // this method is used to convert a date to a local date
    public static LocalDate convertDate2LocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    // this method is used to get the end of the day
    public static Date endOfDay(Date date) {
        OffsetDateTime offsetDateTime = date.toInstant()
                .atOffset(ZoneOffset.UTC);
        OffsetDateTime reallyEndOfDay = offsetDateTime.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return Date.from(reallyEndOfDay.toLocalDateTime().toInstant(ZoneOffset.UTC));
    }

    /**
     * Get start of day by timezone and date
     *
     * @param sourceDate
     * @param timeZone
     * @return Date
     * @throws ParseException
     */
    public static Date startOfDayUTC(Date sourceDate, TimeZone timeZone) throws ParseException {
        SimpleDateFormat simpleDateFormatUtc = new SimpleDateFormat("dd.MM.yyyy");

        SimpleDateFormat targetTimezoneFormat = new SimpleDateFormat(FORMAT_DATE_DAY_MONTH_YEAR);
        targetTimezoneFormat.setTimeZone(timeZone);

        //parser date utc truoc, vi tren he thong dang set gio utc
        String dateSource = simpleDateFormatUtc.format(sourceDate) + " 00:00:00";
        simpleDateFormatUtc.applyPattern(FORMAT_DATE_DAY_MONTH_YEAR);


        Calendar calendar = new GregorianCalendar();
        calendar.setTime(targetTimezoneFormat.parse(dateSource));
        calendar.set(Calendar.MILLISECOND, 0);

        String utc = simpleDateFormatUtc.format(calendar.getTime());

        return simpleDateFormatUtc.parse(utc);

    }

    //system time is UTC, return date utc, sourceDate -> utc

    /**
     * Get end of day by timezone and date
     *
     * @param sourceDate
     * @param timeZone
     * @return Date
     * @throws ParseException
     */
    public static Date endOfDayUTC(Date sourceDate, TimeZone timeZone) throws ParseException {
        SimpleDateFormat simpleDateFormatUtc = new SimpleDateFormat("dd.MM.yyyy");

        SimpleDateFormat targetTimezoneFormat = new SimpleDateFormat(FORMAT_DATE_DAY_MONTH_YEAR);
        targetTimezoneFormat.setTimeZone(timeZone);

        //parser date utc truoc, vi tren he thong dang set gio utc
        String dateSource = simpleDateFormatUtc.format(sourceDate) + " 23:59:59";
        simpleDateFormatUtc.applyPattern(FORMAT_DATE_DAY_MONTH_YEAR);


        Calendar calendar = new GregorianCalendar();
        calendar.setTime(targetTimezoneFormat.parse(dateSource));
        calendar.set(Calendar.MILLISECOND, 0);

        String utc = simpleDateFormatUtc.format(calendar.getTime());

        return simpleDateFormatUtc.parse(utc);
    }

    //system time is UTC, return date is utc too

    /**
     * Get current store date by timezone
     *
     * @param timeZone
     * @return Date
     * @throws ParseException
     */
    public static Date getCurrentStoreDate(TimeZone timeZone) throws ParseException {
        Date utcDate = new Date();

        SimpleDateFormat targetTimezoneFormat = new SimpleDateFormat(FORMAT_DATE_DAY_MONTH_YEAR);
        targetTimezoneFormat.setTimeZone(timeZone);
        String targetDate = targetTimezoneFormat.format(utcDate);

        SimpleDateFormat simpleDateFormatUtc = new SimpleDateFormat(FORMAT_DATE_DAY_MONTH_YEAR);
        return simpleDateFormatUtc.parse(targetDate);
    }

    public static String getOffset(TimeZone timeZone) {
        Calendar cal = GregorianCalendar.getInstance(timeZone);
        int offsetInMillis = timeZone.getOffset(cal.getTimeInMillis());
        String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
        offset = (offsetInMillis >= 0 ? "+" : "-") + offset;
        return offset;
    }

    //system is UTC, convert from utc to

    /**
     * Convert date to utc
     *
     * @param source
     * @param oldTimeZone
     * @return
     * @throws ParseException
     */
    public static Date convertToUtc(Date source, TimeZone oldTimeZone) throws ParseException {
        SimpleDateFormat simpleDateFormatUtc = new SimpleDateFormat(SocialNetworkingConstant.DATE_TIME_FORMAT);

        SimpleDateFormat oldTimezoneFormat = new SimpleDateFormat(SocialNetworkingConstant.DATE_TIME_FORMAT);
        String date = oldTimezoneFormat.format(source);
        oldTimezoneFormat.setTimeZone(oldTimeZone);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(oldTimezoneFormat.parse(date));

        String utc = simpleDateFormatUtc.format(calendar.getTime());

        return simpleDateFormatUtc.parse(utc);
    }
}