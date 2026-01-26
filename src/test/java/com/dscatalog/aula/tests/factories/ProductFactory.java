package com.dscatalog.aula.tests.factories;

import java.time.Instant;

import com.dscatalog.aula.dto.ProductDTO;
import com.dscatalog.aula.entities.Category;
import com.dscatalog.aula.entities.Product;

public class ProductFactory {

	
	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", Instant.parse("2026-01-11T03:00:00Z"));
		product.getCategories().add(CategoryFactory.createCategory());
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product prod = createProduct();
		return new ProductDTO(prod, prod.getCategories());
	}
}
