package com.zhuofengyuan.fengtoos.hql.base;

import com.zhuofengyuan.fengtoos.hql.util.DateUtils;

import java.util.Date;

public class DefaultField implements IField {
	private IColumn column;
	private Object value;

	private void init(String name, int type) {
		this.column = new DefaultColumn(name, type);
	}

	public DefaultField(String name, Object value, int type) {
		this.init(name, type);
		this.value = value;
	}

	public DefaultField(String name, int value) {
		this.init(name, 4);
		this.value = Integer.valueOf(value);
	}

	public DefaultField(String name, float value) {
		this.init(name, 6);
		this.value = new Float(value);
	}

	public DefaultField(String name, double value) {
		this.init(name, 8);
		this.value = new Double(value);
	}

	public DefaultField(String name, Date value) {
		this.init(name, 91);
		this.value = value;
	}

	public DefaultField(String name, byte[] value) {
		this.init(name, -2);
		this.value = value;
	}

	public DefaultField() {
	}

	public String getAlais() {
		return this.column.getAlais();
	}

	public IColumn getColumn() {
		return this.column;
	}

	public String getName() {
		return this.column.getName();
	}

	public int getType() {
		return this.column.getType();
	}

	public Object getValue() {
		return this.value;
	}

	public boolean toBoolean() {
		return this.toBoolean(false);
	}

	public boolean toBoolean(boolean defaultValue) {
		try {
			return Boolean.valueOf(this.value.toString()).booleanValue();
		} catch (Exception arg2) {
			return defaultValue;
		}
	}

	public boolean toBoolean(int tvalue) {
		return this.toInteger(tvalue + 1) == tvalue;
	}

	public boolean toBoolean(String tvalue) {
		return tvalue.equals(this.value);
	}

	public Date toDate() {
		return this.toDate("");
	}

	public Date toDate(String timezone) {
		if (this.value == null) {
			return null;
		} else {
			Date ret = null;
			if (this.value instanceof java.sql.Date) {
				ret = new Date(((java.sql.Date) this.value).getTime());
			} else {
				if (!(this.value instanceof Date)) {
					return DateUtils.valueOf(this.value.toString(), "yyyy-MM-dd", timezone);
				}

				ret = (Date) this.value;
			}

			return DateUtils.get(ret, timezone);
		}
	}

	public Date toDate(String pattern, String timezone) {
		Date date = this.toDate(timezone);
		String v = DateUtils.format(date, pattern, "");
		return DateUtils.valueOf(v, pattern);
	}

	public double toDouble(double defaultValue) {
		try {
			return Double.valueOf(this.value.toString()).doubleValue();
		} catch (Exception arg3) {
			return defaultValue;
		}
	}

	public float toFloat(float defaultValue) {
		try {
			return Float.valueOf(this.value.toString()).floatValue();
		} catch (Exception arg2) {
			return defaultValue;
		}
	}

	public int toInteger(int defaultValue) {
		try {
			return Integer.valueOf(this.value.toString()).intValue();
		} catch (Exception arg2) {
			return defaultValue;
		}
	}

	public String toString() {
		return this.value == null ? null : (this.value instanceof String ? (String) this.value : this.value.toString());
	}

	public String toString(String pattern, String timezone) {
		Date date = this.toDate(timezone);
		return DateUtils.format(date, pattern, "");
	}

	public boolean isMuilt() {
		return false;
	}

	public short toShort(int defaultValue) {
		try {
			return Short.valueOf(this.value.toString()).shortValue();
		} catch (Exception arg2) {
			return Short.valueOf(Integer.toString(defaultValue)).shortValue();
		}
	}
}