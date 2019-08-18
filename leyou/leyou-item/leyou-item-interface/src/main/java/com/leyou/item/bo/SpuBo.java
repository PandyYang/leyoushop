package com.leyou.item.bo;

import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import lombok.Data;

import java.util.List;

/**
 * @Author: Pandy
 * @Version 1.0
 * Spu的扩展属性 需要向页面传输cname以及bname
 */
@Data
public class SpuBo extends Spu {
    private String cname;

    private String bname;

    private SpuDetail spuDetail;

    private List<Sku> skus;
}
