/**
 * 
 */
/**
 * @author Administrator
 *
 */
package com.hecore.Impl.crawl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;

import com.hecore.Iface.crawl.ICrawler;
import com.hecore.pojo.CrawlResultPojo;
import com.hecore.pojo.UrlPojo;

public class SocketCrawlerImp implements ICrawler{

	@Override
	public CrawlResultPojo craw(UrlPojo urlPojo) {
		return craw(urlPojo, "utf-8");
		// TODO Auto-generated method stub
		
	
	}
	
	//乱码处理
	private String getResUrl(BufferedReader br, Socket socket, BufferedWriter bw, UrlPojo urlPojo, String host,String encode) throws Exception {
		StringBuilder sb=new StringBuilder();
		br=new BufferedReader(new InputStreamReader(socket.getInputStream(),encode));//读取设置 更重要
		String line=null;
		boolean flag=false;
		while ((line=br.readLine())!=null) {
			//System.out.println(line);
			sb.append(line+"\n");
//			if (line.contains("�")||line.contains("?")) {
//				System.out.println("00000xxxxx读取错误");
//				System.out.println("=====================");
//				flag=true;
//				break;
//			}
		}
		return sb.toString();
//		if (flag) {
//			flag=false;
//			sendUrl(bw, socket, urlPojo, host, "gbk");
//			getResUrl(br, socket, bw, urlPojo, host);
//		}	
	}

	private void sendUrl(BufferedWriter bw, Socket socket, UrlPojo urlPojo, String host, String encode) throws Exception{
		bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),encode));
		//参照url
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
		System.out.println("目标网站"+url);
		bw.write("GET "+url+" HTTP/1.0\r\n");//1.1keep-alive
		bw.write("HOST:"+host+"\r\n");
		bw.write("\r\n");//代表协议头 httphead输出给服务器端完成。
		bw.flush();
	}
	
	

	public static void main(String[] args) {
		SocketCrawlerImp sci=new SocketCrawlerImp();
		UrlPojo up=new UrlPojo("https://www.baidu.com");
		CrawlResultPojo crp = sci.craw(up,"gbk");
		System.out.println("done"+crp.getPageContent());
	}

	@Override
	public CrawlResultPojo craw(UrlPojo urlPojo, String encode) {
		CrawlResultPojo crp=new CrawlResultPojo();
		if (urlPojo == null || urlPojo.getUrl() == null) {
			crp.setSucess(false);
			crp.setPageContent(null);
			
			return crp;
		}
		//host怎么拿，提出post
		String host=urlPojo.getHost();//www.baidu.com
		if (host==null) {
			crp.setSucess(false);
			crp.setPageContent(null);
			return crp;
		}
		//
		BufferedWriter bw=null;
		BufferedReader br=null;
		try {
			Socket socket=new Socket(host,80);//Socket[addr=www.baidu.com/119.75.213.61,port=80,localport=60028]
			//发送命令
			sendUrl(bw,socket,urlPojo,host,encode);
			
			//字符操作,尽量字符流
			//socket.getOutputStream();
			//接收返回结果
			String res = getResUrl(br,socket,bw,urlPojo, host,encode);
			crp.setSucess(true);
			crp.setPageContent(res);
			
			return crp;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}finally{
			//关闭流
			try {
				if (bw!=null) 
					bw.close();
				if (br!=null) 
					br.close();
			} catch (Exception e) {
				System.out.println("流最终中未关闭");
				e.printStackTrace();
			}
		}
		return null ;
	}
	
}