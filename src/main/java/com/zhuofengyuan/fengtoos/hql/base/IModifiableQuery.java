package com.zhuofengyuan.fengtoos.hql.base;

import java.util.List;

public interface IModifiableQuery extends IPrepareQuery {
	String getSQL();

	String merger(IQuery arg0, List<ICondition> arg1);

	List<ICondition> value2Conditions();
}