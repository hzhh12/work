package com.sys.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
@Service("securityMetadataSource")
public class FilterInvocationSecurityMetadataSourceFactoryBean implements 
			FactoryBean<FilterInvocationSecurityMetadataSource>{
	@Autowired
	private PreparFilterInvocationSecurityMetadataSource preparFilterInvocationSecurityMetadataSource;

	//返回实际的对象
	@Override
	public FilterInvocationSecurityMetadataSource getObject()
			throws Exception {
		Map<String, List<String>>srcMap=preparFilterInvocationSecurityMetadataSource.getSrcMap();
		LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap=new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
		
		for(Map.Entry<String, List<String>>map:srcMap.entrySet()){
			String path=map.getKey();
			List<String> roles=map.getValue();
			RequestMatcher requestMatcher=new AntPathRequestMatcher(path);
			Collection<ConfigAttribute>attributes=new ArrayList<ConfigAttribute>();
			for(String role:roles){
				attributes.add(new SecurityConfig(role));
			}
			requestMap.put(requestMatcher, attributes);
		}
		
		FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource=new DefaultFilterInvocationSecurityMetadataSource(requestMap);
		return filterInvocationSecurityMetadataSource;
	}

	//返回实际的Bean的类型
	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return DefaultFilterInvocationSecurityMetadataSource.class;
	}

	//返回Bean是否是单例的
	@Override
	public boolean isSingleton() {
		return true;
	}

}
