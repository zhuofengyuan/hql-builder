package com.zhuofengyuan.fengtoos.hql.base;

public class DefaultCondition implements ICondition {
	private Object value;
	private boolean muilt;
	private int type;

	public DefaultCondition(Object value, boolean muilt, int type) {
		this.value = value;
		this.muilt = muilt;
		this.type = type;
	}

	public Object getValue() {
		return this.value;
	}

	public boolean isMuilt() {
		return this.muilt;
	}

	public int getType() {
		return this.type;
	}
}