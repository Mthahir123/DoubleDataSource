package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.customer.model.Customer;
import com.example.demo.customer.repository.CustomerRepository;
import com.example.demo.product.model.Product;
import com.example.demo.product.repository.ProductRepository;

@SpringBootTest
class DoubleDataSourceApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	@Transactional("productTransactionManager")
	public void create_check_product() {
		Product product = new Product("228781", "Running Shoes", 20.0);
		product = productRepository.save(product);
		assertNotNull(productRepository.findById(product.getId()));
	}

	@Test
	@Transactional("customerTransactionManager")
	public void create_check_customer() {

		Customer customer = new Customer("user@wordpress-241348-2978695.cloudwaysapps.com", "Robert", "Hickle");
		customer = customerRepository.save(customer);

		assertNotNull(customerRepository.findById(customer.getId()));
		assertEquals(customerRepository.findById(customer.getId()).get().getEmail(),
				"user@wordpress-241348-2978695.cloudwaysapps.com");
	}

}
