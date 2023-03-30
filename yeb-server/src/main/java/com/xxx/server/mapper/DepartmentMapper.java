package com.xxx.server.mapper;

import com.xxx.server.pojo.Department;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mean
 * @since 2022-03-30
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 查询所有部门
     * @return
     */
    List<Department> getAllDepartments(Integer parentId);

    /**
     * 添加部门
     * @param dep
     * @return
     */
    void addDep(Department dep);

    /**
     * 删除部门
     * @param dep
     * @return
     */
    void deleteDep(Department dep);
}
