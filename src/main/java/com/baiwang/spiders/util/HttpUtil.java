/**
 *Copyright (c) 1997, 2015,BEST WONDER CO.,LTD. All rights reserved.
 */

package com.baiwang.spiders.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @ClassName: HttpUtil
 * @Description: TODO
 * @author wsdoing
 * @date 2016年1月28日 上午9:58:17
 */
@SuppressWarnings("deprecation")
public class HttpUtil {

	private static final String filePath = "pics";

	public static String getCaptcha(String url) throws IOException {

		@SuppressWarnings({ "resource" })
		HttpClient hc = new DefaultHttpClient();
		HttpGet hg = new HttpGet(url);
	    Calendar cd = Calendar.getInstance();  
	    SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss 'GMT'", Locale.US);  
	    sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT  
	    String str = sdf.format(cd.getTime());  
		hg.setHeader("Pragma", "No-cache");
		hg.setHeader("Cache-Control", "no-cache");
		hg.setHeader("Content-Type", "image/jpeg;charset=UTF-8");
		hg.setHeader("Expires", str);
		HttpResponse response = hc.execute(hg);
		if (response.getStatusLine().getStatusCode() != 200) {
			System.out.println(response.getStatusLine().getStatusCode());
			return "";
		}
		HttpEntity entity = response.getEntity();
		String htm_str = null;
		InputStream htm_in = null;

		if (entity != null) {

			htm_in = entity.getContent();
			htm_str = FileUtil.InputStream2File(htm_in, filePath);
		}
		return htm_str;
	}

	public static String getOnePage(String url) throws IOException {

		@SuppressWarnings("resource")
		HttpClient hc = new DefaultHttpClient();
		HttpGet hg = new HttpGet(url);
		HttpResponse response = hc.execute(hg);
		HttpEntity entity = response.getEntity();
		String htm_str = null;
		InputStream htm_in = null;

		if (entity != null) {
			htm_in = entity.getContent();
			htm_str = FileUtil.InputStream2String(htm_in, "UTF-8");
		}
		return htm_str;
	}

	public static String postOnePage(String url, List<NameValuePair> params) throws IOException {

		@SuppressWarnings({ "resource" })
		HttpClient hc = new DefaultHttpClient();
		HttpPost hp = new HttpPost(url);
		hp.setHeader("User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.63 Safari/534.3");
		UrlEncodedFormEntity uparams = new UrlEncodedFormEntity(params, "UTF-8");
		hp.setEntity(uparams);
		HttpResponse response = hc.execute(hp);
		HttpEntity entity = response.getEntity();
		System.out.println(response.getStatusLine().getStatusCode());
		String htm_str = null;
		InputStream htm_in = null;

		if (entity != null) {
			htm_in = entity.getContent();
			htm_str = FileUtil.InputStream2String(htm_in, "UTF-8");
		}
		return htm_str;
	}

	/**
	 * @author wsdoing
	 * @Description: TODO
	 * @param @param
	 *            args
	 * @return void
	 * @throws IOException
	 * @throws @date
	 *             2016年1月28日 上午9:58:17
	 */
	public static void main(String[] args) throws IOException {

		// TODO Auto-generated method stub
		System.out.println(getCaptcha("http://shixin.court.gov.cn/image.jsp"));

	}

}
