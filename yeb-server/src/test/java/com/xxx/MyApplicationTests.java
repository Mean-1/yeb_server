package com.xxx;

import com.xxx.server.config.RedisConfig;
import com.xxx.server.mapper.MenuMapper;
import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.Menu;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Keafmd
 *
 * @ClassName: test1
 * @Description: 测试Menu_sql语句查询
 * @author: liuchen
 * @date: 2022/5/1 16:21
 * @Blog:
 */
/*
    这里测试不了，初步推测是因为这里无法调用bean，但是在YebApplication类中就能够测试
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = test1.class )
//
////@MybatisPlusTest
////@AutoConfigureTestDatabase
//public class test1 {
//    @Autowired
//    private MenuMapper menuMapper;
//    @Test
//    public void test() {
//        List<Menu> map = menuMapper.getMenusByAdminId(2);
//        for (Menu menu : map) {
//            System.out.println(menu);
//        }
//    }
//
//}
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = {RedisConfig.class})
public class MyApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testQueryTime() {
        long start = System.currentTimeMillis();
        ResponseEntity<String> response = restTemplate.getForEntity("/system/cfg/menu", String.class);
        long end = System.currentTimeMillis();
        long queryTime = end - start;
        System.out.println("Query time: " + queryTime + " ms");
        // 验证查询结果是否正确
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isEqualTo("Expected response body");
    }

}

