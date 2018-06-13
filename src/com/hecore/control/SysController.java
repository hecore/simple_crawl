
/**
 * @author Administrator
 *
 */
package com.hecore.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hecore.manager.CrawelManager;
import com.hecore.pojo.CrawlResultPojo;
import com.hecore.pojo.UrlPojo;

public class SysController{
	
	public static void main(String[] args) {
		//MulPageCrawl();
		String jsonStr=crawlWebSite("https://www.wdzj.com/front_select-plat");
				//crawlWebSite("https://www.wdzj.com/wdzj/html/json/dangan_search.json");
		//JSONObject jo=JSONObject.parseObject(jsonStr);
		JSONArray ja=JSONArray.parseArray(jsonStr);
		
		System.out.println(ja.size());//  6140条数据
		for (int i = 0; i < ja.size(); i++) {
			JSONObject jo=ja.getJSONObject(i);
			System.out.println(jo.get("platName")+"=="+jo.get("platIconUrl")+"=="+jo.get("hbAppActivityUrl"));
			//DB持久化，或者文档持久化
			
		}
		//System.out.println("CrawRes==>"+crawl.getPageContent());
	}
	
	/**
	 * 	https://www.wdzj.com/dangan/
	 */
	public static void MulPageCrawl(){
		String baseurl="https://www.wdzj.com/front_select-plat";
		UrlPojo up=new UrlPojo(baseurl);
		Map<String,String> params=new HashMap<>();
		int max_page_number=2;
		int have_down_page_count=0;//重复数
		Set<String> uniqSet=new HashSet<>();
		for (int pageNumber=1; pageNumber < max_page_number; pageNumber++) {
			params.put("currentPage",pageNumber+"");
			params.put("params", "");
			params.put("sort", "0");
			up.setParams(params);
			CrawlResultPojo rp=crawlOnePage(up);
			if (uniqSet.contains(rp.getPageContent())) {
				System.out.println("重复");
				have_down_page_count++;
			}
		}
		System.out.println("爬取完毕:重复"+have_down_page_count);
	}
	
	private static CrawlResultPojo crawlOnePage(UrlPojo up) {
		CrawelManager cm=new CrawelManager("HC");
		CrawlResultPojo crawl = cm.crawl(up,"utf-8");
		System.out.println(crawl.getPageContent());
		return crawl;
	}


	/**
	 * httpClient
	 * @ eg：http://www.wandaizhijia.com/dangan.html
	 * 	https://www.wdzj.com/dangan/
	 * @param site
	 */
	public static String crawlWebSite(String site){
		HashMap<String,String> params=new HashMap<>();
//		params.put("user","admin");
//		params.put("pwd","admin");
		//https://www.taobao.com
		UrlPojo u1=new UrlPojo(site,params);
		CrawelManager cm=new CrawelManager("HC");
		CrawlResultPojo crawl = cm.crawl(u1,"utf-8");//utf-8
		System.out.println("CrawRes==>"+crawl.isSucess());
		System.out.println(crawl.getPageContent());
		//分页爬虫,循环遍历
		return crawl.getPageContent();
		
	}
	
	public static void craw1(){
		List<UrlPojo> up=new ArrayList<>();
		UrlPojo u1=new UrlPojo("http://www.baidu.com");
		up.add(u1);
		UrlPojo u2=new UrlPojo("http://www.qq.com");
		up.add(u2);
		CrawelManager cm=new CrawelManager("HUC");
		//CrawelManager cm=new CrawelManager("HUC");
		for (UrlPojo u_t : up) {
			CrawlResultPojo crawl = cm.crawl(u_t,"gb2312");
			System.out.println("CrawRes==>"+crawl.toString());
			System.out.println("是否成功"+crawl.isSucess());
		}
	}

}