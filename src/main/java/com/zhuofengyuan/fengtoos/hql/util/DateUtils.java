package com.zhuofengyuan.fengtoos.hql.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {
	public static final int TIME_MONTH = 2;
	public static final int TIME_HOUROFDAY = 11;
	public static final int TIME_DAYOFMONTH = 5;
	public static final int TIME_DAYOFYEAR = 6;
	public static final int TIME_MINUTE = 12;
	public static final int TIME_SECOND = 13;
	public static final int TIME_YEAR = 1;
	public static final int TIME_DAYOFWEEK = 7;
	public static final int TIME_WEEK_OF_YEAR = 3;

	public static Date add(Date date, int part, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(part, value);
		return calendar.getTime();
	}

	public static long diff(Date from, Date to, int part) {
		if (to != null && from != null) {
			long d = to.getTime() - from.getTime();
			switch (part) {
				case 11 :
					return d / 1000L / 60L / 60L;
				case 12 :
					return d / 1000L / 60L;
				default :
					return d / 1000L / 60L / 60L / 24L;
			}
		} else {
			return 0L;
		}
	}

	public static String format(Date date, String format, String timeZone) {
		if (date == null) {
			return "";
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			if (timeZone != null && !timeZone.trim().equals("")) {
				formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
			}

			return formatter.format(date);
		}
	}

	public static int getDatePart(Date date, int part, String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (timeZone != null && !timeZone.trim().equals("")) {
			calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
		}

		int ret = calendar.get(part);
		if (part == 2) {
			++ret;
		}

		return ret;
	}

	public static Date get(Date date, String locate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (locate != null && !locate.trim().equals("")) {
			calendar.setTimeZone(TimeZone.getTimeZone(locate));
		}

		return calendar.getTime();
	}

	public static int getDay(Date date, String timeZone) {
		GregorianCalendar cal = new GregorianCalendar(getYear(date, timeZone), getMonth(date, timeZone), 1);
		return cal.getActualMaximum(5);
	}

	public static int getHour(Date date) {
		return getTimePart(date, 11, "");
	}

	public static int getMinute(Date date) {
		return getTimePart(date, 12, "");
	}

	public static int getMonth(Date date, String timeZone) {
		return getTimePart(date, 2, timeZone);
	}

	public static int getTimePart(Date date, int part, String timeZone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (timeZone != null && !timeZone.trim().equals("")) {
			calendar.setTimeZone(TimeZone.getTimeZone(timeZone));
		}

		return calendar.get(part);
	}

	public static Date getToday(boolean includeHours, String zone) {
		return includeHours ? get(new Date(), zone) : valueOf(format(new Date(), "yyyy-MM-dd", zone), "");
	}

	public static int getYear(Date date, String timeZone) {
		return getTimePart(date, 1, timeZone);
	}

	public static Date stringToDate(String source, String patterns) {
		return stringToDate(source, patterns, true);
	}

	public static Date stringToDate(String source, String patterns, boolean locate) {
		return locate ? stringToDate(source, patterns, "GMT+8") : stringToDate(source, patterns, "");
	}

	public static Date stringToDate(String source, String patterns, String timeZone) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(patterns);
		Date date = null;
		if (source == null) {
			return date;
		} else {
			if (timeZone != null && !timeZone.trim().equals("")) {
				dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
			}

			try {
				date = dateFormat.parse(source);
			} catch (ParseException arg5) {
				System.out.println("[string to date]" + arg5.getMessage());
			}

			return date;
		}
	}

	public static Date valueOf(String source, String patterns) {
		return valueOf(source, patterns, "GMT+8");
	}

	public static Date valueOf(String value, String patterns, String timeZone) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(patterns);
		Date date = null;
		if (value == null) {
			return date;
		} else {
			if (timeZone != null && !timeZone.trim().equals("")) {
				dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
			}

			try {
				date = dateFormat.parse(value);
			} catch (ParseException arg5) {
				System.out.println("[string to date]" + arg5.getMessage());
			}

			return date;
		}
	}

	public static String dateToString10(Date d) {
		if (d != null) {
			SimpleDateFormat smdAll = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = smdAll.format(d);
			return strDate;
		} else {
			return "";
		}
	}

	public static String dateToString16(Date d) {
		if (d != null) {
			SimpleDateFormat smdAll = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			return smdAll.format(d);
		} else {
			return "";
		}
	}

	public static Date string10ToDate(String strDate) {
		if (StringUtils.isBlank(strDate)) {
			return null;
		} else {
			try {
				strDate = strDate.trim() + " 00:00:00";
				SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d = e.parse(strDate);
				return d;
			} catch (Exception arg2) {
				arg2.printStackTrace();
				return null;
			}
		}
	}

	public static Date string10ToDate_max(String strDate) {
		if (StringUtils.isBlank(strDate)) {
			return null;
		} else {
			try {
				strDate = strDate.trim() + " 23:59:59";
				SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d = e.parse(strDate);
				return d;
			} catch (Exception arg2) {
				arg2.printStackTrace();
				return null;
			}
		}
	}

	public static Date string18ToDate(String strDate) {
		try {
			SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = e.parse(strDate);
			return d;
		} catch (Exception arg2) {
			arg2.printStackTrace();
			return null;
		}
	}

	public static String date18ToString(Date date) {
		try {
			SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String d = e.format(date);
			return d;
		} catch (Exception arg2) {
			arg2.printStackTrace();
			return null;
		}
	}

	public static Date string15ToDate(String strDate) {
		try {
			SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date d = e.parse(strDate);
			return d;
		} catch (Exception arg2) {
			arg2.printStackTrace();
			return null;
		}
	}

	public static Long DateToLong(Date date) {
		long ret = 0L;
		if (date != null) {
			SimpleDateFormat smdYear = new SimpleDateFormat("yyyy");
			SimpleDateFormat smdMonth = new SimpleDateFormat("MM");
			SimpleDateFormat smdDay = new SimpleDateFormat("dd");
			SimpleDateFormat smdH = new SimpleDateFormat("HH");
			SimpleDateFormat smdm = new SimpleDateFormat("mm");
			SimpleDateFormat smds = new SimpleDateFormat("ss");
			String strDate = smdYear.format(date) + smdMonth.format(date) + smdDay.format(date) + smdH.format(date)
					+ smdm.format(date) + smds.format(date);
			ret = (new Long(strDate)).longValue();
		}

		return Long.valueOf(ret);
	}

	public static String DateToChineseString(Date date) {
		String ret = "";
		if (date != null) {
			SimpleDateFormat smdYear = new SimpleDateFormat("yyyy");
			SimpleDateFormat smdMonth = new SimpleDateFormat("MM");
			SimpleDateFormat smdDay = new SimpleDateFormat("dd");
			SimpleDateFormat smdH = new SimpleDateFormat("HH");
			SimpleDateFormat smdm = new SimpleDateFormat("mm");
			new SimpleDateFormat("ss");
			ret = smdYear.format(date) + "年" + smdMonth.format(date) + "月" + smdDay.format(date) + "日 "
					+ smdH.format(date) + ":" + smdm.format(date);
		}

		return ret;
	}
}