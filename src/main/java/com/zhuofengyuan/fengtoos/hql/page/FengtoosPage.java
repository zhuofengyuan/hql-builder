package com.zhuofengyuan.fengtoos.hql.page;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页
 * 
 * @author ZhuoFeng.Yuan ( 2018年11月21日 ) 
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FengtoosPage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2913171657638132690L;
	/** 数据列表 */
	private List<?> items;
	private long total;
	private int pageSize;
	private long pageCount;
	private long totalPage;
}
