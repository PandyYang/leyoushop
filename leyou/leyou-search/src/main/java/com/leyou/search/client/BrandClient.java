package com.leyou.search.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Pandy
 * @Version 1.0
 */
@FeignClient(value = "item-service")
public interface BrandClient extends BrandApi {
}
