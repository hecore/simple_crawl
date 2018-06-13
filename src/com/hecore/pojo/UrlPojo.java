
/**
 * @author Administrator
 *
 */
package com.hecore.pojo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import com.hecore.common.TaskLevel;

/**
 * @author Administrator
 * url pojo
 */
public class UrlPojo{
	
	private String url;
	
	private TaskLevel taskLevel=TaskLevel.MIDDLE;
	
	private Map<String,String> params=new HashMap<>();
	

	public String getHost(){
		try {
			URL url=new URL(this.url);
			return url.getHost();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public UrlPojo() {
		super();
	}
	
	public UrlPojo(String url){
		this.url=url;
	}
	
	public UrlPojo(String url,Map<String,String> params){
		this.url=url;
		this.params=params;
	}
	
	public UrlPojo(String url,TaskLevel taskLevel) {
		this.url=url;
		this.taskLevel=taskLevel;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public TaskLevel getTaskLevel() {
		return taskLevel;
	}

	public void setTaskLevel(TaskLevel taskLevel) {
		this.taskLevel = taskLevel;
	}
	
	public HttpURLConnection getConnection(String urlstr){
		try {
			URL url=new URL(urlstr);
			URLConnection oc = url.openConnection();
			if (oc instanceof HttpURLConnection) {
				return (HttpURLConnection) oc;
			}else{
				throw new Exception("connection is err!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public HttpURLConnection getConnection(){
		try {
			URL url=new URL(this.url);
			URLConnection oc = url.openConnection();
			if (oc instanceof HttpURLConnection) {
				return (HttpURLConnection) oc;
			}else{
				throw new Exception("connection is err!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;		
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

}