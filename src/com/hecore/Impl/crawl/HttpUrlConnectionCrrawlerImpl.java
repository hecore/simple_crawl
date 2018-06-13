package com.hecore.Impl.crawl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;

import com.hecore.Iface.crawl.ICrawler;
import com.hecore.pojo.CrawlResultPojo;
import com.hecore.pojo.UrlPojo;

/**
 * 抽象类，无法直接初始化
 * 
 * @author Administrator
 *
 */
public class HttpUrlConnectionCrrawlerImpl implements ICrawler {
	
	private static String encode;
	
	public CrawlResultPojo craw(UrlPojo urlPojo,String encode){
		CrawlResultPojo crp = new CrawlResultPojo();
		if (urlPojo == null || urlPojo.getUrl() == null) {
			crp.setSucess(false);
			crp.setPageContent(null);

			return crp;
		}
		String url=urlPojo.getUrl();
		//
		if (urlPojo.getParams().size()!=0) {	
			try {
				URIBuilder builder = new URIBuilder(url);
				for (Map.Entry<String, String> entry : urlPojo.getParams().entrySet()) {
					builder.setParameter(entry.getKey(), entry.getValue());
				}
				url = builder.build().toString();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		
		// 当前逻辑
		// 首先要看这个API 通过已有对象去获取
		HttpURLConnection conn = urlPojo.getConnection(url);
		StringBuilder sb=new StringBuilder();
		if (conn != null) {
			// conn.getInputStream();//获取输入流
			BufferedReader br = null;
			String line = null;
			try {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream(),encode));
				while ((line = br.readLine()) != null) {
					//System.out.println(line);
					sb.append(line+"\n");
				}
				crp.setSucess(true);
				crp.setPageContent(sb.toString());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {			
				try {
					if (null!=br) 
					br.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					System.out.println("流未关闭，可能内存泄露");
				}	
			}

		}
		return crp;	
	}
	
	public HttpUrlConnectionCrrawlerImpl(String encode){
		this.encode=encode;
	}
	
	public HttpUrlConnectionCrrawlerImpl() {
		super();
	}

	@Override
	public CrawlResultPojo craw(UrlPojo urlPojo) {
		return craw(urlPojo,"utf-8");
	}
	
	public static void main(String[] args) {
		HttpUrlConnectionCrrawlerImpl sci=new HttpUrlConnectionCrrawlerImpl();
		UrlPojo up=new UrlPojo("http://www.qq.com");
		CrawlResultPojo crp = sci.craw(up,"gb2312");
		System.out.println("done"+crp.getPageContent());
	}

}
