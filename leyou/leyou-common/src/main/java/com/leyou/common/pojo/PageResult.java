package com.leyou.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Pandy
 * @Version 1.0
 * 分页
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    //总条数
    private Long total;
    //总页数
    private Integer totalPage;
    //具体类目
    private List<T> items;

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }
}
