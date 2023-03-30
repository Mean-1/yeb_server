package com.xxx.server.config.security.component;

import com.xxx.server.pojo.Menu;
import com.xxx.server.pojo.Role;
import com.xxx.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * Keafmd
 *
 * @ClassName: CustomFilter
 * @Description: 根据请求url分析请求所需的角色
 * @author: liuchen
 * @date: 2022/5/23 14:02
 * @Blog:
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IMenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取请求的url
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        List<Menu> menusWithRole = menuService.getMenusWithRole();

        for (Menu menu : menusWithRole) {
            //判断请求的url与菜单角色是否匹配
            if(antPathMatcher.match(menu.getUrl(),requestUrl)){
                String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(str);// 有个易理解错的点：只匹配到一个url，那怎么获取所有与这个url对应role的name---
                // ---解释：一对多，在resultMap中用collection集合解决了这个一对多的问题
            }
        }
        //没匹配上的url默认登录即可访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
