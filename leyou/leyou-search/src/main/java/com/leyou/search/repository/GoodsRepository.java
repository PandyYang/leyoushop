package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author: Pandy
 * @Version 1.0
 */
public interface GoodsRepository extends
        ElasticsearchRepository<Goods,Long> {
}
