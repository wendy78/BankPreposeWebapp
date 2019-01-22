package com.iawtr.commons.util;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Wally 2008-05-03
 * 
 * 日期帮助类
 */
public class TimeHelper {
    private final static HashMap<String, String> leapYear = new HashMap<String, String>();

    static {
        leapYear.put("0", "31");
        leapYear.put("1", "29");
        leapYear.put("2", "31");
        leapYear.put("3", "30");
        leapYear.put("4", "31");
        leapYear.put("5", "30");
        leapYear.put("6", "31");
        leapYear.put("7", "31");
        leapYear.put("8", "30");
        leapYear.put("9", "31");
        leapYear.put("10", "30");
        leapYear.put("11", "31");
    }
    private final static HashMap<String, String> calmYear = new HashMap<String, String>();

    static {
        calmYear.put("0", "31");
        calmYear.put("1", "28");
        calmYear.put("2", "31");
        calmYear.put("3", "30");
        calmYear.put("4", "31");
        calmYear.put("5", "30");
        calmYear.put("6", "31");
        calmYear.put("7", "31");
        calmYear.put("8", "30");
        calmYear.put("9", "31");
        calmYear.put("10", "30");
        calmYear.put("11", "31");
    }
    /**
     * 得到当前的年份
     * 返回格式:yyyy
     * @return String
     */
    public static String getCurrentYear() {
        java.util.Date NowDate = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return formatter.format(NowDate);
    }

    /**
     * 得到当前的月份
     * 返回格式:MM
     * @return String
     */
    public static String getCurrentMonth() {
        java.util.Date NowDate = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return formatter.format(NowDate);
    }

    /**
     * 得到当前的日期
     * 返回格式:dd
     * @return String
     */
    public static String getCurrentDay() {
        java.util.Date NowDate = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return formatter.format(NowDate);
    }

    /**
     * 得到当前的时间，精确到秒,共14位
     * 返回格式:yyyyMMddHHmmss
     * @return String
     */
    public static String getCurrentTime14() {
        Date NowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return formatter.format(NowDate);
    }

    /**
     * 将传入的字符串用给定的格式解析成日期
     * @param date   
     * @param dateFormat
     * @return
     */
    private static Date strToDate(String date, String dateFormat) {
        if (!validateDate(date)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, DateFormatSymbols.getInstance(Locale.US));
        try {
            return formatter.parse(date);
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    /**
     * 将日期型转换成给定格式的字符串
     * @param date
     * @param dateFormat 
     * @return   
     */
    public static String dateToStr(Date date, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, DateFormatSymbols.getInstance(Locale.US));
        return formatter.format(date);
    }

    /**
     * 得到当前的时间加上输入年后的时间，精确到毫秒,共14位
     * 返回格式:yyyyMMddHHmmss
     * @return String
     */
    public static String getCurrentTimeAddYear(int addyear) {
        String currentYear = "";
        Date NowDate = new Date();
        currentYear = TimeHelper.getCurrentYear();
        currentYear = String.valueOf(Integer.parseInt(TimeHelper.getCurrentYear()) + addyear);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return formatter.format(NowDate);
    }

    /**
     * 得到当前的日期,共8位
     * 返回格式：yyyyMMdd
     * @return String
     */
    public static String getCurrentDate() {
        Date NowDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return formatter.format(NowDate);
    }
    /**
     * 转换时间字符转的格式 
     * @param originaldate   2008年08月08日 必须与原始数据格式一致
     * @param  originalDateFormat     原始数据格式   yyyy年MM月dd日
     * @param  targetDateFormt    目标数据格式    yyyyMMdd
     * @return    20080808   目标日期
     */
    public static String convertDataFormat(String originaldate, String originalDateFormat, String targetDateFormat) {
        System.out.println("要转换的字符串为:" + originaldate);
        SimpleDateFormat formatter = new SimpleDateFormat(originalDateFormat, DateFormatSymbols.getInstance(Locale.US));
        Date targetDate = null;
        try {
            targetDate = formatter.parse(originaldate);
        } catch (ParseException ex) {
            Logger.getLogger(TimeHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        formatter = new SimpleDateFormat(targetDateFormat, DateFormatSymbols.getInstance(Locale.US));
        return formatter.format(targetDate).toUpperCase();

    }

    /**
     * 得到两个时间的差
     * @param date1  yyyyMMdd 
     * @param date2  yyyyMMdd
     * @return   两个日期之间的天数
     */
    public static String getDifferenceDay(String date1, String date2) {
        String result = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", DateFormatSymbols.getInstance(Locale.US));
        Date date1tm = null;
        Date date2tm = null;
        try {
            date1tm = formatter.parse(date1);
            date2tm = formatter.parse(date2);
        } catch (ParseException ex) {
            Logger.getLogger(TimeHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (date1.compareTo(date2) > 0) {
            result = String.valueOf((date1tm.getTime() - date2tm.getTime()) / (1000 * 60 * 60 * 24));
        } else {
            result = String.valueOf((date2tm.getTime() - date1tm.getTime()) / (1000 * 60 * 60 * 24));
        }
        return result;
    }

    /**
     * 将字符串转换为日期
     * @param value
     * @return
     */
    public static Date strToDate(String value) {
        if (!validateDate(value)) {
            return null;
        }
        Date result = null;
        if (value.trim().length() == 8) {
            result = TimeHelper.strToDate(value.trim(), "yyyyMMdd");
        }
        if (value.trim().length() == 12) {
            result = TimeHelper.strToDate(value.trim(), "yyyyMMddHHmm");
        }
        if (value.trim().length() == 14) {
            result = TimeHelper.strToDate(value.trim(), "yyyyMMddHHmmss");
        }
        return result;
    }

    /**
     * 验证输入的字符串日期是否合法,8位,10位,12位或者14位,如:20091022235923
     * @param value   要验证的字符串日期
     * @return
     */
    public static boolean validateDate(String value) {
        if (value == null || (value.trim().length() != 8 && value.trim().length() != 12) && value.trim().length() != 14) {
            return false;
        }
        value = value.trim();

        int year = StrToInt(value.substring(0, 4));
        int month = StrToInt(value.substring(4, 6));
        if (month > 12 || month < 0) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);

        int day = StrToInt(value.substring(6, 8));
        if (day <= 0 || day > 31) {
            return false;
        }
        if (day > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            return false;
        }
        if (value.length() == 12) {
            int hour = StrToInt(value.substring(8, 10));
            if (hour < 0 || hour >= 24) {
                return false;
            }
            int minute = StrToInt(value.substring(10, 12));
            if (minute < 0 || minute >= 60) {
                return false;
            }
        }
        if (value.length() == 14) {
            int secend = StrToInt(value.substring(12, 14));
            if (secend < 0 || secend > 60) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串转化为int
     * @param value
     * @return
     */
    private static int StrToInt(String value) {
        if (value == null || value.trim().length() == 0) {
            return 0;
        }
        return Integer.valueOf(value).intValue();
    }


    public static void main(String args[]) {
    }
}
