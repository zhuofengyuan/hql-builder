package com.zhuofengyuan.fengtoos.hql.base;

public class DefaultColumn implements IColumn {
	private String alais;
	private String name;
	private int columnIndex;
	private int type;

	public DefaultColumn(String name, int type) {
		this.name = name;
		this.type = type;
	}

	public DefaultColumn(String name, int type, int columnIndex) {
		this.name = name;
		this.columnIndex = columnIndex;
		this.type = type;
	}

	public DefaultColumn(String name, int type, int columnIndex, String alais) {
		this.name = name;
		this.columnIndex = columnIndex;
		this.type = type;
		this.alais = alais;
	}

	public String getAlais() {
		return this.alais;
	}

	public String getName() {
		return this.name;
	}

	public int getColumnIndex() {
		return this.columnIndex;
	}

	public int getType() {
		return this.type;
	}
}