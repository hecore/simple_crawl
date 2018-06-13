package com.hecore.common;

public enum CrawlTypeEnum {
	SO("SO"),HC("HC"),HUC("HUC");
	
	String type;
	
	//构造
	private CrawlTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
