package com.xxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mean
 * @since 2022-03-30
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 通过用户id查询用户列表
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);
}
