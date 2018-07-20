package net.honeyflower.lecturing.springbootmongodb.services;

import java.util.List;

import net.honeyflower.lecturing.springbootmongodb.commands.ProductForm;
import net.honeyflower.lecturing.springbootmongodb.domain.Product;

/**
 * Created by jt on 1/10/17.
 */
public interface ProductService {

    List<Product> listAll();

    Product getById(String id);

    Product saveOrUpdate(Product product);

    void delete(String id);

    Product saveOrUpdateProductForm(ProductForm productForm);
}
