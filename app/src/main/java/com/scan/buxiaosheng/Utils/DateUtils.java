package com.scan.buxiaosheng.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 * Created by Thong on 2018/1/16.
 */

public class DateUtils {
    private static volatile DateUtils self = null;
    private static SimpleDateFormat format;

    private final int second_of_minute = 60;
    private final int minute_of_hour = 60 * second_of_minute;
    private final int hour_of_day = 24 * minute_of_hour;
    private final int day_of_month = 30 * hour_of_day;
    private final int month_of_year = day_of_month * 12;

    public static DateUtils getInstance() {
        if (self == null) {
            synchronized (DateUtils.class) {
                if (null == self) {
                    self = new DateUtils();
                    format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                }
            }
        }
        return self;
    }

    /**
     * 转化数字时间20180501122533
     *
     * @return 转化后字符串格式
     */
    public String getTime(long time) {
        String str = String.valueOf(time);
        if (str.length() == 14) {
            return str.substring(0, 4) + "-" +
                    str.substring(4, 6) + "-" +
                    str.substring(6, 8) + " " +
                    str.substring(8, 10) + ":" +
                    str.substring(10, 12) + ":" +
                    str.substring(12, 14);
        } else if (str.length() == 12) {
            return str.substring(0, 4) + "-" +
                    str.substring(4, 6) + "-" +
                    str.substring(6, 8) + " " +
                    str.substring(8, 10) + ":" +
                    str.substring(10, 12);
        } else if (str.length() == 8) {
            return str.substring(0, 4) + "-" +
                    str.substring(4, 6) + "-" +
                    str.substring(6, 8);
        } else {
            return "";
        }
    }

    //解析时间
    public String getTime(String time){
        String str = time;
        return str.substring(0,4) + "-" +
                str.substring(4,6) + "-" +
                str.substring(6,8);
    }

    public Date getDateWithString(String dateString) {
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Date getDateWithString2(String dateString) {
        Date date = null;
        dateString = dateString.replace("-","");
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
            date = format1.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String parseDateToString(Date date) {
        return format.format(date);
    }

    public String parseDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(date);
    }

    public String parseDataNoHour(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public Date getCurDateForFirst() {
        String year;
        String month;
        String day;
        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        return getDateWithString(year + "-" + month + "-" + day + " 00:00:00");
    }

    public Date getCurDateForLast() {
        String year;
        String month;
        String day;
        Calendar calendar = Calendar.getInstance();
        year = String.valueOf(calendar.get(Calendar.YEAR));
        month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        return getDateWithString(year + "-" + month + "-" + day + " 23:59:59");
    }

    public String getDiffTime(Date time) {
        if (null != time) {
            long timeStamp = time.getTime();
            long curTime = System.currentTimeMillis();
            long diffTime = curTime - timeStamp;
            return cauTime(diffTime);
        } else {
            return "";
        }
    }

    public int calcuDiffDay(Calendar calendar1, Calendar calendar2) {
        long timeStamp = calendar2.getTime().getTime() - calendar1.getTime().getTime();
        return (int) (timeStamp / 1000 / 60 / 60 / 24);
    }

    private String cauTime(long time) {
        time = time / 1000;
        if (time < second_of_minute) {
            return time + "秒前";
        } else if (time < minute_of_hour) {
            return (time / second_of_minute) + "分钟前";
        } else if (time < hour_of_day) {
            return (time / minute_of_hour) + "小时前";
        } else if (time < day_of_month) {
            return ((time / hour_of_day) + 1) + "天前";
        } else if (time < month_of_year) {
            return (time / day_of_month) > 12 ? (time / day_of_month) + "年前" : (time / day_of_month) + "月前";
        } else {
            return "";
        }
    }

    /**
     * 获取当月有多少天
     *
     * @param year  年份
     * @param month 月份
     * @return 当月天数
     */
    public int getMonthDay(int year, int month) {
        switch (month) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;
            case 1:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    return 29;
                } else {
                    return 28;
                }
            case 3:
            case 5:
            case 8:
            case 10:
                return 30;
            default:
                return 30;
        }
    }

    //获取当前日期位于星期几，这里第一位是星期一
    public int getWeekDay(Calendar calendar) {
        int week = 0;
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                week = -6;
                break;
            case 2:
                week = 0;
                break;
            case 3:
                week = -1;
                break;
            case 4:
                week = -2;
                break;
            case 5:
                week = -3;
                break;
            case 6:
                week = -4;
                break;
            case 7:
                week = -5;
                break;
        }
        return week;
    }

    /**
     * 获取这周有几天是上月
     *
     * @return 返回上月在当月的天数
     */
    public int getLastWeekDay(Calendar calendar) {
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 1://星期天 前面还有6天
                return 6;
            case 2://星期一 前面还有0天
                return 0;
            case 3://星期二 前面还有1天
                return 1;
            case 4://星期三 前面还有2天
                return 2;
            case 5://星期四 前面还有3天
                return 3;
            case 6://星期五 前面还有4天
                return 4;
            case 7://星期六 前面还有5天
                return 5;
            default:
                return 0;
        }
    }

    //通过long时间获取日期
    public int getDayForLong(long time){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = null;
        try {
            date = format.parse(String.valueOf(time));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getYearDay(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return 366;
        } else {
            return 365;
        }
    }

    /**
     * 解析时间列表输出成字符串
     *
     * @param dates dates
     * @return 字符串
     */
    public String parseListDataToString(List<String> dates) {
        StringBuilder date = new StringBuilder();
        if (dates.size() == 1) {
            date.append(dates.get(0));
        } else if (dates.size() == 2) {
            date.append(dates.get(0));
            date.append(" 至 ");
            date.append(dates.get(1));
        }
        return date.toString();
    }

    /**
     * 获取某月的上一个月有多少天
     *
     * @param year  年份
     * @param month 月份
     * @return 上月天数
     */
    public int getLastMonthDay(int year, int month) {
        month = month == 0 ? 11 : month - 1;//判断是否是1月，如果是则为12月;
        switch (month) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;
            case 1:
                if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                    return 29;
                } else {
                    return 28;
                }
            case 3:
            case 5:
            case 8:
            case 10:
                return 30;
            default:
                return 30;
        }
    }

    /**
     * 获取月份
     *
     * @param month 月份
     * @return 字符串
     */
    public String getMonth(int month) {
        switch (month) {
            case 0:
                return "1月";
            case 1:
                return "2月";
            case 2:
                return "3月";
            case 3:
                return "4月";
            case 4:
                return "5月";
            case 5:
                return "6月";
            case 6:
                return "7月";
            case 7:
                return "8月";
            case 8:
                return "9月";
            case 9:
                return "10月";
            case 10:
                return "11月";
            case 11:
                return "12月";
            default:
                return "1月";
        }
    }
}
