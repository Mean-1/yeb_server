package com.xxx.server.config.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.server.pojo.RespBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Keafmd
 *
 * @ClassName: RestfulAccessDeniedHandler
 * @Description: 当访问接口没有权限时，自定义返回结果
 * @author: liuchen
 * @date: 2022/4/11 22:07
 * @Blog:
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Application/json");
        PrintWriter out = response.getWriter();
        RespBean bean = RespBean.error("权限不足，请联系管理员!");
        bean.setCode(403);
        out.write(new ObjectMapper().writeValueAsString(bean));
        out.flush();
        out.close();
    }
}
