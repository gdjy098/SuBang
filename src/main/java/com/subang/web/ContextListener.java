package com.subang.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import weixin.popular.support.TicketManager;
import weixin.popular.support.TokenManager;

import com.subang.util.Common;
import com.subang.util.SmsUtil;
import com.subang.util.StratUtil;

@WebListener
public class ContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		Common.init(sce.getServletContext());
		TokenManager.init(Common.getProperty("appid"), Common.getProperty("appsecret"));
		TicketManager.init(Common.getProperty("appid"));
		SmsUtil.init();
		StratUtil.init();
	}

	public void contextDestroyed(ServletContextEvent sce) {
		StratUtil.deinit();
		TicketManager.destroyed();
		TokenManager.destroyed();		
	}

}
