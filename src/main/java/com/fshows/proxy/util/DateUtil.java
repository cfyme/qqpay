package com.fshows.proxy.util;

import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 类DateUtil.java的实现描述：TODO 类实现描述
 *
 * @author zhh 2015年4月21日 下午9:00:57
 */
public class DateUtil {

    private final static Logger log = LoggerFactory.getLogger(DateUtil.class);

    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Format Date into which format you define
     *
     * @param date(java.util.Date)
     * @return String example formatDate(date, "MMMM dd, yyyy") = July 20, 2000
     */
    public static String formatDate(Date date, String newFormat) {
        if ((date == null) || (newFormat == null)) {
            return null;
        }

        SimpleDateFormat formatter = new SimpleDateFormat(newFormat);

        return formatter.format(date);
    }

    /**
     * 获取当前时间一小时后的时间
     */
    public static Date getOneHourAfterTime() {
        return DateTime.now().plusHours(1).toDate();
    }

    /**
     * 两个时间比较;d1>d2?true:false 出现异常就返回false
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean dateCompare(Date d1, Date d2) {
        if (d1 == null || d2 == null)
            return false;
        try {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(d1);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(d2);

            long p1 = c1.getTimeInMillis();
            long p2 = c2.getTimeInMillis();
            if (p1 > p2)
                return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * 获得当前时间的全字符串yyyyMMddhhmmss
     *
     * @return
     */
    public static String getNowDateTimeStr() {

        return DateTime.now().toString("yyyyMMddHHmmss");
    }

    /**
     * 获得当前时间的字符串yyyyMMdd
     *
     * @return
     */
    public static String getNowDateStr() {

        return DateTime.now().toString("yyyyMMdd");
    }

    /**
     * 获得当前时间的int类型yyyyMMdd
     *
     * @return
     */
    public static int getNowDate() {

        return Integer.valueOf(DateTime.now().toString("yyyyMMdd"));
    }


    /**
     * 获得当前时间的int类型yyyyMMdd
     *
     * @return
     */
    public static int getYesterdayDate() {

        return Integer.valueOf(DateTime.now().plusDays(-1).toString("yyyyMMdd"));
    }

    /**
     * 获得当前时间的字符串yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNowDateFormat() {

        return DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获得当前时间的字符串自定义转换类型
     *
     * @return
     */
    public static String getNowDateFormat(String format) {

        return DateTime.now().toString(format);
    }

    /**
     * 获得当前时间戳
     *
     * @return
     */
    public static long getNow() {

        return DateTime.now().getMillis();
    }

    /**
     * 获取当前时间附近小时的时间
     */
    public static Date getTimeByHours(int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    /**
     * 传入的日期是否在3个月内不包括今天
     *
     * @param createDay
     * @return
     */
    public static boolean isScopeByThreeMonth(Integer createDay) {

        Integer today = Integer.valueOf(DateTime.now().toString("yyyyMMdd"));
        Integer threeMonth = Integer.valueOf(DateTime.now().plusMonths(-3).toString("yyyyMMdd"));

        return createDay >= threeMonth && createDay < today;
    }

    /**
     * 传入的日期是否在3个月内包括今天
     *
     * @param createDay
     * @return
     */
    public static boolean isScopeByThreeMonthAndToday(Integer createDay) {

        Integer today = Integer.valueOf(DateTime.now().toString("yyyyMMdd"));
        Integer threeMonth = Integer.valueOf(DateTime.now().plusMonths(-3).toString("yyyyMMdd"));

        return createDay >= threeMonth && createDay <= today;
    }

    /**
     * 把时间字符串转时间戳
     *
     * @param time
     * @return
     */
    public static long formatMillis(String time, String formatType) {
        DateFormat format = new SimpleDateFormat(formatType);
        Date date;
        try {
            date = format.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            return 0L;
        }
    }

    /**
     * 获得特定时间的时间戳
     *
     * @return
     */
    public static long getMillisByString(String time, String forMate) {
        return DateTimeFormat.forPattern(forMate).parseDateTime(time).getMillis();
    }

    /**
     * 获得特定时间戳的格式化字符串
     *
     * @return
     */
    public static String getStringByMillis(long millis, String formatType) {

        return new DateTime(millis).toString(formatType);
    }

    /**
     * Map: 开始时间 key = startTime  结束时间 key = endTime
     * 获得当天的开始与结束时间
     *
     * @return
     */
    public static Map<String, Long> getCurrentStartAndEnd() {

        return getStartAndEndByDay(DateTime.now().toString("yyyyMMdd"));
    }

    /**
     * Map: 开始时间 key = startTime  结束时间 key = endTime
     *
     * @return
     */
    public static Map<String, Long> getYesterdayStartAndEnd() {

        return getStartAndEndByDay(DateTime.now().plusDays(-1).toString("yyyyMMdd"));
    }

    /**
     * Map: 开始时间 key = startTime  结束时间 key = endTime
     * 获得指定时间的开始与结束时间
     *
     * @param day
     * @return
     */
    public static Map<String, Long> getStartAndEndByDay(String day) {

        DateTime dayTime = DateTime.parse(day, DateTimeFormat.forPattern("yyyyMMdd"));
        // 获得昨天开始与结束时间
        long startTime = dayTime.withTimeAtStartOfDay().getMillis();
        long endTime = dayTime.plusDays(1).withTimeAtStartOfDay().getMillis();

        Map<String, Long> params = Maps.newHashMap();
        params.put("startTime", startTime);
        params.put("endTime", endTime);

        return params;
    }

    /**
     * 根据订单号获得订单日日期
     *
     * @param orderSn
     * @return
     */
    public static int getDateByOrderSn(String orderSn) {
        String date = orderSn.substring(0, 8);
        return Integer.valueOf(DateTimeFormat.forPattern("yyyyMMdd").parseDateTime(date).toString("yyyyMMdd"));
    }

    /**
     * 当前时间是否在指定时间内
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isInTime(long startTime, long endTime) {

        long nowTime = DateTime.now().getMillis();
        return nowTime >= startTime && nowTime <= endTime;
    }

    /**
     * 当前时间是否在指定时间内
     *
     * @return
     */
    public static boolean isInTimeForHours(int startHours, int endHours) {

        DateTime now = DateTime.now();
        long startTime = now.withTimeAtStartOfDay().withHourOfDay(startHours).getMillis();
        long endTime = now.withTimeAtStartOfDay().withHourOfDay(endHours).getMillis();
        return isInTime(startTime, endTime);
    }

    /**
     * 是否在当天早上9点到晚上21点之间(名字好难取)
     *
     * @return
     */
    public static boolean isInTime9to21() {

        DateTime now = DateTime.now().withTimeAtStartOfDay();
        long startTime = now.withHourOfDay(9).getMillis();
        long endTime = now.withHourOfDay(21).getMillis();

        return isInTime(startTime, endTime);
    }

    /**
     * 当前时间是否在指定时间内
     *
     * @return
     */
    public static String unionpayQrValidTime() {

        DateTime now = DateTime.now();

        if (now.getHourOfDay() >= 23) {
            return "0";
        }

        DateTime end = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(now.toString("yyyyMMdd") + "230000");
        int d = end.getSecondOfDay() - now.getSecondOfDay();
        d = d < 0 ? 0 : d;

        return String.valueOf(d);
    }


    /**
     * 当前时间是否在指定时间内
     *
     * @return
     */
    public static String unionpayQrValidTime(Integer day) {

        DateTime now = DateTime.now();

        if (now.getHourOfDay() >= 23) {
            return "0";
        }

        DateTime end = now.plusDays(day);
        long d = (end.getMillis() - now.getMillis()) / 1000;
        d = d < 0 ? 0 : d;

        return String.valueOf(d);
    }

    public static void main(String[] args) {


        System.out.println(15552000 / 60 / 60 / 24);
        System.err.println(unionpayQrValidTime(180));
    }
}
