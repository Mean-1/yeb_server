package com.xxx.server;

import com.xxx.server.config.RedisConfig;
import com.xxx.server.mapper.MenuMapper;
import com.xxx.server.pojo.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * Keafmd
 *
 * @ClassName: YebApplication
 * @Description:启动类
 * @author: liuchen
 * @date: 2022/3/30 16:04
 * @Blog:
 */
@SpringBootApplication
@MapperScan("com.xxx.server.mapper")
@EnableSwagger2
@EnableWebMvc
@EnableScheduling
//测试所需要的注解
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = YebApplication.class )
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = {RedisConfig.class})
public class YebApplication {
    /*@Autowired
    private MenuMapper menuMapper;
    @Test
    public void test() {
        List<Menu> map = menuMapper.getMenusByAdminId(2);
        for (Menu menu : map) {
            System.out.println(menu);
        }
    }*/
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    public void testQueryTime() {
//        long start = System.currentTimeMillis();
//        ResponseEntity<String> response = restTemplate.getForEntity("/system/cfg/menu", String.class);
//        long end = System.currentTimeMillis();
//        long queryTime = end - start;
//        System.out.println("Query time: " + queryTime + " ms");
//        // 验证查询结果是否正确
////        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
////        assertThat(response.getBody()).isEqualTo("Expected response body");
//    }

    public static void main(String[] args) {
        SpringApplication.run(YebApplication.class,args);
    }
}
