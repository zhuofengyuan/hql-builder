package com.zhuofengyuan.fengtoos.hql.base;

import java.util.List;

public interface IQuery {
	String getDeleteSQL();

	String getHQL();

	String getSQL();

	String getSQL(String arg0);

	String getTotalSQL();

	List<ICondition> getValues();

	String getWhere();
}