package com.poscodx.jblog.vo;


public class CategoryVo {
	
	private int no;
	
	private String name;
	
	private String description;
	
	private String blogId;
	
	public CategoryVo(String name, String description, String blogId) {
		this.name = name;
		this.description = description;
		this.blogId = blogId;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBlogId() {
		return blogId;
	}
	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}
	@Override
	public String toString() {
		return "CategoryVo [no=" + no + ", name=" + name + ", description=" + description + ", blogId=" + blogId + "]";
	}
}
