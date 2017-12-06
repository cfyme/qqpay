package com.fshows.proxy.myutil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @author wudy
 */
public class DateUtils {

  public static final String DATE_FORMAT_PATTERN_YEARMONTH = "yyyyMM";

  public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

  public static final String DATE_FORMAT_PATTERN_DAY = "yyyy-MM-dd";

  public static final String DATE_FORMAT_PATTERN_MONTH = "yyyy-MM";

  public static final String DATE_FORMAT_PATTERN_YEAR = "yyyy";

  private long lNow = System.currentTimeMillis();
  private Calendar cNow = Calendar.getInstance();
  private Date dNow = new Date(lNow);
  private Timestamp tNow = new Timestamp(lNow);
  private java.sql.Date today = new java.sql.Date(lNow);
  private java.sql.Time now = new java.sql.Time(lNow);

  /**
   * 默认构造方法
   */
  public DateUtils() {

  }

  /*
   * private DateUtils(long lNow, Calendar cNow, Date dNow, Timestamp tNow, java.sql.Date today,
   * Time now) { this.lNow = lNow; this.cNow = cNow; this.dNow = dNow; this.tNow = tNow; this.today
   * = today; this.now = now; }
   */

  /**
   * 得到年
   * 
   * @param c
   * @return
   */
  public static int getYear(Calendar c) {
    if (c != null) {
      return c.get(Calendar.YEAR);
    } else {
      return Calendar.getInstance().get(Calendar.YEAR);
    }
  }

  /**
   * 得到月份 注意，这里的月份依然是从0开始的
   * 
   * @param c
   * @return
   */
  public static int getMonth(Calendar c) {
    if (c != null) {
      return c.get(Calendar.MONTH);
    } else {
      return Calendar.getInstance().get(Calendar.MONTH);
    }
  }

  /**
   * 得到日期
   * 
   * @param c
   * @return
   */
  public static int getDate(Calendar c) {
    if (c != null) {
      return c.get(Calendar.DATE);
    } else {
      return Calendar.getInstance().get(Calendar.DATE);
    }
  }

  /**
   * 得到星期
   * 
   * @param c
   * @return
   */
  public static int getDay(Calendar c) {
    if (c != null) {
      return c.get(Calendar.DAY_OF_WEEK);
    } else {
      return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }
  }

  /**
   * 得到小时
   * 
   * @param c
   * @return
   */
  public static int getHour(Calendar c) {
    if (c != null) {
      return c.get(Calendar.HOUR);
    } else {
      return Calendar.getInstance().get(Calendar.HOUR);
    }
  }

  /**
   * 得到分钟
   * 
   * @param c
   * @return
   */
  public static int getMinute(Calendar c) {
    if (c != null) {
      return c.get(Calendar.MINUTE);
    } else {
      return Calendar.getInstance().get(Calendar.MINUTE);
    }
  }

  /**
   * 得到秒
   * 
   * @param c
   * @return
   */
  public static int getSecond(Calendar c) {
    if (c != null) {
      return c.get(Calendar.SECOND);
    } else {
      return Calendar.getInstance().get(Calendar.SECOND);
    }
  }

  /**
   * 得到指定或者当前时间前n天的Calendar
   * 
   * @param c
   * @param n
   * @return
   */
  public static Calendar beforeNDays(Calendar c, int n) {
    // 偏移量，给定n天的毫秒数
    long offset = n * 24 * 60 * 60 * 1000L;
    Calendar calendar = null;
    if (c != null) {
      calendar = c;
    } else {
      calendar = Calendar.getInstance();
    }

    calendar.setTimeInMillis(calendar.getTimeInMillis() - offset);
    return calendar;
  }

  /**
   * 得到指定或者当前时间后n天的Calendar
   * 
   * @param c
   * @param n
   * @return
   */
  public static Calendar afterNDays(Calendar c, int n) {
    // 偏移量，给定n天的毫秒数
    long offset = n * 24 * 60 * 60 * 1000L;
    Calendar calendar = null;
    if (c != null) {
      calendar = c;
    } else {
      calendar = Calendar.getInstance();
    }

    calendar.setTimeInMillis(calendar.getTimeInMillis() + offset);
    return calendar;
  }

  /**
   * 昨天
   * 
   * @param c
   * @return
   */
  public static Calendar yesterday(Calendar c) {
    long offset = 1 * 24 * 60 * 60 * 1000L;
    Calendar calendar = null;
    if (c != null) {
      calendar = c;
    } else {
      calendar = Calendar.getInstance();
    }

    calendar.setTimeInMillis(calendar.getTimeInMillis() - offset);
    return calendar;
  }

  /**
   * 明天
   * 
   * @param c
   * @return
   */
  public static Calendar tomorrow(Calendar c) {
    long offset = 1 * 24 * 60 * 60 * 1000L;
    Calendar calendar = null;
    if (c != null) {
      calendar = c;
    } else {
      calendar = Calendar.getInstance();
    }

    calendar.setTimeInMillis(calendar.getTimeInMillis() + offset);
    return calendar;
  }

  /**
   * 得到指定或者当前时间前offset毫秒的Calendar
   * 
   * @param c
   * @param offset
   * @return
   */
  public static Calendar before(Calendar c, long offset) {
    Calendar calendar = null;
    if (c != null) {
      calendar = c;
    } else {
      calendar = Calendar.getInstance();
    }

    calendar.setTimeInMillis(calendar.getTimeInMillis() - offset);
    return calendar;
  }

  /**
   * 得到指定或者当前时间前offset毫秒的Calendar
   * 
   * @param c
   * @param offset
   * @return
   */
  public static Calendar after(Calendar c, long offset) {
    Calendar calendar = null;
    if (c != null) {
      calendar = c;
    } else {
      calendar = Calendar.getInstance();
    }

    calendar.setTimeInMillis(calendar.getTimeInMillis() - offset);
    return calendar;
  }

  /**
   * 日期格式化
   * 
   * @param c
   * @param pattern
   * @return
   */
  public static String format(Calendar c, String pattern) {
    Calendar calendar = null;
    if (c != null) {
      calendar = c;
    } else {
      calendar = Calendar.getInstance();
    }
    if (pattern == null || pattern.equals("")) {
      pattern = DateUtils.DATE_FORMAT_PATTERN;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);

    return sdf.format(calendar.getTime());
  }

  /**
   * 日期格式化
   * 
   * @param c
   * @param pattern
   * @return
   */
  public static String dateToStr(Date c, String pattern) {
    if (c != null) {
      return format(dateConvertCalendar(c), pattern);
    }
    return null;
  }

  /**
   * 日期格式化
   * 
   * @param c
   * @param pattern
   * @return
   */
  public static String dateToStr(Date c) {
    if (c != null) {
      return format(dateConvertCalendar(c), DATE_FORMAT_PATTERN_DAY);
    }
    return null;
  }

  /**
   * Date类型转换到Calendar类型
   * 
   * @param d
   * @return
   */
  public static Calendar dateConvertCalendar(Date d) {
    Calendar c = Calendar.getInstance();
    c.setTime(d);
    return c;
  }

  /**
   * Calendar类型转换到Date类型
   * 
   * @param c
   * @return
   */
  public static Date calendarConvertDate(Calendar c) {
    return c.getTime();
  }

  /**
   * Date类型转换到Timestamp类型
   * 
   * @param d
   * @return
   */
  public static Timestamp dateConvertTimestamp(Date d) {
    return new Timestamp(d.getTime());
  }

  /**
   * Calendar类型转换到Timestamp类型
   * 
   * @param c
   * @return
   */
  public static Timestamp calendarConvertTimestamp(Calendar c) {
    return new Timestamp(c.getTimeInMillis());
  }

  /**
   * Timestamp类型转换到Calendar类型
   * 
   * @param ts
   * @return
   */
  public static Calendar timestampConvertCalendar(Timestamp ts) {
    Calendar c = Calendar.getInstance();
    c.setTime(ts);
    return c;
  }

 

  /**
   * 得到当前时间的字符串表示 格式2010-02-02 12:12:12
   * 
   * @return
   */
  public static String getTimeString() {
    return format(Calendar.getInstance(), DateUtils.DATE_FORMAT_PATTERN);
  }

  /**
   * 标准日期格式字符串解析成Calendar对象
   * 
   * @param s
   * @return
   */
  public static Calendar parsConvertCalender(String s) {
    Timestamp ts = Timestamp.valueOf(s);
    return timestampConvertCalendar(ts);
  }

  /**
   * Calendar类型转换到Date类型
   * 
   * @param c
   * @return
   * @throws ParseException
   */
  public static Date stringToDate(String s, String formatPattern) throws ParseException {
    SimpleDateFormat format = new SimpleDateFormat(formatPattern);
    return format.parse(s);
  }

  // ================以下是get和set方法=========================//

  public long getLNow() {
    return lNow;
  }

  public void setLNow(long now) {
    lNow = now;
  }

  /**
   * 获取n个月前的日期格式
   * 
   * @param n
   */
  @SuppressWarnings("static-access")
  public static String getMonthBefore(int n, String pattern) {
    Date date = new Date();
    Calendar calendar = Calendar.getInstance(); // 得到日历
    calendar.setTime(date);// 把当前时间赋给日历
    calendar.add(calendar.MONTH, -n);
    date = calendar.getTime();
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    return sdf.format(date);

  }

  public Calendar getCNow() {
    return cNow;
  }

  public void setCNow(Calendar now) {
    cNow = now;
  }

  public Date getDNow() {
    return dNow;
  }

  public void setDNow(Date now) {
    dNow = now;
  }

  public Timestamp getTNow() {
    return tNow;
  }

  public void setTNow(Timestamp now) {
    tNow = now;
  }

  public java.sql.Date getToday() {
    return today;
  }

  public void setToday(java.sql.Date today) {
    this.today = today;
  }

  public java.sql.Time getNow() {
    return now;
  }

  public void setNow(java.sql.Time now) {
    this.now = now;
  }

  public static boolean add(int a, int b) {
    try {
      @SuppressWarnings("unused")
      int c = a / b;
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  /**
   * 
   * @Description: 获取时间之间的天数只差（精确到天）
   * @param startDate 开始时间
   * @param endDate 截止时间
   * @return
   */
  public static int daysBetween(Date startDate, Date endDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    long time1 = 0;
    long time2 = 0;
    long betweenDays = 0;
    try {
        startDate = sdf.parse(sdf.format(startDate));
        endDate = sdf.parse(sdf.format(endDate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        time2 = cal.getTimeInMillis();

      betweenDays = (time2 - time1) / (1000 * 3600 * 24);

    } catch (ParseException e) {
      e.printStackTrace();
    }
    return Integer.parseInt(String.valueOf(betweenDays));
  }
public static void main(String[] args) {
  Calendar cal = Calendar.getInstance();
  cal.add(Calendar.DAY_OF_MONTH, 4);
  System.out.println(cal.getTime());
  System.out.println(new Date());
  System.out.println(daysBetween(cal.getTime(),new Date()));
}
  /**
   * 
   * @Description: 获取月份的天数
   * @param  
   * @return
   */
  public static int fetchDayOfMouth(Date date) {
    
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.DAY_OF_MONTH);
    
  }
  /**
   * 
   * @Description: 获取月份的天数
   * @param  
   * @return
   */
  public static Date addMouth(Date date,int mouth) {
    
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MONTH, mouth);
    return calendar.getTime();
    
  }
}
