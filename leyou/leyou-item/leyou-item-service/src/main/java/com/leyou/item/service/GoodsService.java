package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Pandy
 * @Version 1.0
 */
@Service
public class GoodsService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper detailMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

    /**
     * 由分页条件查询spu
     * http://api.leyou.com/api/item/spu/page?key=&saleable=true&page=1&rows=5
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    public PageResult<SpuBo> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        //添加查询条件
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        //添加上下架的过滤条件 使用criteria就是为了求交集 上下架中只能搜到对应的上下架的商品 0或者1
        if (saleable != null){
            criteria.andEqualTo("saleable",saleable);
        }
        //添加分页
        PageHelper.startPage(page,rows);
        //执行查询 获取spu集合
        List<Spu> spus = this.spuMapper.selectByExample(example);
        PageInfo<Spu> spuPageInfo = new PageInfo<>(spus);
        //spu集合转化成spuBo的集合
        List<SpuBo> spuBos = spus.stream().map(spu -> {
            SpuBo spuBo = new SpuBo();
            BeanUtils.copyProperties(spu, spuBo);
            //查询品牌名称
            Brand brand = this.brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuBo.setBname(brand.getName());
            //查询分类名称
            List<String> names = this.categoryService.queryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            spuBo.setCname(StringUtils.join(names, "-"));
            return spuBo;
        }).collect(Collectors.toList());
        //返回pageResult<SpuBo> 类别 总数量
        return new PageResult<>(spuPageInfo.getTotal(),spuBos);
    }

    /**
     * 新增商品
     * @param spuBo
     */
    @Transactional
    public void saveGoods(SpuBo spuBo) {
        //先新增spu
        spuBo.setId(null);//防止恶意注入
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());//防止时间差
        this.spuMapper.insertSelective(spuBo);
        //新增spu_detail 前台比后台只增加一个id字段
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        this.detailMapper.insertSelective(spuBo.getSpuDetail());

        //新增sku
        spuBo.getSkus().forEach(sku -> {
            //递增sku
            sku.setId(null);
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insertSelective(sku);
            //新增stock
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insertSelective(stock);
        });
    }

    /**
     * // 根据spuId查询商品详情(spuDetail)和skus(编辑的回显)
     * oldGoods.spuDetail = await this.$http.loadData("/item/spu/detail/" + oldGoods.id);
     * oldGoods.skus = await this.$http.loadData("/item/sku/list?id=" + oldGoods.id);
     */
    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        return this.detailMapper.selectByPrimaryKey(spuId);
    }


    /**
     * 根据商品id查询商品详情集合(编辑的回显)
     *  oldGoods.skus = await this.$http.loadData("/item/sku/list?id=" + oldGoods.id);
     * @param spuId
     * @return
     */
    public List<Sku> querySkusBySpuId(Long spuId) {
        Sku record = new Sku();
        record.setSpuId(spuId);
        //根据spuId查询到sku  但是此时的库存未知
        List<Sku> skus = this.skuMapper.select(record);
        skus.forEach(sku -> {
            //库存的主键就是sku_id 查询到sku  转而去根据skuid查询库存 查到的库存封装进行sku
            Stock stock = this.stockMapper.selectByPrimaryKey(sku.getId());
            sku.setStock(stock.getStock());
        });
        return skus;
    }
}
