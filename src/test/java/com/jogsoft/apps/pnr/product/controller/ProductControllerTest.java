/**
 * 
 */
package com.jogsoft.apps.pnr.product.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jogsoft.apps.pnr.product.service.ProductService;

/**
 * @author Julius Oduro
 *
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

	protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	protected void setConverters(HttpMessageConverter<?>[] converters) {
		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny()
				.orElse(null);
		/*
		 * assertNotNull("the JSON message converter must not be null",
		 * this.mappingJackson2HttpMessageConverter);
		 */
	}

	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
	
	
	@Test
	public void findProducts_returnPage1() throws Exception{
		this.mockMvc.perform(MockMvcRequestBuilders.get("/products"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.number", is(0))) // 0 == page 1
			.andExpect(jsonPath("$.content", Matchers.hasSize(ProductService.PAGESIZE)));// page size
	}

	
	@Test
	public void findProduct() throws Exception{
		int productId = 101;
		String uri = "/products/" + productId;
		this.mockMvc.perform(MockMvcRequestBuilders.get(uri))
			.andExpect(status().isOk())
			.andExpect(content().contentType(this.contentType))
			.andExpect(jsonPath("$.id", is(productId)));
	}
	
	
	@Test
	public void findProduct_thatDoesNotExist() throws Exception{
		int productId = 1;
		String uri = "/products/" + productId;
		this.mockMvc.perform(MockMvcRequestBuilders.get(uri))
			.andExpect(status().isNotFound());
	}
}
