package com.nikhil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.nikhil.entities.Product;

@SpringBootTest
class ProductrestapiApplicationTests {
	
	@Value("${productrestapi.services.url}")
	private String baseURL;
	
	@Test
	void testGetProduct() {
		System.out.println(baseURL);
		RestTemplate restTemplate = new RestTemplate();
		Product product = restTemplate.getForObject(baseURL + "1", Product.class);
		assertNotNull(product);
		assertEquals(1, product.getId());
		assertEquals("IPhone", product.getName());
	}

	@Test
	void createProductTest() {
		RestTemplate restTemplate = new RestTemplate();
		Product product = new Product();
		product.setName("Samsung");
		product.setDescription("X2");
		product.setPrice(50000);
		Product newProduct = restTemplate.postForObject(baseURL, product, Product.class);
		assertNotNull(newProduct);
		assertNotNull(newProduct.getId());
		assertEquals("Samsung", newProduct.getName());
	}
	
	@Test
	void updateProductTest() {
		RestTemplate restTemplate = new RestTemplate();
		Product product = restTemplate.getForObject(baseURL + "1", Product.class);
		product.setPrice(1500);
		restTemplate.put(baseURL, Product.class);
	}
}
