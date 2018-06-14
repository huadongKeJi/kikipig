package com.kikipig.util.comm;

import java.io.Serializable;

public class PaginationBean implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	public static final String PREVIOUS = "previous";
	public static final String NEXT = "next";
	public static final String FIRST = "first";
	public static final String LAST = "last";
	private int maxinum;// 最大数量
	private int perCount = 10; // 每页多少条
	private int pageNum;// 总共页数
	private int pageCount;// 当前页
	private String operate;// 操作 上一页 下一页
	private int startIndex;// mysql 多少条开始拿数据\
	private int pCount;
	
	public int getpCount() {
		return pCount;
	}
	public void setpCount(int pCount) {
		this.pCount = pCount;
	}
	public PaginationBean() {
		super();
	}
	/**
	 * 构造方法
	 * @param perCount 当前页
	 * @param pageCount 每页显示数量
	 */
	public PaginationBean(int perCount, int pageCount) {
		this.perCount = perCount;
		this.pageCount = pageCount;
	}

	
	public PaginationBean(int pageCount,int perCount,int maxinum) {
		this.perCount = perCount;
		this.pageCount = pageCount;
		countPageCount(maxinum);
	}
	public void countPageCount(int maxinum) {
		this.maxinum = maxinum;

		this.pageNum = (maxinum % this.perCount == 0 ? maxinum / this.perCount : maxinum / this.perCount + 1);

		if ("previous".equals(this.operate))
			this.pageCount -= 1;
		else if ("next".equals(this.operate))
			this.pageCount += 1;
		else if ("first".equals(this.operate))
			this.pageCount = 1;
		else if ("last".equals(this.operate)) {
			this.pageCount = this.pageNum;
		} else if ("num".equals(this.operate)) {
			// this.pageCount = this.pageCount;
		}
		if (this.pageCount < 1)
			this.pageCount = 1;
		if (this.pageCount > this.pageNum)
			this.pageCount = this.pageNum;
		this.startIndex = ((this.pageCount - 1) * this.perCount);
		if (this.startIndex < 0)
			this.startIndex = 0;
	}

	public String getOperate() {
		return this.operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public int getMaxinum() {
		return this.maxinum;
	}

	public int getPageCount() {
		return this.pageCount;
	}

	public int getPageNum() {
		return this.pageNum;
	}

	public int getPerCount() {
		return this.perCount;
	}

	public void setMaxinum(int maxinum) {
		this.maxinum = maxinum;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public void setPerCount(int perCount) {
		this.perCount = perCount;
	}

	public int getStartIndex() {
		return this.startIndex;
	}

	@Override
	public String toString() {
		return "PaginationBean [maxinum=" + maxinum + ", perCount=" + perCount + ", pageNum=" + pageNum + ", pageCount="
				+ pageCount + ", operate=" + operate + ", startIndex=" + startIndex + "]";
	}
}
