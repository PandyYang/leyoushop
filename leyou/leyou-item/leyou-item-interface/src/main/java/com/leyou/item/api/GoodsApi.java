package com.leyou.item.api;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Pandy
 * @Version 1.0
 */
public interface GoodsApi {
    /**
     * 同步发起请求，根据spuId查询商品详情(spuDetail)
     * oldGoods.spuDetail = await this.$http.loadData("/item/spu/detail/" + oldGoods.id);
     */
    @GetMapping("spu/detail/{spuId}")
    public SpuDetail querySpuDetailBySpuId(@PathVariable("spuId")Long spuId);

    /**
     * 根据条件分页查询spu(对应前端商品列表中的商品管理)
     * http://api.leyou.com/api/item/spu/page?key=&saleable=true&page=1&rows=5
     */

    @GetMapping("spu/page")
    public PageResult<SpuBo> querySpuByPage(
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value = "saleable",required = false)Boolean saleable,
            @RequestParam(value = "page",defaultValue ="1" )Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows
    );

    /**
     * 根据spuId查询商品详情集合
     *  oldGoods.skus = await this.$http.loadData("/item/sku/list?id=" + oldGoods.id);
     * @param spuId
     * @return
     */
    @GetMapping("sku/list")
    public List<Sku> querySkusBySpuId(@RequestParam("id")Long spuId);
}
