package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name = "tb_spec_group")
@Data
public class SpecGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cid;

    private String name;

    //表中没有字段 忽略该字段
    @Transient
    private List<SpecParam> params;

   // getter和setter省略
}