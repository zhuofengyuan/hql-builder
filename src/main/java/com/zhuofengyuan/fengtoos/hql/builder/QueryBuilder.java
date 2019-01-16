package com.zhuofengyuan.fengtoos.hql.builder;

import com.zhuofengyuan.fengtoos.hql.base.ICondition;
import com.zhuofengyuan.fengtoos.hql.base.IQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryBuilder extends ConditionFactory implements IQuery {
	private StringBuffer orderBuf = new StringBuffer();
	private String tableName = "";
	private StringBuffer groupBuf = new StringBuffer();
	private StringBuffer selectBuf = new StringBuffer();
	private List<Join> joins = null;

	public QueryBuilder() {
	}

	public QueryBuilder(Class<?> pojo) {
		this.tableName = this.getClassName(pojo);
	}

	public QueryBuilder(Class<?> pojo, String alias) {
		this.tableName = this.getClassName(pojo) + " " + alias;
	}

	public QueryBuilder(Class<?>[] pojos) {
		this.tableName = "";
		String alias = "abcdefghijklmnopqrstuvwxyz";

		for (byte i = 0; i < pojos.length; ++i) {
			if (i == 0) {
				this.tableName = String.format("%s %s",
						this.getClassName(pojos[i]), Character.valueOf(alias.charAt(i)));
			} else {
				this.tableName = this.tableName + String.format(",%s %s",
						this.getClassName(pojos[i]), Character.valueOf(alias.charAt(i)));
			}
		}

	}

	public QueryBuilder(String tableNames) {
		this.tableName = tableNames;
	}

	public void addBuilder(IQuery builder) {
		String tmp = builder.getSQL();
		if (!tmp.equals("")) {
			this.append("(" + tmp + ")", true);
			this.fieldValues.addAll(builder.getValues());
			this.lastOper = "";
		}
	}

	public void addBuilder(IQuery builder, String fieldName, String targetFieldName, boolean isRange) {
		this.addBuilder(builder, fieldName, targetFieldName, isRange ? 4 : 0);
	}

	public void addBuilder(IQuery builder, String fieldName, String targetFieldName, int action) {
		if (fieldName != null && !fieldName.trim().equals("")) {
			String tmp = builder.getSQL();
			if (!tmp.equals("")) {
				tmp = "select " + this.att2FName(targetFieldName) + tmp;
				switch (action) {
					case 0 :
						this.append(this.att2FName(fieldName) + " = (" + tmp + ")", true);
						break;
					case 1 :
					case 2 :
					case 3 :
					case 6 :
					case 7 :
					case 8 :
					default :
						String msg = "addBuilder:不可知的逻辑运算行为" + action;
						throw new QueryBuilderException(msg);
					case 4 :
						this.append(this.att2FName(fieldName) + " in (" + tmp + ")", true);
						break;
					case 5 :
						this.append(this.att2FName(fieldName) + " <> (" + tmp + ")", true);
						break;
					case 9 :
						this.append(this.att2FName(fieldName) + " not in (" + tmp + ")", true);
				}

				this.fieldValues.addAll(builder.getValues());
			}
		} else {
			this.addBuilder(builder);
		}
	}

	protected String class2Table(Class<?> type) {
		return this.class2Table(type.getName());
	}

	protected String class2Table(String className) {
		return className;
	}

	protected synchronized String getClassName(Class<?> type) {
		String name = type.getName();
		return name.substring(name.lastIndexOf(46) + 1);
	}

	public String getDeleteSQL() {
		this.endGroudCondtion();
		String tmp = this.whereBuf.toString();
		if (!this.tableName.equals("")) {
			if (tmp.equals("")) {
				tmp = "delete from " + this.tableName;
			} else {
				tmp = "delete from " + this.tableName + " where " + this.whereBuf.toString();
			}
		}

		tmp = tmp + this.orderBuf.toString();
//		log.info("QueryBuilder.getDeleteSQL:" + tmp);
		return tmp;
	}

	public String getHQL() {
		return this.getQL(null, false);
	}

	public String getHQL(String fieldNames) {
		return this.getQL(fieldNames, false);
	}

	protected String getQL(String fieldNames, boolean jdbc) {
		this.endGroudCondtion();
		String tmp = this.whereBuf.toString();
		fieldNames = this.getSelectFieldList(fieldNames);
		if (!this.tableName.equals("")) {
			if (tmp.equals("")) {
				tmp = " from " + this.tableName + this.getJoin();
			} else {
				tmp = " from " + this.tableName + this.getJoin() + " where " + this.whereBuf.toString();
			}

			if (jdbc || !"*".equals(fieldNames.trim())) {
				tmp = "select " + fieldNames + tmp;
			}
		}

		tmp = tmp + this.groupBuf.toString() + this.orderBuf.toString();
//		log.info("QueryBuilder.getHQL:" + tmp);
		return tmp;
	}

	private String getJoin() {
		if (this.joins == null) {
			return "";
		} else {
			StringBuffer buf = new StringBuffer();
			String[] connExps = new String[]{" USE_HASH(%s) ", " USE_HASH(%s) ", " USE_MERGE(%s) ",
					" inner join %s %s on ", " left join %s %s on ", " right join %s %s on ", " full join %s %s on ",
					" cross join %s %s on "};
			ArrayList<ICondition> values = new ArrayList<ICondition>();

			for (int i = 0; i < this.joins.size(); ++i) {
				Join item = this.joins.get(i);
				buf.append(String.format(connExps[item.join], item.tableName, item.alias));
				buf.append(item.getWhere().replace(" where ", ""));
				values.addAll(item.fieldValues);
			}

			values.addAll(this.fieldValues);
			this.fieldValues = values;
			return buf.toString();
		}
	}

	private String getSelectFieldList(String fieldList) {
		if (fieldList == null || fieldList.trim().equals("")) {
			fieldList = this.selectBuf.toString();
		}

		if (fieldList == null || fieldList.trim().equals("")) {
			fieldList = "*";
		}

		return fieldList;
	}

	public String getSQL() {
		return this.getQL(null, true);
	}

	public String getSQL(String fieldList) {
		return this.getQL(fieldList, true);
	}

	public String getTotalSQL() {
		return this.getQL("count(*)", true);
	}

	public String getUpdate() {
		return !this.tableName.equals("") ? "update " + this.tableName : "";
	}

	public List<ICondition> getValues() {
		return this.fieldValues;
	}

	public void groupby(String field) {
		if (field != null && !field.trim().equals("")) {
			if (this.groupBuf.toString().trim().equals("")) {
				this.groupBuf.append(" group by ");
			} else {
				this.groupBuf.append(",");
			}

			this.groupBuf.append(field);
		}
	}

	public void groupby(String[] fields) {
		if (fields.length > 0) {
			if (this.groupBuf.toString().trim().equals("")) {
				this.groupBuf.append(" group by ");
			} else {
				this.groupBuf.append(",");
			}

			for (int i = 0; i < fields.length; ++i) {
				if (i != 0) {
					this.groupBuf.append("," + fields[i]);
				} else {
					this.groupBuf.append(fields[i]);
				}
			}
		}

	}

	public Join join(String tableName, int join) {
		if (this.joins == null) {
			this.joins = new ArrayList<Join>();
		}

		String alias = "abcdefghijklmnopqrstuvwxyz";
		return this.join(tableName, String.valueOf(alias.charAt(this.joins.size() + 1)), join);
	}

	public Join join(String tableName, String alias, int join) {
		Join j = new Join(tableName, join);
		this.joins.add(j);
		j.alias = alias;
		return j;
	}

	public void orderBy(String fieldName) {
		if (this.orderBuf.toString().equals("")) {
			this.orderBuf.append(" order by " + this.att2FName(fieldName));
		} else {
			this.orderBuf.append("," + this.att2FName(fieldName));
		}

	}

	public void orderBy(String fieldName, boolean asc) {
		if (asc) {
			fieldName = this.att2FName(fieldName) + " asc";
		} else {
			fieldName = this.att2FName(fieldName) + " desc";
		}

		if (this.orderBuf.toString().equals("")) {
			this.orderBuf.append(" order by " + fieldName);
		} else {
			this.orderBuf.append("," + fieldName);
		}

	}

	public void select(String fieldName) {
		if (this.selectBuf.toString().equals("")) {
			this.selectBuf.append(fieldName);
		} else {
			this.selectBuf.append("," + fieldName);
		}

	}

	protected String shortName2Table(String shortName) {
		return shortName;
	}

	public void where(String fieldName, Date value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String fieldName, double value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String fieldName, Double value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String fieldName, float value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String fieldName, Float value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String fieldName, ICondition condition, int queryAction) {
		super.where(fieldName, condition, queryAction);
	}

	public void where(String fieldName, int value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String fieldName, int value) {
		super.where(fieldName, value);
	}

	public void where(String fieldName, int[] values, int action) {
		super.where(fieldName, values, action);
	}

	public void where(String fieldName, int[] values) {
		super.where(fieldName, values);
	}

	public void where(String fieldName, Integer value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String field, IQuery subQuery) {
		super.where(field, subQuery);
	}

	public void where(String fieldName, long value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String fieldName, long value) {
		super.where(fieldName, value);
	}

	public void where(String fieldName, String value, int queryAction, String patterns) {
		super.where(fieldName, value, queryAction, patterns);
	}

	public void where(String fieldName, String value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}

	public void where(String fieldName, String value) {
		super.where(fieldName, value);
	}

	public void where(String fieldName, String[] values) {
		super.where(fieldName, values);
	}

	public void where(String hql) {
		super.where(hql);
	}

	public void where(String[] fieldNames, String value, int queryAction) {
		super.where(fieldNames, value, queryAction);
	}

	public void whereAsDate(String fieldName, String value, int queryAction) {
		super.whereAsDate(fieldName, value, queryAction);
	}

	public void whereAsInt(String fieldName, String value, int queryAction) {
		super.whereAsInt(fieldName, value, queryAction);
	}
	
	public void where(String fieldName, Object value, int queryAction) {
		super.where(fieldName, value, queryAction);
	}
	
	public void where(String fieldName, Object value) {
		super.where(fieldName, value);
	}
}