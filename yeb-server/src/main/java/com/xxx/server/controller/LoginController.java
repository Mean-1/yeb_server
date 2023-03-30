package com.xxx.server.controller;

import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.AdminLoginParam;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Keafmd
 *
 * @ClassName: LoginController
 * @Description: 登录
 * @author: liuchen
 * @date: 2022/4/5 11:39
 * @Blog:
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {
    @Autowired
    private IAdminService adminService;
    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
        return adminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCode(),request);
    }

    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){//这里principal是由于实现类里更新security登录用户对象,这里才能获取对应的用户名
        if(null==principal){
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUsername(username);
        admin.setPassword(null);
        admin.setRoles(adminService.getRoles(admin.getId()));
        return  admin;
    }


    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    /**
     * 这里主要功能交给前端完成,这里只是起了返回的作用
     */
    public RespBean logout(){
        return RespBean.success("注销成功");
    }
}
