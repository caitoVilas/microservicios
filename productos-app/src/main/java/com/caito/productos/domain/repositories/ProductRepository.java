package com.caito.productos.domain.repositories;

import com.caito.productos.domain.emtities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author claudio.vilas
 * date 09/2023
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
