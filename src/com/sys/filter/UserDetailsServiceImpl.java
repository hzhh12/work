package com.sys.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //关键的sql
        String sql="SELECT username,PASSWORD,NAME FROM USER u \n" +
                "JOIN user_role ur ON u.id=ur.user_id \n" +
                "JOIN role r ON r.id=ur.role_id \n" +
                "WHERE username=?";
        List<Map<String,Object>> results=jdbcTemplate.queryForList(sql, username);
        if (results==null || results.size()==0){
            throw new UsernameNotFoundException("");
        }

        User user=null;
        String password=(String)results.get(0).get("PASSWORD");
        boolean enabled=true;//账户是否可用
        boolean accountNonExpired=true;//当前用户是否没过期
        boolean credentialsNonExpired=true;//凭证是否没过期
        boolean accountNonLocked=true;//帐号是否没锁定
        Collection<GrantedAuthority>authorities=new HashSet<GrantedAuthority>();//赋予权限
        for(Map<String,Object>map:results){
            String role=(String)map.get("NAME");
            GrantedAuthority authority=new GrantedAuthorityImpl(role);
            authorities.add(authority);
        }

        user=new User(username, password, enabled, accountNonExpired,credentialsNonExpired, accountNonLocked,  authorities);
        return user;
        
    }
}
