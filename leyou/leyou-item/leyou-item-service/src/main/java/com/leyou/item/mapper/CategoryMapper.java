package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;


/**
 * @Author: Pandy
 * @Version 1.0
 * SelectByIdListMapper 根据list中的条件查询相对应的值
 */
public interface CategoryMapper extends Mapper<Category>,SelectByIdListMapper<Category,Long> {
}
