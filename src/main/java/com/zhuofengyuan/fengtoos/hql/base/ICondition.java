package com.zhuofengyuan.fengtoos.hql.base;

public interface ICondition {
	Object getValue();

	boolean isMuilt();

	int getType();
}