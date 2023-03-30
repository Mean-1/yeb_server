package com.xxx.server.controller;

import com.xxx.server.mapper.MenuMapper;
import com.xxx.server.pojo.Menu;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Keafmd
 *
 * @ClassName: HelloController
 * @Description: 测试
 * @author: liuchen
 * @date: 2022/4/12 11:38
 * @Blog:
 */
@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello(){
        return "hello";
    }

}
