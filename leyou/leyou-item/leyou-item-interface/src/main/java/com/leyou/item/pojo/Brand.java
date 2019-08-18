package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Pandy
 * @Version 1.0
 */
@Table(name = "tb_brand")
@Data
public class Brand {

    //图片的id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //图片的名称
    private String name;
    //图片的地址
    private String image;
    //品牌的首字母
    private Character letter;
}
