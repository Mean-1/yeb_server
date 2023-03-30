package com.xxx.server.config.security.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Keafmd
 *
 * @ClassName: JwtAuthenticationTokenFilter
 * @Description: JWT登录授权过滤器
 * @author: liuchen
 * @date: 2022/4/8 10:02
 * @Blog:
 */

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired//@Resource 好像也可以
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    //重写前置拦截
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //通过key拿到value
        String authHeader = request.getHeader(this.tokenHeader);
        //存在token
        if(null!=authHeader&&authHeader.startsWith(tokenHead)){
            String authToken = authHeader.substring(tokenHead.length());
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            //token存在用户名但未登录(后半段也就是判断Spring全局上下文拿不到我们的一个对象,就相当于未登录)
            if(null!=username && null == SecurityContextHolder.getContext().getAuthentication()){
                //登录
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //验证token是否有效,重新设置用户对象
                if(jwtTokenUtil.validateToken(authToken,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new
                            UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
