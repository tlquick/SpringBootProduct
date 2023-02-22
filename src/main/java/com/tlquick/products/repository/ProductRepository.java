package com.tlquick.products.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tlquick.products.model.Product;

@Repository // all subclasses access database
	public interface ProductRepository extends JpaRepository<Product, Integer> {
		List<Product> findByNameContaining(String name); //same as @Query("SELECT p FROM Product p WHERE p.name =?1" in JPQL
}
