package com.sys.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class SpringSecurityListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//1、得到Spring IOC容器
		ApplicationContext ctxApplicationContext=WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
		//2、取出FilterSecurityInterceptor对象
		FilterSecurityInterceptor filterSecurityInterceptor=ctxApplicationContext.getBean(FilterSecurityInterceptor.class);
		
		//3、从IOC容器中取出SecurityMetadaSource
		FilterInvocationSecurityMetadataSource newSource=(FilterInvocationSecurityMetadataSource) ctxApplicationContext.getBean("securityMetadataSource");
		//4、调用2的方法，参数为3
		filterSecurityInterceptor.setSecurityMetadataSource(newSource);
		
	}

}
