package com.zhuofengyuan.fengtoos.hql.base;

import com.zhuofengyuan.fengtoos.hql.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractModiableQuery implements IModifiableQuery {
	protected List<IField> items = new ArrayList<IField>();
	protected List<IField> wheres = new ArrayList<IField>();
	protected String tableName;

	public AbstractModiableQuery(Class<?> pojo) {
		this.tableName = this.getClassName(pojo);
	}

	public AbstractModiableQuery(String tableName) {
		this.tableName = tableName;
	}

	private void add(IField item) {
		this.items.add(item);
	}
	
	private void addWhere(IField item) {
		this.wheres.add(item);
	}
	
	public void addField(String fieldName, byte[] value) {
		this.add(new DefaultField(fieldName, value));
	}

	public void addField(String fieldName, Date value) {
		this.add(new DefaultField(fieldName, value));
	}

	public void addField(String fieldName, double value) {
		this.add(new DefaultField(fieldName, value));
	}

	public void addField(String fieldName, float value) {
		this.add(new DefaultField(fieldName, value));
	}

	public void addField(String fieldName, int value) {
		this.add(new DefaultField(fieldName, value));
	}

	public void addField(String fieldName, long value) {
		this.add(new DefaultField(fieldName, (float) value));
	}

	public void addField(String fieldName, Object value, int type) {
		this.add(new DefaultField(fieldName, value, type));
	}

	public void addField(String fieldName, String value) {
		this.add(new DefaultField(fieldName, value, 12));
	}

	protected void where(String fieldName, Object value, int type) {
		this.addWhere(new DefaultField(fieldName, value, type));
	}
	
	protected synchronized String getClassName(Class<?> pojo) {
		String name = pojo.getName();
		return name.substring(name.lastIndexOf(46) + 1);
	}

	protected Object getExpress(IField field, boolean prepare) {
		if (prepare) {
			return "?";
		} else {
			switch (field.getType()) {
				case -5 :
					return field.getValue().toString();
				case 1 :
				case 12 :
					return "\'" + field.getValue() + "\'";
				case 4 :
					return field.getValue().toString();
				case 91 :
					return "\'" + DateUtils.format((Date) field.getValue(), "yyyy-MM-dd HH:mm:ss", "") + "\'";
				case 92 :
					return "\'" + DateUtils.format((Date) field.getValue(), "HHmm:ss", "") + "\'";
				case 93 :
					return "\'" + DateUtils.format((Date) field.getValue(), "yyyy-MM-dd HHmm:ss", "") + "\'";
				default :
					return field.getValue();
			}
		}
	}

	protected abstract String getSQL(boolean arg0);

	public String getSQL() {
		return this.getSQL(false);
	}

	public String getPrepareSQL() {
		return this.getSQL(true);
	}

	public List<IField> getValues() {
		return this.items;
	}

	public List<ICondition> value2Conditions() {
		ArrayList<ICondition> conditions = new ArrayList<ICondition>();

		for (int i = 0; i < this.items.size(); ++i) {
			conditions.add(this.items.get(i));
		}
		
		for (int i = 0; i < this.wheres.size(); ++i) {
			conditions.add(this.wheres.get(i));
		}

		return conditions;
	}
}