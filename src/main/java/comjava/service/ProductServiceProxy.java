package comjava.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import comjava.dto.ProductDTO;

@FeignClient(name = "product-service")
// @RibbonClient(name = "product-service")
public interface ProductServiceProxy {

    @GetMapping("/products/{productId}")
    public ProductDTO getProductDetail(@PathVariable("productId") Long productId);
}
