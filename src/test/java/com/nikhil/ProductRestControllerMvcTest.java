package com.nikhil;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.nikhil.entities.Product;
import com.nikhil.repository.ProductRepository;

@WebMvcTest
class ProductRestControllerMvcTest {
	
	private static final String PRODUCTS_URL = "/productrestapi/products/";

	private static final String CONTEXT_PATH = "/productrestapi";

	private static final int PRODUCT_PRICE = 10000;

	private static final String PRODUCT_DESCRIPTION = "Awesome";

	private static final String PRODUCT_NAME = "MacBook";

	private static final int PRODUCT_ID = 1;

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductRepository repository;
	
	@Test
	void testFindAll() throws Exception {
		Product product = buildProduct();
		
		List<Product> products = Arrays.asList(product);
		when(repository.findAll()).thenReturn(products);
		
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc.perform(get(PRODUCTS_URL).contextPath(CONTEXT_PATH))
			.andExpect(status().isOk())
			.andExpect((ResultMatcher) content().json(objectWriter.writeValueAsString(products)));
	}

	@Test
	void testCreateProduct() throws JsonProcessingException, Exception {
		Product product = buildProduct();
		when(repository.save(any())).thenReturn(product);
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc.perform(post(PRODUCTS_URL).contextPath(CONTEXT_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(objectWriter.writeValueAsString(product)))
				.andExpect(status().isOk())
				.andExpect((ResultMatcher) content().json(objectWriter.writeValueAsString(product)));
	}
	
	@Test
	void testUpdateProduct() throws JsonProcessingException, Exception {
		Product product = buildProduct();
		when(repository.save(any())).thenReturn(product);
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		
		mockMvc.perform(put(PRODUCTS_URL).contextPath(CONTEXT_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(objectWriter.writeValueAsString(product)))
				.andExpect(status().isOk())
				.andExpect((ResultMatcher) content().json(objectWriter.writeValueAsString(product)));
	}
	
	@Test
	void testDeleteProduct() throws Exception {
		doNothing().when(repository).deleteById(PRODUCT_ID);
		mockMvc.perform(delete(PRODUCTS_URL+PRODUCT_ID).contextPath(CONTEXT_PATH)).andExpect(status().isOk());
	}
	
	private Product buildProduct() {
		Product product = new Product();
		product.setId(PRODUCT_ID);
		product.setName(PRODUCT_NAME);
		product.setDescription(PRODUCT_DESCRIPTION);
		product.setPrice(PRODUCT_PRICE);
		return product;
	}

}
