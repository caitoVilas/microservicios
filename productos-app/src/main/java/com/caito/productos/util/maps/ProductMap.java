package com.caito.productos.util.maps;

import com.caito.productos.api.models.request.ProductRequest;
import com.caito.productos.api.models.response.ProductResponse;
import com.caito.productos.domain.emtities.Product;

/**
 * @author claudio.vilas
 * date 09/2023
 */
public class ProductMap {

    public static Product mapToEntity(ProductRequest request){
        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }

    public static ProductResponse mapToDto(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .createAt(product.getCreateAt())
                .build();
    }
}
