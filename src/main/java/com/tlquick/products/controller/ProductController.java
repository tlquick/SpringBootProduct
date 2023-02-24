package com.tlquick.products.controller;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tlquick.products.model.Product;
import com.tlquick.products.repository.ProductRepository;


@RestController
@RequestMapping(path = "/")
public class ProductController {
	@Autowired
	ProductRepository repository;

	@GetMapping(path = "/")
	public String getBanner() {
		return "Welcome the products api; use /swagger-ui.html to see available endpoints and /actuator to monitor health. Endpoints are versioned eg v1/products.";
	}

	@GetMapping("/v1/products")
	public List<Product> getProducts() {

		List<Product> products = new ArrayList<>();
		repository.findAll().forEach(products::add);
		return products;
	}
	@GetMapping("/v1/products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
		Optional<Product> productData = repository.findById(id);

		if (productData.isPresent()) {
			return new ResponseEntity<>(productData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/v1/products")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		try {
			Product _product = repository
					.save(new Product(product.getName(), product.getDescription(), product.getPrice()));
			return new ResponseEntity<>(_product, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("/v1/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") int id, @RequestBody Product product) {
		Optional<Product> productData = repository.findById(id);
		if(productData == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else if (productData.isPresent()) {
			Product _product = productData.get();
			_product.setName(product.getName());
			_product.setDescription(product.getDescription());
			_product.setPrice(product.getPrice());
			return new ResponseEntity<>(repository.save(_product), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	@DeleteMapping("/v1/products/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") int id) {
		try {
			repository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
