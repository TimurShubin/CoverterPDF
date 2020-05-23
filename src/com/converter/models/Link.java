package com.converter.models;

public class Link {

	private String name;
	private long size;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long l) {
		this.size = l;
	}

	@Override
	public String toString() {
		return "Link [name=" + name + ", size=" + size + "]";
	}
	
}
