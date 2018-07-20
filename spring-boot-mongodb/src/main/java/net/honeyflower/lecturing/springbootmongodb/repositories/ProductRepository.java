package net.honeyflower.lecturing.springbootmongodb.repositories;

import org.springframework.data.repository.CrudRepository;

import net.honeyflower.lecturing.springbootmongodb.domain.Product;

/**
 * Created by jt on 1/10/17.
 */
public interface ProductRepository extends CrudRepository<Product, String> {
}
