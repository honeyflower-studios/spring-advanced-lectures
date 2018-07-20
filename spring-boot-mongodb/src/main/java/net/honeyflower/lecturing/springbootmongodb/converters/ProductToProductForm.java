package net.honeyflower.lecturing.springbootmongodb.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import net.honeyflower.lecturing.springbootmongodb.commands.ProductForm;
import net.honeyflower.lecturing.springbootmongodb.domain.Product;

/**
 * Created by jt on 1/10/17.
 */
@Component
public class ProductToProductForm implements Converter<Product, ProductForm> {
    @Override
    public ProductForm convert(Product product) {
        ProductForm productForm = new ProductForm();
        productForm.setId(product.getId().toHexString());
        productForm.setDescription(product.getDescription());
        productForm.setPrice(product.getPrice());
        productForm.setImageUrl(product.getImageUrl());
        return productForm;
    }
}
