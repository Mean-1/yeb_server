package com.xxx.server.exception;

import com.xxx.server.pojo.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Keafmd
 *
 * @ClassName: GlobalException
 * @Description: 全局异常处理
 * @author: liuchen
 * @date: 2022/5/25 22:09
 * @Blog:
 */
@RestControllerAdvice
public class GlobalException {

    //SQL异常处理
    @ExceptionHandler(SQLException.class)
    public RespBean mysqlException(SQLException e){
        if( e instanceof SQLIntegrityConstraintViolationException){
            return RespBean.success("该数据有相关数据，操作失败！");
        }
        return RespBean.error("数据库异常，操作失败！");
    }
}
