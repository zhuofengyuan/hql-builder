package com.zhuofengyuan.fengtoos.hql.builder;

import com.zhuofengyuan.fengtoos.hql.base.ICondition;

import java.util.Date;

public class Join extends ConditionFactory {
	public int join;
	public String tableName;
	public String alias;

	public Join(String tableName, int join) {
		this.tableName = tableName;
		this.join = join;
	}

	public void condition(String fieldName, Date value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void condition(String fieldName, double value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void condition(String fieldName, Double value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void condition(String fieldName, float value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void condition(String fieldName, Float value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void condition(String fieldName, ICondition condition, int queryAction) {
		super.where(fieldName, condition, queryAction);
	}

	public void condition(String fieldName, int value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void condition(String fieldName, int value) {
		super.where(fieldName, value);
	}

	public void condition(String fieldName, int[] values, int action) {
		super.where(fieldName, values, action);
	}

	public void condition(String fieldName, int[] values) {
		super.where(fieldName, values);
	}

	public void condition(String fieldName, Integer value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void condition(String fieldName, long value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void condition(String fieldName, long value) {
		super.where(fieldName, value);
	}

	public void condition(String fieldName, String value, int queryAction, String patterns) {
		super.where(fieldName, value, queryAction, patterns);
	}

	public void condition(String fieldName, String value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void condition(String fieldName, String value) {
		super.where(fieldName, value);
	}

	public void condition(String fieldName, String[] values) {
		super.where(fieldName, values);
	}

	public void condition(String hql) {
		super.where(hql);
	}

	public void condition(String[] fieldNames, String value, int queryAction) {
		super.where(fieldNames, value, queryAction);
	}

	public void conditionAsDate(String fieldName, String value, int queryAction) {
		super.whereAsDate(fieldName, value, queryAction);
	}

	public void conditionAsInt(String fieldName, String value, int queryAction) {
		super.whereAsInt(fieldName, value, queryAction);
	}
}