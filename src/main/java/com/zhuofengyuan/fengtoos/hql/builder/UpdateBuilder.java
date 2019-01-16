package com.zhuofengyuan.fengtoos.hql.builder;

import com.zhuofengyuan.fengtoos.hql.base.AbstractModiableQuery;
import com.zhuofengyuan.fengtoos.hql.base.ICondition;
import com.zhuofengyuan.fengtoos.hql.base.IField;
import com.zhuofengyuan.fengtoos.hql.base.IQuery;

import java.util.List;

public class UpdateBuilder extends AbstractModiableQuery {
	public UpdateBuilder(Class<?> classType) {
		super(classType);
	}

	public UpdateBuilder(String tableName) {
		super(tableName);
	}

	public String getSQL(boolean prepare) {
		StringBuffer sql = new StringBuffer();

		for (int i = 0; i < this.items.size(); ++i) {
			IField item = (IField) this.items.get(i);
			if (i == 0) {
				sql.append(item.getName() + "=" + this.getExpress(item, prepare));
			} else {
				sql.append("," + item.getName() + "=" + this.getExpress(item, prepare));
			}
		}

		if (this.tableName != null && !this.tableName.trim().equals("")) {
			return "update " + this.tableName + " set " + sql.toString();
		} else {
			return sql.toString();
		}
	}

	public String merger(IQuery builder, List<ICondition> conditions) {
		String sql = this.getSQL(true);
		String where = builder.getWhere();

		for(int i = 0, len = conditions.size(); i < len; i++) {
			ICondition item = conditions.get(i);
			where("", item.getValue(), item.getType());
		}

		return where.equals("") ? sql : sql + where;
	}
}