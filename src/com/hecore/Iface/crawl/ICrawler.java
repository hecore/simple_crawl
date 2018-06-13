
/**
 * @author Administrator
 *
 */
package com.hecore.Iface.crawl;

import java.util.Map;

import com.hecore.pojo.CrawlResultPojo;
import com.hecore.pojo.UrlPojo;

public interface ICrawler{
	
	public CrawlResultPojo craw(UrlPojo urlPojo);

	public CrawlResultPojo craw(UrlPojo urlPojo, String encode);
	
}