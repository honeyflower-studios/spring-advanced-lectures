package com.javasampleapproach.beanpostprocessors;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.javasampleapproach.beanpostprocessors.bean.Customer;
import com.javasampleapproach.beanpostprocessors.bean.MyBeanPostProcessor;
import com.javasampleapproach.beanpostprocessors.service.CustomerService;

@Configuration
public class FrameworkConfiguration {
	
	@Bean
	public BeanPostProcessor myBeanPostProcessor() {
		return new MyBeanPostProcessor();
	}
	
	@Bean
	public CommonAnnotationBeanPostProcessor commonAnnotationBeanPostProcessor() {
		return new CommonAnnotationBeanPostProcessor();
	}
	
	@Bean
	@Lazy
	public CustomerService customerService() {
		CustomerService service = new CustomerService();
		service.setCustomer(customer());
		return service;
	}
	
	@Bean
	public Customer customer() {
		Customer customer =  new Customer();
		customer.setName("Jack");
		return customer;
	}

}
