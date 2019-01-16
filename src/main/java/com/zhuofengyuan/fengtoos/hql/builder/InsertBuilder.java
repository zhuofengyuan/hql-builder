package com.zhuofengyuan.fengtoos.hql.builder;

import com.zhuofengyuan.fengtoos.hql.base.AbstractModiableQuery;
import com.zhuofengyuan.fengtoos.hql.base.ICondition;
import com.zhuofengyuan.fengtoos.hql.base.IField;
import com.zhuofengyuan.fengtoos.hql.base.IQuery;

import java.util.List;

public class InsertBuilder extends AbstractModiableQuery {
	public InsertBuilder(Class<?> pojo) {
		super(pojo);
	}

	public InsertBuilder(String tableName) {
		super(tableName);
	}

	public String getSQL(boolean prepare) {
		StringBuffer fields = new StringBuffer();
		StringBuffer values = new StringBuffer();

		for (int i = 0; i < this.items.size(); ++i) {
			IField item = (IField) this.items.get(i);
			if (i == 0) {
				fields.append(item.getName());
				values.append(this.getExpress(item, prepare));
			} else {
				fields.append("," + item.getName());
				values.append("," + this.getExpress(item, prepare));
			}
		}

		if (this.tableName != null && !this.tableName.trim().equals("")) {
			return "insert " + this.tableName + "(" + fields.toString() + ") values(" + values.toString() + ")";
		} else {
			return "(" + fields.toString() + ") values(" + values.toString() + ")";
		}
	}

	public String merger(IQuery builder, List<ICondition> conditions) {
		return this.merger(builder, conditions, "*");
	}

	public String merger(IQuery builder, List<ICondition> conditions, String queryFields) {
		String sql = this.getSQL(true);
		String sql1 = builder.getSQL(queryFields);

		for (int list = 0; list < this.items.size(); ++list) {
			conditions.add((ICondition) this.items.get(list));
		}

		List<?> arg7 = builder.getValues();

		for (int i = 0; i < arg7.size(); ++i) {
			conditions.add((ICondition) arg7.get(i));
		}

		return sql1.equals("") ? sql : sql + sql1;
	}
}