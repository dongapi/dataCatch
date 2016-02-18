/**
 *Copyright (c) 1997, 2015,BEST WONDER CO.,LTD. All rights reserved.
 */

package com.baiwang.excuter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.baiwang.spiders.GSspider_jx_cccj;
import com.baiwang.spiders.GSspider_jx_jcxx;
import com.baiwang.spiders.GSspider_jx_jyyc;
import com.baiwang.spiders.GSspider_jx_xzcf;
import com.baiwang.spiders.GSspider_jx_yzwf;
import com.baiwang.spiders.util.HttpUtil;

import us.codecraft.webmagic.Spider;

/**
  * @ClassName: DataCatchExcuter
  * @Description: 江西企业信息抓取工具类
  * @author Administrator
  * @date 2016年1月29日 下午2:22:48
  */
public class DataCatchExcuter_jx {
	
	/**
	  * @author ldm
	  * @Description: 企业基本信息（工商类抓取）
	  * @param @param djh 企业登记号
	  * @param @param qylx 企业类型	
	  * @return void  
	  * @throws
	  * @date 2016年1月28日 下午5:06:50
	  */
	private static Long spider_jbxx(String djh,String qylx){
		GSspider_jx_jcxx jcxx = new GSspider_jx_jcxx();
		Spider.create(jcxx)
		.addUrl("http://gsxt.jxaic.gov.cn/ECPS/qyxxgsAction_initQyjbqk.action?nbxh="+djh+"&qylx="+qylx)
		.run();
//		System.out.println("----id----"+jcxx.getQyId());
		return jcxx.getQyId();
	}
	/**
	  * @author ldm
	  * @Description: 经营异常
	  * @param @param djh 登记号
	  * @param @param qylx 企业类型
	  * @return void  
	  * @throws
	  * @date 2016年1月29日 下午5:36:42
	  */
	private static void spider_jyyc(String djh,String qylx,Long qyId){
		GSspider_jx_jyyc jyyc = new GSspider_jx_jyyc();
		jyyc.setQyId(qyId);
		Spider.create(jyyc)
		//jyycxxAction_init.action?nbxh=3600006000042031
		.addUrl("http://gsxt.jxaic.gov.cn/ECPS/jyycxxAction_init.action?nbxh="+djh)
		.run();
	}
	/**
	  * @author Administrator
	  * @Description: 行政处罚
	  * @param @param djh 登记号
	  * @param @param qylx  企业类型
	  * @return void  
	  * @throws
	  * @date 2016年1月29日 下午5:36:42
	  */
	private static void spider_xzcf(String djh,String qylx,Long qyId){
		GSspider_jx_xzcf xzcf = new GSspider_jx_xzcf();
		xzcf.setQyId(qyId);
		Spider.create(xzcf)
		//xzcfxxAction_initXzcfxx.action?nbxh=3600006000042031
		.addUrl("http://gsxt.jxaic.gov.cn/ECPS/xzcfxxAction_initXzcfxx.action?nbxh="+djh)
		.run();
	}
	/**
	  * @author Administrator
	  * @Description: 严重违法
	  * @param @param djh 登记号
	  * @param @param qylx  企业类型
	  * @return void  
	  * @throws
	  * @date 2016年1月29日 下午5:36:42
	  */
	private static void spider_yzwf(String djh,String qylx,Long qyId){
		GSspider_jx_yzwf yzwf = new GSspider_jx_yzwf();
		yzwf.setQyId(qyId);
		Spider.create(yzwf)
		//yzwfxxAction_init.action
		.addUrl("http://gsxt.jxaic.gov.cn/ECPS/yzwfxxAction_init.action")
		.run();
	}
	/**
	  * @author Administrator
	  * @Description: 抽查抽检
	  * @param @param djh 登记号
	  * @param @param qylx  企业类型
	  * @return void  
	  * @throws
	  * @date 2016年1月29日 下午5:36:42
	  */
	private static void spider_cccj(String djh,String qylx,Long qyId){
		GSspider_jx_cccj cccj = new GSspider_jx_cccj();
		cccj.setQyId(qyId);
		Spider.create(cccj)
		//ccjcxxAction_init.action?nbxh=3600006000042031
		.addUrl("http://gsxt.jxaic.gov.cn/ECPS/ccjcxxAction_init.action?nbxh="+djh)
		.run();
	}
	
	/**
	  * @author ldm
	  * @Description: 根据关键字arg执行查询操作 在返回的页面中抽取nbxh和qylx的值 为获取企业信息的请求提供必要参数。
	  * @param @param arg
	  * @param @return  
	  * @return String[] --- String[0]为nbxh ,String[1]为qylx
	  * @throws
	  * @date 2016年2月15日 上午9:55:08
	  */
	private static String[] queryCom(String arg){
		String[] rest = new String[2];
		try {
			String url = "http://gsxt.jxaic.gov.cn/ECPS/qyxxgsAction_queryXyxx.action?flag=1&selectValue="+URLEncoder.encode(arg, "gb2312") ;
			String resulthtml = HttpUtil.getOnePage(url);
			int start = resulthtml.indexOf("onclick=\"showJbxx(");
			if(start>0){
				int end = start + resulthtml.substring(start).indexOf(")\">");
				String preResult = resulthtml.substring(start, end);
//				System.out.println("----------------"+preResult);
				String num = preResult.substring(preResult.indexOf("'")+1, preResult.indexOf("','"));
				String qylx = preResult.substring(preResult.indexOf("','")+3,preResult.indexOf("','", preResult.indexOf("','")+1));
//				System.out.println("num:"+num+" ; qylx:"+qylx);
				rest[0] = num;
				rest[1] = qylx;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rest;
	}
	
	/**
	  * @author ldm
	  * @Description: 抓取企业工商类信息
	  * @param @param name:企业名称 或 统一社会信用代码或注册号  
	  * @return void  
	  * @throws
	  * @date 2016年2月4日 上午11:59:22
	  */
	public static void catchData(String name){
		String[] re = queryCom(name);
		final String nbxh = re[0];
		final String qylx = re[1];
		final Long qyId = spider_jbxx(nbxh, qylx);
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		executorService.execute(new Runnable() {
			public void run() {
				spider_jyyc(nbxh, qylx,qyId);
			}
		});
		executorService.execute(new Runnable() {
			public void run() {
				spider_xzcf(nbxh, qylx,qyId);
			}
		});
		executorService.execute(new Runnable() {
			public void run() {
				spider_yzwf(nbxh, qylx,qyId);
			}
		});
		executorService.execute(new Runnable() {
			public void run() {
				spider_cccj(nbxh, qylx,qyId);
			}
		});
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
		
		catchData("联创光电");
		 
	}
	 
}
