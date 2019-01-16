package com.zhuofengyuan.fengtoos.hql.base;

import java.util.List;

public interface IPrepareQuery {
	String getPrepareSQL();

	List<IField> getValues();
}