package com.zhuofengyuan.fengtoos.hql.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {
	public static List<?> arrayToList(Object[] items) {
		List<Object> list = new ArrayList<Object>();

		for (int i = 0; i < items.length; ++i) {
			list.add(items[i]);
		}

		return list;
	}

	public static String arrayToSting(String[] values, String split, boolean asNumber) {
		StringBuffer buf = new StringBuffer();
		String fix = "\'";
		if (asNumber) {
			fix = "";
		}

		if (values != null) {
			for (int i = 0; i < values.length; ++i) {
				if (i == 0) {
					buf.append(fix + values[i] + fix);
				} else {
					buf.append(split + fix + values[i] + fix);
				}
			}
		}

		return buf.toString();
	}

	public static String arrayToString(int[] items, String splitChar) {
		StringBuffer buf = new StringBuffer();
		if (items != null) {
			for (int i = 0; i < items.length; ++i) {
				if (i > 0) {
					buf.append(splitChar + items[i]);
				} else {
					buf.append(items[i]);
				}
			}
		}

		return buf.toString();
	}

	public static String arrayToString(Object[] items, String splitChar) {
		StringBuffer buf = new StringBuffer();
		if (items != null) {
			for (int i = 0; i < items.length; ++i) {
				if (i > 0) {
					buf.append(splitChar + items[i]);
				} else {
					buf.append(items[i]);
				}
			}
		}

		return buf.toString();
	}

	public static int[] convert(String[] items) {
		int[] ret = new int[items.length];

		for (int i = 0; i < items.length; ++i) {
			ret[i] = Integer.parseInt(items[i]);
		}

		return ret;
	}

	public static int[] strArrayToInt(String[] items) {
		return convert(items);
	}

	public static List<?> stringToList(String source, String splitchar) {
		return source == null ? new ArrayList<Object>() : arrayToList(source.split(splitchar));
	}
}