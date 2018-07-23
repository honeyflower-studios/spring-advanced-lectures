package net.honeyflower.lecturing.beanfactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import net.honeyflower.lecturing.beanfactory.service.CustomerService;

@SpringBootApplication
public class SpringBeanPostProcessorsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBeanPostProcessorsApplication.class, args);
		

		System.out.println("Context has been initialized");
		CustomerService service = (CustomerService) context.getBean("customerService");
		System.out.println("Already retrieved Bean from context. Next, show Bean data.");
		System.out.println("Customer Name: " + service.getCustomer().getName());
		context.close();
	}
}
