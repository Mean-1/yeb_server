package com.xxx.server.mapper;

import com.xxx.server.pojo.AdminRole;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mean
 * @since 2022-03-30
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    Integer addAdminRole(@Param("adminId")Integer adminId,@Param("rids") Integer[] rids);
}
