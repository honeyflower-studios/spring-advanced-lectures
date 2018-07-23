package net.honeyflower.lecturing.beanfactory;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import net.honeyflower.lecturing.beanfactory.bean.Customer;
import net.honeyflower.lecturing.beanfactory.bean.MyBeanPostProcessor;
import net.honeyflower.lecturing.beanfactory.service.CustomerService;

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
	
	@Bean(/*initMethod="customInitBean", destroyMethod="customDestroyBean"*/)
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
