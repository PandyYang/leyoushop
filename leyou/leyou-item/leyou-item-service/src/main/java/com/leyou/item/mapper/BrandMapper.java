package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: Pandy
 * @Version 1.0
 */
public interface BrandMapper extends Mapper<Brand> {

    /**
     * 品牌新增
     * @param cid
     * @param bid
     */
    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid}, #{bid})")
    int insertCategoryAndBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     * 根据分类id查询品牌列表
     * @param cid
     * @return
     */
    @Select("SELECT * FROM  tb_brand a inner join tb_category_brand b on a.id = b.brand_id where b.category_id = #{cid}")
    List<Brand> selectBrandsByCid(Long cid);
}
