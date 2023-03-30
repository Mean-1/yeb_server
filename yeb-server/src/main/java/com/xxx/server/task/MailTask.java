package com.xxx.server.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xxx.server.mapper.EmployeeMapper;
import com.xxx.server.pojo.Employee;
import com.xxx.server.pojo.MailConstants;
import com.xxx.server.pojo.MailLog;
import com.xxx.server.service.IMailLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Keafmd
 *
 * @ClassName: MailTask
 * @Description: 邮件发送定时任务
 * @author: liuchen
 * @date: 2022/7/20 16:05
 * @Blog:
 */
@Component
public class MailTask {

    @Autowired
    private IMailLogService mailLogService;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 邮件定时发送任务
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask(){
        //把状态为发送中的邮件提取出来重新发送
        List<MailLog> list = mailLogService.list(new QueryWrapper<MailLog>().eq("status", 0)
                .lt("tryTime", LocalDateTime.now()));
        list.forEach(mailLog -> {
            //如果重试次数超过3次后，更新状态为投递失败（2），不再重试
            if(3<=mailLog.getCount()){
                mailLogService.update(new UpdateWrapper<MailLog>().set("status",2).eq("msgId",mailLog.getMsgId()));
            }else{
                mailLogService.update(new UpdateWrapper<MailLog>().set("count",mailLog.getCount()+1)
                        .set("updateTime",LocalDateTime.now()).set("tryTime",LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT))
                        .eq("msgId",mailLog.getMsgId()));
                Employee emp = employeeMapper.getEmployee(mailLog.getEid()).get(0);
                //发送消息
                rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,emp
                        ,new CorrelationData(mailLog.getMsgId()));
            }
        });
    }
}
