package org.framework.common.util.date;

import org.joda.time.*;

import java.util.Date;

/**
 * @author jiashuai.xie
 * @description 提供方便的日期操作API
 * @date Created in 2018/9/3 18:09
 */
public final class DateUtils {
    
    public static final String FORMAT_PATTERN = "yyyyMMdd";

    private static final String MINUTE = "minute";

    private static final String HOUR = "hour";

    private static final String DAY = "day";

    private static final String WEEK = "week";

    private static final String MONTH = "month";

    private static final String YEAR = "year";
    
    public static String formatByDefault(Date date) {
        return formatByPattern(date, DateUtils.FORMAT_PATTERN);
    }

    public static String formatByPattern(Date date, String pattern) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern);

        return dateTimeFormatter.print(toDateTime(date));
    }
    
    /**
     * 返回时间= 时间-(计算单位)数量
     *
     * @param date     时间
     * @param num      数量
     * @param timeUnit 计算单位
     * @return
     */
    public static Date sub(Date date, Integer num, TimeUnit timeUnit) {


        if (timeUnit.equals(TimeUnit.HOURS)) {

            return toDateTime(date).minusHours(num).toDate();

        }

        throw new ServiceException("can not support timeUnit");

    }
    
     /**
     * 获取距离${date}之前${num}小时的时间
     * @param date
     * @param num
     * @return
     */
    public static Date subWithHours(Date date, Integer num) {

        return sub(date, num, TimeUnit.HOURS);

    }

    /**
     * 把Date转换成DateTime
     *
     * @param date
     * @return DateTime
     */
    public static DateTime toDateTime(Date date) {
        return new DateTime(date);
    }

    /**
     * 判断两个日期是否在同一天
     *
     * @param firstDate
     * @param secondDate
     * @return
     */
    public static boolean isSameDay(Date firstDate, Date secondDate) {
        return toDateTime(firstDate).toLocalDate().equals(toDateTime(secondDate));
    }

    /**
     * 计算指定单位上的两个指定日期之间的差值
     *
     * @param smallerDate 较小的日期
     * @param biggishDate 较大的日期
     * @param unit        比较单位 DateUtils#HOUR DateUtils#DAY DateUtils#MONTH
     * @return 指定单位上的两个指定日期之间的差值 返回-1代表没有计算出结果
     */
    public static int differNum(Date smallerDate, Date biggishDate, String unit) {

        if (MINUTE.equals(unit)) {
            Minutes.minutesBetween(toDateTime(smallerDate), toDateTime(biggishDate)).getMinutes();
        }

        if (HOUR.equals(unit)) {
            // 返回相差几个小时
            return Hours.hoursBetween(toDateTime(smallerDate), toDateTime(biggishDate)).getHours();
        }

        if (DAY.equals(unit)) {
            // 返回相差几天
            return Days.daysBetween(toDateTime(smallerDate), toDateTime(biggishDate)).getDays();
        }

        if (WEEK.equals(unit)) {
            return Weeks.weeksBetween(toDateTime(smallerDate), toDateTime(biggishDate)).getWeeks();
        }

        if (MONTH.equals(unit)) {
            return Months.monthsBetween(toDateTime(smallerDate), toDateTime(biggishDate)).getMonths();
        }

        if (YEAR.equals(unit)) {
            return Years.yearsBetween(toDateTime(smallerDate), toDateTime(biggishDate)).getYears();
        }

        return -1;

    }

    /**
     * 获取传入时间的一天开始时间
     *
     * @param date
     * @return
     */
    public static Date getStartDate(Date date) {
        return toDateTime(date).withTimeAtStartOfDay().toDate();
    }

    /**
     * 获取传入时间的一天结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndDate(Date date) {
        return toDateTime(date).millisOfDay().withMaximumValue().toDate();
    }
    
    /**
     * 获取当前月的第一天
     */
    public static Date getFirstDayOfMonth() {
        // 获取当前日期
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        // 设置为1号,当前日期既为本月第一天
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当前时间的上一个月份第一天
     */
    public static Date getFirstDayOfLastMonth() {
        // 获取当前日期
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        // 设置为1号,当前日期既为本月第一天
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取指定时间的所在月份的第一天
     */
    public static Date getFirstDayOfMonth(Date date) {
        // 获取当前日期
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 0);
        // 设置为1号,当前日期既为本月第一天
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取指定时间的所在月份的第一天，并且格式化为指定格式的字符串
     *
     * @param date    时间
     * @param pattern 指定的格式
     * @return 格式化后的时间字符串
     */
    public static String getFirstDayOfMonth(Date date, String pattern) {

        Date firstDayOfMonth = getFirstDayOfMonth(date);

        return new SimpleDateFormat(pattern).format(firstDayOfMonth);
    }

    /**
     * 获取指定时间的所在月份的最后一天
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * 获取指定时间的昨天
     */
    public static Date getYesterDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }


    /**
     * 获取指定时间的的前几天
     */
    public static Date getGivenDay(Date date, Integer num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -num);
        return c.getTime();
    }

    /**
     * 获取指定时间的起始时间00:00:00.000
     */
    public static Date getDayBegin(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定时间的结束时间 23:59:59.999
     */
    public static Date getDayEnd(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    public static Date plus(Date target, Integer number) {
        ZoneId zoneId = ZoneId.systemDefault();
        return Date.from(LocalDateTime.ofInstant(target.toInstant(), zoneId).plusDays(number).atZone(zoneId).toInstant());
    }

    /**
     * 根据pattern解析文本为日期
     *
     * @param text
     * @param pattern
     * @return
     */
    public static Date parse(String text, String pattern) {

        try {

            return new SimpleDateFormat(pattern).parse(text);

        } catch (ParseException e) {
            throw new ServiceException(e.getMessage());
        }

    }

    public static Date getDayTime(final Date date, Integer hour, Integer minute) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }


}
