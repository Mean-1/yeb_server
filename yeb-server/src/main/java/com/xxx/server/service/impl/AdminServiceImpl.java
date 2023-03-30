package com.xxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.xxx.server.config.security.component.JwtTokenUtil;
import com.xxx.server.mapper.AdminMapper;
import com.xxx.server.mapper.AdminRoleMapper;
import com.xxx.server.mapper.RoleMapper;
import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.AdminRole;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.pojo.Role;
import com.xxx.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mean
 * @since 2022-03-30
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;//这里会爆红,idea的问题，这里找的到adminMapper,因为@autowired是Spring提供的,@mapper是mybatis提供的，两者关联不上
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        //验证码判断
        String captcha = (String) request.getSession().getAttribute("captcha");
        if(StringUtils.isNullOrEmpty(captcha)||!captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码输入错误，请重新输入！");
        }

        //登录判断
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);//UserDetailsService接口实现类是UserDetails,都是在Spring security 依赖下
        if(null==userDetails){
            return RespBean.error("用户名错误");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){

            return RespBean.error("密码错误");
        }
        if(!userDetails.isEnabled()){
            return RespBean.error("账号已被禁用，请联系管理员");
        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken= new
                UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String > tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return RespBean.success("登录成功",tokenMap);
    }

    /**
     * 获取当前登录用户的信息
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUsername(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("enabled",true));
    }

    /**
     * 通过用户id查询用户列表
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }

    /**
     * 查询所有操作员
     * @param keywords
     * @return
     */
    @Override
    public List<Admin> getAllAdmins(String keywords) {

        return adminMapper.getAllAdmins(((Admin)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(),
                keywords);
    }

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    @Override
    @Transactional
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {

        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId",adminId));

        Integer result = adminRoleMapper.addAdminRole(adminId, rids);

        if(result==rids.length){
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }


}
