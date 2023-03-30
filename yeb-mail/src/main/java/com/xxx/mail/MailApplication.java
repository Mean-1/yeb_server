package com.xxx.mail;

import com.xxx.server.pojo.MailConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Keafmd
 *
 * @ClassName: YebApplication
 * @Description:启动类
 * @author: liuchen
 * @date: 2022/6/30 21:57
 * @Blog:
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MailApplication {


    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class,args);
    }

    @Bean
    public Queue queue(){
        return new Queue(MailConstants.MAIL_QUEUE_NAME);
    }
}
