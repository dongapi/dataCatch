/**
 *Copyright (c) 1997, 2015,BEST WONDER CO.,LTD. All rights reserved.
 */

package com.baiwang.excuter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.baiwang.spiders.GSspider_bj_cccj;
import com.baiwang.spiders.GSspider_bj_jcxx;
import com.baiwang.spiders.GSspider_bj_jyyc;
import com.baiwang.spiders.GSspider_bj_xzcf;
import com.baiwang.spiders.GSspider_bj_yzwf;
import com.baiwang.spiders.util.HttpUtil;

import us.codecraft.webmagic.Spider;

/**
  * @ClassName: DataCatchExcuter
  * @Description: 北京企业信息抓取工具类
  * @author Administrator
  * @date 2016年1月29日 下午2:22:48
  */
public class DataCatchExcuter_bj {
	
//	private static final Logger logger = Logger.getLogger(DataCatchExcuter_bj.class);
	
	/**
	  * @author ldm
	  * @Description: 企业基本信息（工商类抓取）
	  * @param @param djh 企业登记号
	  * @param @param qylx 企业类型	
	  * @return void  
	  * @throws
	  * @date 2016年1月28日 下午5:06:50
	  */
	private static Long spider_jbxx(String entId,String credit_ticket){
		GSspider_bj_jcxx jcxx = new GSspider_bj_jcxx();
		Spider.create(jcxx)
		//http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!openEntInfo.dhtml?entId=20e38b8b4d194cc8014d1e12e97f6374&credit_ticket=8173D03BDAA53D4E34282E704327D289
		.addUrl("http://qyxy.baic.gov.cn/gjjbj/gjjQueryCreditAction!openEntInfo.dhtml?entId="+entId+"&credit_ticket="+credit_ticket)
		.run();
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
		GSspider_bj_jyyc jyyc = new GSspider_bj_jyyc();
		jyyc.setQyId(qyId);
		Spider.create(jyyc)
		//http://qyxy.baic.gov.cn/gsgs/gsxzcfAction!list_jyycxx.dhtml?entId=20e38b8b4d194cc8014d1e12e97f6374&clear=true
		.addUrl("http://qyxy.baic.gov.cn/gsgs/gsxzcfAction!list_jyycxx.dhtml?entId="+djh+"&clear=true")
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
		GSspider_bj_xzcf xzcf = new GSspider_bj_xzcf();
		xzcf.setQyId(qyId);
		Spider.create(xzcf)
		//http://qyxy.baic.gov.cn/gsgs/gsxzcfAction!list.dhtml?entId=20e38b8b4d194cc8014d1e12e97f6374&clear=true
		.addUrl("http://qyxy.baic.gov.cn/gsgs/gsxzcfAction!list.dhtml?entId="+djh+"&clear=true")
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
		GSspider_bj_yzwf yzwf = new GSspider_bj_yzwf();
		yzwf.setQyId(qyId);
		Spider.create(yzwf)
		//http://qyxy.baic.gov.cn/gsgs/gsxzcfAction!list_yzwfxx.dhtml?ent_id=20e38b8b4d194cc8014d1e12e97f6374&clear=true
		.addUrl("http://qyxy.baic.gov.cn/gsgs/gsxzcfAction!list_yzwfxx.dhtml?ent_id="+djh+"&clear=true")
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
		GSspider_bj_cccj cccj = new GSspider_bj_cccj();
		cccj.setQyId(qyId);
		Spider.create(cccj)
		//http://qyxy.baic.gov.cn/gsgs/gsxzcfAction!list_ccjcxx.dhtml?ent_id=20e38b8b4d194cc8014d1e12e97f6374&clear=true
		.addUrl("http://qyxy.baic.gov.cn/gsgs/gsxzcfAction!list_ccjcxx.dhtml?ent_id="+djh+"&clear=true")
		.run();
	}
	
	/**
	  * @author ldm
	  * @Description: 根据关键字arg执行查询操作 在返回的页面中抽取entId和credit_ticket的值 为获取企业信息的请求提供必要参数。
	  * @param @param arg
	  * @param @return  
	  * @return String[] --- String[0]为entId ,String[1]为credit_ticket
	  * @throws
	  * @date 2016年2月15日 上午9:55:08
	  */
	private static String[] queryCom(String arg){
		String[] rest = new String[2];
		try {
			String url = "http://gsxt.jxaic.gov.cn/ECPS/qyxxgsAction_queryXyxx.action?flag=1&selectValue="+URLEncoder.encode(arg, "gb2312") ;
			String resulthtml = HttpUtil.getOnePage(url);
			int start = resulthtml.indexOf("onclick=\"openEntInfo(");
			if(start>0){
				int end = start + resulthtml.substring(start).indexOf(")");
				String preResult = resulthtml.substring(start, end);
				String num = preResult.substring(preResult.indexOf("'")+1, preResult.indexOf("','"));
				String qylx = preResult.substring(preResult.indexOf("','")+3,preResult.indexOf("','", preResult.indexOf("','")+1));
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
//		final String nbxh = re[0];
//		final String qylx = re[1];
		final String nbxh = "20e38b8b4d194cc8014d1e12e97f6374";
		final String qylx = "8173D03BDAA53D4E34282E704327D289";
		final Long qyId = spider_jbxx(nbxh, qylx);
		System.out.println(qyId);
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
		
		catchData("百望股份");
		 
	}
	 
}
