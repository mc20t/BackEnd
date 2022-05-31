package org.example.Lesha_REST.repository;

import org.example.Lesha_REST.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> getProductById(long id);
    List<Product> findAll();
}
