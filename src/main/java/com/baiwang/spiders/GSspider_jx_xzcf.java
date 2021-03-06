/**
 *Copyright (c) 1997, 2015,BEST WONDER CO.,LTD. All rights reserved.
 */

package com.baiwang.spiders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;


/**
  * @ClassName: GSspider_jx
  * @Description: 工商类行政处罚信息抓取工具——江西站点
  * @author Administrator
  * @date 2016年1月29日 下午1:36:01
  */
public class GSspider_jx_xzcf implements PageProcessor {

	Site site = Site.me().setRetryTimes(3).setSleepTime(1).setCharset("gb2312");
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("jdbc");
	private Long qyId;
	
	public void setQyId(Long qyId) {
		this.qyId = qyId;
	}
	/**
	  * @author Administrator
	  * @Description: 定义抓取规则
	  * @param @param page  
	  * @throws
	  * @date 2016年1月29日 下午1:21:07
	  */
	public void process(Page page) {
		List<String> result = page.getHtml().xpath("//td/text()").all();
		System.out.println(this.getClass().getName()+":  "+result.toString());
		Connection connect = null;
		PreparedStatement preStat = null;
		String sql = null;
		try {
			connect = DriverManager.getConnection(bundle.getString("jdbc.url"),bundle.getString("jdbc.username"),bundle.getString("jdbc.password"));
		
			sql = "insert into pub_gs_others(sh,jlsj,type,detail,datatype,qyid,gsmc) values(?,?,?,?,?,?,?)";
			preStat = connect.prepareStatement(sql);
			preStat.setString(1, result.get(1));
			preStat.setString(2, result.get(5));
			preStat.setString(3, result.get(2));
			preStat.setString(4, result.get(3));
			preStat.setString(5, "1");
			preStat.setLong(6, this.qyId);
			preStat.setString(7, null);
			if(preStat != null) preStat.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}finally {
			try {
				if(connect != null)
				connect.close();
				if(preStat != null)
				preStat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Site getSite() {
		return this.site;
	}
}
