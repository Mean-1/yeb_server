package com.xxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mean
 * @since 2022-03-30
 */
public interface IAdminService extends IService<Admin> {
    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);

    /**
     * 获取当前登录用户的信息
     * @param username
     * @return
     */
    Admin getAdminByUsername(String username);

    /**
     * 通过用户id查询用户列表
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);

    /**
     * 查询所有操作员
     * @param keywords
     * @return
     */
    List<Admin> getAllAdmins(String keywords);

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    RespBean updateAdminRole(Integer adminId, Integer[] rids);
}
