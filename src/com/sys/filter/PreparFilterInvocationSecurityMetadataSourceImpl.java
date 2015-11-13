package com.sys.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
@Service("preparFilterInvocationSecurityMetadataSource")
public class PreparFilterInvocationSecurityMetadataSourceImpl implements PreparFilterInvocationSecurityMetadataSource{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public Map<String, List<String>> getSrcMap() {
		String sql = "SELECT res_string path, role.`name` role "
				+ "FROM resc JOIN resc_role rr "
				+ "ON resc.`id` = rr.`resc_id` " + "JOIN role "
				+ "ON role.`id` = rr.`role_id` ";
		// 先创建一个 srcMap, 然后再由 srcMap 来构建 requestMap
		// srcMap 是 Map<String, List<String>> 类型: 其中键为 path, 值为 path 对应的 role
		// 的集合
		// index.jsp - ["ROLE_ADMIN", "ROLE_USER"]
		// admin.jsp - ["ROLE_USER"]
		Map<String, List<String>>srcMap=new HashMap<String, List<String>>();
		List<Map<String, Object>>result=jdbcTemplate.queryForList(sql);
		for (Map<String, Object>map:result) {
			String path=(String) map.get("path");
			String role=(String) map.get("role");
			List<String>roles=srcMap.get(path);
			if(!srcMap.containsKey(path)){
				roles=new ArrayList<String>();
				srcMap.put(path, roles);
			}
			roles.add(role);
		}
		System.out.println(srcMap);
		return srcMap;
	}

}
