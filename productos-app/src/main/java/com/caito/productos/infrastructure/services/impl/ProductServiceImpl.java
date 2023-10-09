package com.caito.productos.infrastructure.services.impl;

import com.caito.productos.api.exceptions.customs.BadRequestException;
import com.caito.productos.api.exceptions.customs.NotFoundException;
import com.caito.productos.api.models.request.ProductRequest;
import com.caito.productos.api.models.response.ProductResponse;
import com.caito.productos.domain.repositories.ProductRepository;
import com.caito.productos.infrastructure.services.contracts.ProductService;
import com.caito.productos.util.constants.ProductConst;
import com.caito.productos.util.maps.ProductMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author claudio.vilas
 * date 09/2023
 */

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductResponse create(ProductRequest request) {
        log.info("---> inicio servicio crear productos");
        log.info("---> validando entradas...");
        this.validateProduct(request);
        log.info("---> guardando producto...");
        var procuct = productRepository.save(ProductMap.mapToEntity(request));
        log.info("---> finalizado servicio crear producto");
        return ProductMap.mapToDto(procuct);
    }

    @Override
    public ProductResponse getById(Long id) {
        log.info("---> inicio servicio consultar productos por id");
        log.info("---> buscando producto con id: {}...", id);
        var producto = productRepository.findById(id).orElseThrow(()->{
            log.error("ERROR: ".concat(ProductConst.P_ID_NOT_FOUND).concat(id.toString()));
            return new NotFoundException(ProductConst.P_ID_NOT_FOUND.concat(id.toString()));
        });
        log.info("--- finalizado servicio buscar producto por id");
        return ProductMap.mapToDto(producto);
    }

    @Override
    public List<ProductResponse> getAll() {
        log.info("---> inicio servicio buscar productos");
        log.info("---> buscando productos...");
        var products = productRepository.findAll();
        log.info("---> finalizado servicio buscar productos");
        return products.stream().map(ProductMap::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.info("---> inicio servicio eliminar producto por id");
        log.info("---> buscando producto con id: {}...", id);
        productRepository.findById(id).orElseThrow(()->{
            log.error("ERROR: ".concat(ProductConst.P_ID_NOT_FOUND).concat(id.toString()));
            return new NotFoundException(ProductConst.P_ID_NOT_FOUND.concat(id.toString()));
        });
        productRepository.deleteById(id);
        log.info("---> finalizado servicio eliminar producto por id");
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        log.info("---> inicio servicio actualizar producto");
        log.info("---> buscano producto con id: {}...", id);
        var producto = productRepository.findById(id).orElseThrow(()->{
            log.error("ERROR: ".concat(ProductConst.P_ID_NOT_FOUND).concat(id.toString()));
            return new NotFoundException(ProductConst.P_ID_NOT_FOUND.concat(id.toString()));
        });
        if (!request.getName().isBlank())
            producto.setName(request.getName());
        if (request.getPrice() != null){
            if (request.getPrice() < 0){
                log.error("ERROR: ".concat(ProductConst.P_PRICE_NEGATIVE));
                throw new BadRequestException(ProductConst.P_PRICE_NEGATIVE);
            }else {
                producto.setPrice(request.getPrice());
            }
        }
        log.info("---> guardando actualizacion de producto...");
        productRepository.save(producto);
        return ProductMap.mapToDto(producto);
    }

    private void validateProduct(ProductRequest request){

        if (request.getName().isBlank()){
            log.error("ERROR: ".concat(ProductConst.P_NAME_EMPTY));
            throw new BadRequestException(ProductConst.P_NAME_EMPTY);
        }

        if (request.getPrice() < 0){
            log.error("ERROR: ".concat(ProductConst.P_PRICE_NEGATIVE));
            throw new BadRequestException(ProductConst.P_PRICE_NEGATIVE);
        }
    }
}
