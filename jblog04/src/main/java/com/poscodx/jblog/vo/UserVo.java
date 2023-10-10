package com.poscodx.jblog.vo;

import javax.validation.constraints.NotEmpty;

public class UserVo {
	
	@NotEmpty
	private String id;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String password;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserVo [id=" + id + ", name=" + name + ", password=" + password + "]";
	}
}
