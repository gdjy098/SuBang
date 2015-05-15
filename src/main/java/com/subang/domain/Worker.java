package com.subang.domain;

import java.io.Serializable;

public class Worker implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String cellnum;
	private String comment; // 取衣员的备注

	public Worker() {
	}

	public Worker(Integer id, String name, String cellnum, String comment) {
		this.id = id;
		this.name = name;
		this.cellnum = cellnum;
		this.comment = comment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCellnum() {
		return cellnum;
	}

	public void setCellnum(String cellnum) {
		this.cellnum = cellnum;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}