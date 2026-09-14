package org.vaya.creatorstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaya.creatorstore.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
