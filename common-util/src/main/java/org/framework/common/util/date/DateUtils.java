package org.framework.common.util.date;

import org.joda.time.*;

import java.util.Date;

/**
 * @Author xiejs
 * @Description 提供方便的日期操作API
 * @Date Created in 2018/9/3 18:09
 */
public final class DateUtils {

    private static final String MINUTE = "minute";

    private static final String HOUR = "hour";

    private static final String DAY = "day";

    private static final String WEEK = "week";

    private static final String MONTH = "month";

    private static final String YEAR = "year";

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


}
