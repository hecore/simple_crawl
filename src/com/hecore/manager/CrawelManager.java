
/**
 * @author Administrator
 *
 */
package com.hecore.manager;

import com.hecore.Iface.crawl.ICrawler;
import com.hecore.Impl.crawl.HttpClientCrawlerIml;
import com.hecore.Impl.crawl.HttpUrlConnectionCrrawlerImpl;
import com.hecore.Impl.crawl.SocketCrawlerImp;
import com.hecore.common.CrawlType;
import com.hecore.pojo.CrawlResultPojo;
import com.hecore.pojo.UrlPojo;

/**
 * @author hecore
 * 包含业务逻辑的抓取管理器
 */
public class CrawelManager{
	
	private ICrawler crawler;
	//init
	/**
	 * @param type SO|HC|HUC
	 */
	public CrawelManager(String type) {
		switch (type) {
		case CrawlType.SO:
			this.crawler= new SocketCrawlerImp();
			break;
		case CrawlType.HC:
			this.crawler= new HttpClientCrawlerIml();
			break;
		case CrawlType.HUC:
			this.crawler= new HttpUrlConnectionCrrawlerImpl();
			break;
		default:
			this.crawler=null;
			break;
		}
//		if (isSocket) {
//			this.crawler= new SocketCrawlerImp();
//		}else{
//			this.crawler= new HttpUrlConnectionCrrawlerImpl();
//		}
	
	}
	
	//对象提取
	public CrawlResultPojo crawl(UrlPojo urlPojo){
		return this.crawler.craw(urlPojo);
	}
	
	public CrawlResultPojo crawl(UrlPojo urlPojo,String encode){
		return this.crawler.craw(urlPojo,encode);
	}
	
	public static void main(String[] args) {
		CrawelManager cm=new CrawelManager("HUC");
		UrlPojo u=new UrlPojo("http://www.qq.com");
		CrawlResultPojo crawl = cm.crawl(u,"gb2312");
		System.out.println(crawl.getPageContent());
	}
	
}