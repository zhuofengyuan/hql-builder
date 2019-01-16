package com.zhuofengyuan.fengtoos.hql.base;

import java.util.Date;

public interface IField extends ICondition {
	String getAlais();

	IColumn getColumn();

	String getName();

	int getType();

	Object getValue();

	boolean toBoolean();

	boolean toBoolean(boolean arg0);

	boolean toBoolean(int arg0);

	boolean toBoolean(String arg0);

	Date toDate();

	Date toDate(String arg0);

	Date toDate(String arg0, String arg1);

	double toDouble(double arg0);

	float toFloat(float arg0);

	int toInteger(int arg0);

	short toShort(int arg0);

	String toString();

	String toString(String arg0, String arg1);
}