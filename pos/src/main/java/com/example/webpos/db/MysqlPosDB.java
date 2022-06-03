package com.example.webpos.db;

import com.example.webpos.model.Cart;
import com.example.webpos.model.Product;
import com.example.webpos.repository.ProductRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.data.domain.Sort;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

// @Component
@Repository
public class MysqlPosDB implements PosDB {

    @Autowired
    ProductRepository productRepository;

    // public MysqlPosDB(ProductRepository repository) {
    //     this.productRepository = repository;
    // }

    private List<Product> products = null;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
        // if (productRepository == null) {
        //     System.out.println("%%%%%%%%%%%%%%%%%%%%%% IS NULL %%%%%%%%%%%%%%%%%%%%%%%%%");
        //     return null;
        // }
        // System.out.println("%%%%%%%%%%%%%%%%%%%%%%" + productRepository.findAll().toString());
        // return productRepository.findAll();
        // List<Element> products = new ArrayList<>();
        // productsIter.forEachRemaining(products::add);
        // return products;
        // return productRepository.findAll(PageRequest.of(1, 10)).getContent();
    }

    @Override
    public Product getProduct(String productId) {
        for (Product p : getProducts()) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }
}
