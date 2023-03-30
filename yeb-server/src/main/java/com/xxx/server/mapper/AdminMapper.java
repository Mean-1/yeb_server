package com.xxx.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mean
 * @since 2022-03-30
 */
public interface AdminMapper extends BaseMapper<Admin> {


    /**
     * 查询所有操作员
     * @param id
     * @param keywords
     * @return
     */
    List<Admin> getAllAdmins(@Param("id") Integer id, @Param("keywords") String keywords);
}
