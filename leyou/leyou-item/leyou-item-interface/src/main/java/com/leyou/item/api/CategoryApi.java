package com.leyou.item.api;

import com.leyou.item.pojo.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Pandy
 * @Version 1.0
 * 代码经过优化 注释中的是未优化的
 * 出错自然显示500 所以不用try catch
 */
public interface CategoryApi {

    /**
     *根据父节点的id查询子节点
     * @param pid
     * @return
     */
    @GetMapping("list")
    public List<Category> queryCategoriesByPid(@RequestParam(value = "pid",defaultValue = "0") Long pid);



    /**
     * 根据商品分类id 查询商品分类名称
     * @param ids
     * @return
     */
    @GetMapping
    public List<String> queryNamesByIds(@RequestParam("ids")List<Long> ids);
}
