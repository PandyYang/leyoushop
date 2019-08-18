package com.leyou.search.client;

import com.leyou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Pandy
 * @Version 1.0
 */
@FeignClient(value = "item-service")
public interface SpecificationClient extends SpecificationApi {
}
