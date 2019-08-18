package com.leyou.item.api;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Pandy
 * @Version 1.0
 */
public interface BrandApi {
    /**
     * 根据查询条件分页并排序查询品牌信息
     * http://api.leyou.com/api/item/brand/page?key=&page=1&rows=5&sortBy=id&desc=false
     * key:    搜索条件
     * page:   当前页
     * rows:   每页大小
     * sortBy: 排序字段
     * desc:   是否降序
     * @return
     */
    @GetMapping("page")
    public PageResult<Brand> queryBrandsByPage(
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows,
            @RequestParam(value = "sortBy",defaultValue = "id")String sortBy,
            @RequestParam(value = "desc",required = false)Boolean desc);

    /**
     * 根据分类id查询品牌列表
     * http://api.leyou.com/api/item/brand/cid/3
     */
    @GetMapping("cid/{cid}")
    public List<Brand> queryBrandsByCid(@PathVariable("cid")Long cid);

    /**
     * 根据商品品牌id查询商品的品牌
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Brand queryBrandById(@PathVariable("id")Long id);
}
