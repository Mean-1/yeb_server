package com.xxx.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Keafmd
 *
 * @ClassName: RespPageBean
 * @Description:分页公共返回对象
 * @author: liuchen
 * @date: 2022/6/17 17:09
 * @Blog:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespPageBean {
    /**
     * 总条数
     */
    private Long total;

    /**
     * 数据List
     */
    private List<?> data;
}
