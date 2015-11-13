package com.sys.filter;

import java.util.List;
import java.util.Map;
/**
 * 返回通用的srcMap
 * @author Administrator
 *
 */
public interface PreparFilterInvocationSecurityMetadataSource {

	Map<String, List<String>>getSrcMap();
}
