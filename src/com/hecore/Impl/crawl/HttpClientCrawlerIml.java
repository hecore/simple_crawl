package com.hecore.Impl.crawl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.InflaterInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.hecore.Iface.crawl.ICrawler;
import com.hecore.httpclient.HttpClientService;
import com.hecore.pojo.CrawlResultPojo;
import com.hecore.pojo.UrlPojo;

public class HttpClientCrawlerIml implements ICrawler {

	@Override
	public CrawlResultPojo craw(UrlPojo urlPojo) {
		return craw(urlPojo, "utf-8");
	}

	@Override
	public CrawlResultPojo craw(UrlPojo urlPojo, String encode) {
		HttpClientService hcs = HttpClientService.getInstance();
		CrawlResultPojo crp = new CrawlResultPojo();
		try {
			String res = hcs.doGet(urlPojo.getUrl(), urlPojo.getParams(), encode);
			crp.setSucess(true);
			crp.setPageContent(res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// CloseableHttpClient hc=HttpClients.createDefault();
		// HttpGet request=new HttpGet(urlPojo.getUrl());
		// CloseableHttpResponse res=hc.execute(request.);
		return crp;
	}

	public static void main(String[] args) {
		HttpClientService hcs=HttpClientService.getInstance();
		String res = hcs.doGet("http://www.baidu.com",new HashMap<>(),"utf-8");
		System.out.println(res);
	}
	// CRLF expected at end of chunk
	public static void chuck_method(){
		CloseableHttpClient httpclient = HttpClients.custom().build();
		HttpGet httpget = new HttpGet("http://www.baidu.com/");
		BufferedReader br = null;
		CloseableHttpResponse response=null;
		try {
			response = httpclient.execute(httpget);// 响应流
			HttpEntity entity = response.getEntity();
//			EntityUtils.consume(entity);//消费流
			InputStreamReader isr = new InputStreamReader(entity.getContent(), "utf-8");
			br = new BufferedReader(isr);
			String line = null;
			while ((line=br.readLine())!=null) {
				System.out.println(line+"\n");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//本行不注释会有CRLF故障
//			try {
//				response.close();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			if (br!=null) {
				try { 
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("流未关闭");
				}
			}
		}
	}
}
