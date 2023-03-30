package com.xxx.server.config.security.component;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Keafmd
 *
 * 权限控制
 * @ClassName: CustomUrlDecisionManager
 * @Description: 判断用户对象
 * @author: liuchen
 * @date: 2022/5/24 22:02
 * @Blog:
 */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes) {
            //当前url所需角色
            String needRole = configAttribute.getAttribute();
            //判断是否登录即可访问的角色，此角色在CustomFilter中
            if("ROLE_LOGIN".equals(needRole)){
                //判断是否登录
                //判断这个“认证”(authentication)对象是否属于“匿名用户身份验证令牌”(AnonymousAuthenticationToken)这个类，总体意思是判断是否为游客登录
                if(authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("尚未登录，请登录！");

                }else {
                    return;
                }
            }

            //判断用户角色是否为url所需角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            for (GrantedAuthority authority : authorities) {
                if(authority.getAuthority().equals(needRole)){
                    return;
                }


            }


        }
        throw new AccessDeniedException("权限不足，请联系管理员！");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
