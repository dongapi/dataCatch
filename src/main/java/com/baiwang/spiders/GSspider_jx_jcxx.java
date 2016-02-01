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
  * @Description: 工商类基础信息抓取工具——江西站点
  * @author Administrator
  * @date 2016年1月29日 下午1:36:01
  */
public class GSspider_jx_jcxx implements PageProcessor {

	Site site = Site.me().setRetryTimes(3).setSleepTime(1).setCharset("utf-8");
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("jdbc");
	/**
	  * @author Administrator
	  * @Description: 定义抓取规则
	  * @param @param page  
	  * @throws
	  * @date 2016年1月29日 下午1:21:07
	  */
	public void process(Page page) {
		List<String> result = page.getHtml().xpath("//td/text()").all();
		System.out.println(result.toString());
		Connection connect = null;
		PreparedStatement preStat = null;
		String sql = null;
		try {
			connect = DriverManager.getConnection(bundle.getString("jdbc.url"),bundle.getString("jdbc.username"),bundle.getString("jdbc.password"));
		
			if(result != null && result.size() == 12){
				sql = "insert into pub_gs_base(shxydm,gsmc,gslx,pname,address,yyqxs,yyqxz,jyfw,djjg,hzrq,clrq,djzt) values(?,?,?,?,?,?,?,?,?,?,?,?)";
				preStat = connect.prepareStatement(sql);
				preStat.setString(1, result.get(0));
				preStat.setString(2, result.get(1));
				preStat.setString(3, result.get(2));
				preStat.setString(4, result.get(3));
				preStat.setString(5, result.get(4));
				preStat.setString(6, result.get(5));
				preStat.setString(7, result.get(6));
				preStat.setString(8, result.get(7));
				preStat.setString(9, result.get(8));
				preStat.setString(10, result.get(9));
				preStat.setString(11, result.get(10));
				preStat.setString(12, result.get(11));
			}else if(result != null && result.size() == 14){
				sql = "insert into pub_gs_base(shxydm,gsmc,gslx,pname,zczb,clrq,address,yyqxs,yyqxz,jyfw,djjg,hzrq,djzt) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				preStat = connect.prepareStatement(sql);
				preStat.setString(1, result.get(0));
				preStat.setString(2, result.get(1).toString());
				preStat.setString(3, result.get(2));
				preStat.setString(4, result.get(3));
				preStat.setString(5, result.get(4));
				preStat.setString(6, result.get(5));
				preStat.setString(7, result.get(6));
				preStat.setString(8, result.get(7));
				preStat.setString(9, result.get(8));
				preStat.setString(10, result.get(9));
				preStat.setString(11, result.get(10));
				preStat.setString(12, result.get(11));
				preStat.setString(13, result.get(12));
			}
//			if(preStat != null) preStat.executeUpdate();
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
