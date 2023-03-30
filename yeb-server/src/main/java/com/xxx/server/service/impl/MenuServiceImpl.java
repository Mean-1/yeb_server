package com.xxx.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.server.mapper.MenuMapper;
import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.Menu;
import com.xxx.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mean
 * @since 2022-03-30
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 通过用户id查询菜单
     * @return
     */
    @Override
    public List<Menu> getMenusByAdminId() {
        Integer adminId = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        //从redis中获取数据
        List<Menu> menus = (List<Menu>) valueOperations.get("menu_" + adminId);
        //如果未空，则去数据库中抓取
        if(CollectionUtils.isEmpty(menus)){
            menus=menuMapper.getMenusByAdminId(adminId);
            //将数据导入redis中
            valueOperations.set("menu_"+adminId,menus);
        }

        return menus;
    }

    /**
     * 根据角色获取菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenusWithRole() {

        return menuMapper.getMenusWithRole();
    }

    /**
     * 查询所有菜单
     * @return
     */
    @Override
    public List<Menu> getAllMenus() {

        return menuMapper.getAllMenus();
    }
}
