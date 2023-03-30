package com.xxx.server.controller;


import com.xxx.server.pojo.*;
import com.xxx.server.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mean
 * @since 2022-03-30
 */
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IPoliticsStatusService politicsStatusService;

    @Autowired
    private IJoblevelService joblevelService;

    @Autowired
    private INationService nationService;

    @Autowired
    private IPositionService positionService;

    @Autowired
    private IDepartmentService departmentService;

    //这里有问题(未解决)
    @ApiOperation(value = "获取所有员工（分页）")
    @GetMapping("/")
    public RespPageBean getEmployee(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    Employee employee,
                                    LocalDate[] beginDateScope){//这里的时间传值进来转换不了，还未解决（全局字符串转时间没成功，这里采用局部转换解决————
                                                            // 但是用局部转换的方法后，当beginDateScope为空时,就会报异常）
//        LocalDate [] beginDate =null;
//        if(0==beginDateScope.length){
//            //局部String转LocalDate
//            beginDate = new LocalDate[beginDateScope.length];
//            int i=0;
//            for (String s : beginDateScope) {
//                beginDate[i]=LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                i++;
//            }
//        }


        return employeeService.getEmployeeByPage(currentPage,size,employee,beginDateScope);
    }

    @ApiOperation(value = "获取政治面貌")
    @GetMapping("/politicsstatus")
    public List<PoliticsStatus> getAllPoliticsStatus(){
        return politicsStatusService.list();
    }

    @ApiOperation(value = "获取所以职称")
    @GetMapping("/joblevels")
    public List<Joblevel> getAllJoblevels(){
        return joblevelService.list();
    }

    @ApiOperation(value = "获取所有民族")
    @GetMapping("/nations")
    public List<Nation> getAllNations(){
        return nationService.list();
    }

    @ApiOperation(value = "获取所有职位")
    @GetMapping("/positions")
    public List<Position> getAllPositions(){
        return positionService.list();
    }

    @ApiOperation(value = "获取所有部门")
    @GetMapping("/departments")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @ApiOperation(value = "获取最大的ID工号")
    @GetMapping("/maxWorkID")
    public RespBean maxWorkID(){
        return employeeService.maxWorkID();
    }

    @ApiOperation(value = "添加员工")
    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee){
        return employeeService.addEmp(employee);
    }

    @ApiOperation(value = "更新员工")
    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee){
        if(employeeService.updateById(employee)){
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation(value = "删除员工")
    @DeleteMapping("/{id}")
    public RespBean deleteEmp(@PathVariable Integer id){
        if(employeeService.removeById(id)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
