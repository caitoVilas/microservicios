package com.caito.productos.infrastructure.services.contracts;

import com.caito.productos.api.models.request.ProductRequest;
import com.caito.productos.api.models.response.ProductResponse;

import java.util.List;

/**
 * @author claudio.vilas
 * date 09/2023
 */

public interface ProductService {
    ProductResponse create(ProductRequest request);
    ProductResponse getById(Long id);
    List<ProductResponse> getAll();
    void delete(Long id);
    ProductResponse update(Long id, ProductRequest request);
}
