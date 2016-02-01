/**
 *Copyright (c) 1997, 2015,BEST WONDER CO.,LTD. All rights reserved.
 */

package com.baiwang.excuter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.baiwang.spiders.GSspider_jx_cccj;
import com.baiwang.spiders.GSspider_jx_jcxx;
import com.baiwang.spiders.GSspider_jx_jyyc;
import com.baiwang.spiders.GSspider_jx_xzcf;
import com.baiwang.spiders.GSspider_jx_yzwf;

import us.codecraft.webmagic.Spider;

/**
  * @ClassName: DataCatchExcuter
  * @Description: TODO
  * @author Administrator
  * @date 2016年1月29日 下午2:22:48
  */
public class DataCatchExcuter {
	
	/**
	  * @author Administrator
	  * @Description: 企业基本信息（工商类抓取）
	  * @param @param djh 企业登记号
	  * @param @param qylx 企业类型	
	  * @return void  
	  * @throws
	  * @date 2016年1月28日 下午5:06:50
	  */
	private static void spider_jbxx(String djh,String qylx){
		Spider.create(new GSspider_jx_jcxx())
		.addUrl("http://gsxt.jxaic.gov.cn/ECPS/qyxxgsAction_initQyjbqk.action?nbxh="+djh+"&qylx="+qylx)
		.run();
	}
	/**
	  * @author Administrator
	  * @Description: 经营异常
	  * @param @param djh
	  * @param @param qylx  
	  * @return void  
	  * @throws
	  * @date 2016年1月29日 下午5:36:42
	  */
	private static void spider_jyyc(String djh,String qylx){
		Spider.create(new GSspider_jx_jyyc())
		//jyycxxAction_init.action?nbxh=3600006000042031
		.addUrl("http://gsxt.jxaic.gov.cn/ECPS/jyycxxAction_init.action?nbxh="+djh)
		.run();
	}
	/**
	  * @author Administrator
	  * @Description: 行政处罚
	  * @param @param djh
	  * @param @param qylx  
	  * @return void  
	  * @throws
	  * @date 2016年1月29日 下午5:36:42
	  */
	private static void spider_xzcf(String djh,String qylx){
		Spider.create(new GSspider_jx_xzcf())
		//xzcfxxAction_initXzcfxx.action?nbxh=3600006000042031
		.addUrl("http://gsxt.jxaic.gov.cn/ECPS/xzcfxxAction_initXzcfxx.action?nbxh="+djh)
		.run();
	}
	/**
	  * @author Administrator
	  * @Description: 严重违法
	  * @param @param djh
	  * @param @param qylx  
	  * @return void  
	  * @throws
	  * @date 2016年1月29日 下午5:36:42
	  */
	private static void spider_yzwf(String djh,String qylx){
		Spider.create(new GSspider_jx_yzwf())
		//yzwfxxAction_init.action
		.addUrl("yzwfxxAction_init.action")
		.run();
	}
	/**
	  * @author Administrator
	  * @Description: 抽查抽检
	  * @param @param djh
	  * @param @param qylx  
	  * @return void  
	  * @throws
	  * @date 2016年1月29日 下午5:36:42
	  */
	private static void spider_cccj(String djh,String qylx){
		Spider.create(new GSspider_jx_cccj())
		//ccjcxxAction_init.action?nbxh=3600006000042031
		.addUrl("http://gsxt.jxaic.gov.cn/ECPS/ccjcxxAction_init.action?nbxh="+djh)
		.run();
	}

	/**
	  * @author Administrator
	  * @Description: TODO
	  * @param @param args  
	  * @return void  
	  * @throws
	  * @date 2016年1月29日 下午2:22:48
	  */
	public static void main(String[] args) {
//		spider_jbxx("3600006000042031", "1219");
//		System.out.println("完事");
//		List<NameValuePair> list = new ArrayList<>();
//		NameValuePair nvp = new BasicNameValuePair("selectValue", "360803310002382");
//		list.add(nvp);
//		try {
//			String a = HttpUtil.postOnePage("http://gsxt.jxaic.gov.cn/ECPS/qyxxgsAction_queryXyxx.action", list);
//			System.out.println("下载页面："+a);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		final String djh = "3600006000042031";
		String qylx = "1219";
		
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
		scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
			public void run() {
				spider_jbxx(djh, "1219");
			}
		}, 0, 12, TimeUnit.HOURS);
		scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
			public void run() {
				spider_jyyc("3600006000042031", "1219");
			}
		}, 0, 1, TimeUnit.HOURS);
		scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
			public void run() {
				spider_xzcf("3600006000042031", "1219");
			}
		}, 0, 1, TimeUnit.HOURS);
		scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
			public void run() {
				spider_yzwf("3600006000042031", "1219");
			}
		}, 0, 1, TimeUnit.HOURS);
		scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
			public void run() {
				spider_cccj("3600006000042031", "1219");
			}
		}, 0, 1, TimeUnit.HOURS);
		
		System.out.println("完事");
	}

}
