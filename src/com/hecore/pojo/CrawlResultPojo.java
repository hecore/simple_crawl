package com.hecore.pojo;

/**
 * 结果封装
 * @author Administrator
 *
 */
public class CrawlResultPojo {
   
	private boolean isSucess;
	
	private String pageContent;
	
	private int httpStatusCode;

	public boolean isSucess() {
		return isSucess;
	}

	public void setSucess(boolean isSucess) {
		this.isSucess = isSucess;
	}

	public String getPageContent() {
		return pageContent;
	}

	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	
	
	
}
