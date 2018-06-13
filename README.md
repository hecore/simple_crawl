# simple_crawl
1.基本调用方法
site: https://www.taobao.com
params: 链接参数

		HashMap<String,String> params=new HashMap<>();
		params.put("user","admin");
  	params.put("pwd","admin");

		UrlPojo u1=new UrlPojo(site,params);
		CrawelManager cm=new CrawelManager("HC");
		CrawlResultPojo crawl = cm.crawl(u1,"utf-8");
2.CrawelManager
   声明了三种爬取方式
      HC:httpclient    
      HUC:HttpUrlConnection
      SO:Socket
3.代码中SysController类为基本调用方式
