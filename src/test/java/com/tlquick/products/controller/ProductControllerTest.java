package com.tlquick.products.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlquick.products.model.Product;
import com.tlquick.products.repository.ProductRepository;
@WebMvcTest(ProductController.class)
class ProductControllerTest {

	@Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    
    @MockBean
    ProductRepository productRepository;
    
    Product RECORD_1 = new Product(1, "product1", "a very nice product 1", 1.00);
    Product RECORD_2 = new Product(2, "product2", "a very nice product 2", 2.00);
    Product RECORD_3 = new Product(3, "product3", "a very nice product 3", 3.00);
    
    @Test
    public void getProducts_success() throws Exception {
        List<Product> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
        
        Mockito.when(productRepository.findAll()).thenReturn(records);
        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("product3")));
    }
    @Test
    public void getProductById_success() throws Exception {
        Mockito.when(productRepository.findById(RECORD_1.getId())).thenReturn(java.util.Optional.of(RECORD_1));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("product1")));
    }
    @Test
    public void createProduct_success() throws Exception {
        Product record = Product.builder()
                .name("product4")
                .description("a nice product4")
                .price(4.00)
                .build();

        Mockito.when(productRepository.save(record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record));

        mockMvc.perform(mockRequest)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("product4")));
        }
		
		@Test
		public void updateProduct_success() throws Exception {
			Product updatedRecord = Product.builder()
					.id(1)
					.name("newproduct1")
					.description("new description")
					.price(1.00).build();

			Mockito.when(productRepository.findById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));
			Mockito.when(productRepository.save(updatedRecord)).thenReturn(updatedRecord);

			MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/v1/products/1")
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.content(this.mapper.writeValueAsString(updatedRecord));

			mockMvc.perform(mockRequest).andExpect(status().isOk())
					.andExpect(jsonPath("$", notNullValue()))
					.andExpect(jsonPath("$.name", is("newproduct1")));

		}
		 
		@Test
		public void updateProduct_nullId() throws Exception {
			Product updatedRecord = Product.builder().name("unicorn").description("non existent product").price(5.00)
					.build();

			MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/v1/products/1")
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.content(this.mapper.writeValueAsString(updatedRecord));

			mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
		}
	 
    @Test
    public void updateProduct_recordNotFound() throws Exception {
        Product updatedRecord = Product.builder()
                .id(11)
                .name("another unicorn")
                .description("another non existent product")
                .price(11.00)
                .build();

        Mockito.when(productRepository.findById(updatedRecord.getId())).thenReturn(null);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/v1/products/11")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }
    @Test
    public void deletePatientById_success() throws Exception {
        Mockito.when(productRepository.findById(RECORD_2.getId())).thenReturn(Optional.of(RECORD_2));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/products/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

	@Test
	public void deleteProduct_notFound() throws Exception {
		Mockito.when(productRepository.findById(6)).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.delete("/v1/products/6").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
	 
}

