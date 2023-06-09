package com.xxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.server.mapper.MenuRoleMapper;
import com.xxx.server.pojo.MenuRole;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.service.IMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mean
 * @since 2022-03-30
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    @Override
    //事务的注解
    @Transactional
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        //删除指定rid全部菜单
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid",rid));

        if(null==mids||0==mids.length){
            return  RespBean.success("更新成功");
        }
        //添加菜单
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        if(result==mids.length){

            return RespBean.success("更新成功");
        }

        return RespBean.error("更新失败");
    }
}
