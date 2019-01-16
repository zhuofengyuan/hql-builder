package com.zhuofengyuan.fengtoos.hql.test;

import com.zhuofengyuan.fengtoos.hql.builder.*;

public class Test {
	public static void main(String[] args) {
		QueryBuilder b = new QueryBuilder(new Class[]{InsertBuilder.class, UpdateBuilder.class});
		b.select("*");
		Join item = b.join("Test", JoinConst.LEFT_JOIN);
		item.alias = "c";
		item.condition("c.test1", 1);
		item.condition("a.id=c.uid");
		System.out.println(b.getSQL());
		System.out.println(b.getTotalSQL());
		b = new QueryBuilder(InsertBuilder.class, "a");
		b.join("User", JoinConst.LEFT_JOIN).condition("a.id=b.uid");
		System.out.println(b.getHQL("*"));
	}
}