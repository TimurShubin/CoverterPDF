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
	public boolean equals(Object obj) {
		if(!(obj instanceof Link)) return false;
		Link entry = (Link)obj;
		return name.equals(entry.name) && size == entry.size;
	}
	
	@Override
	public int hashCode() {
		int hash = 37;
		hash = hash * 17 + name.hashCode();
		hash = hash * 17 + (int)size;
		return hash;
	}
	
	@Override
	public String toString() {
		return "Link [name=" + name + ", size=" + size + "]";
	}
	
}
