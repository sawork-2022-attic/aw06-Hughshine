package com.example.webpos.biz;

import com.example.webpos.model.Cart;
import com.example.webpos.model.Product;

import java.util.List;

public interface PosService {

    public Cart add(Cart cart, Product product, int amount);

    public Cart add(Cart cart, String productId, int amount);

    public List<Product> products();

    public Product randomProduct();

    public Cart deleteOne(Cart cart, String productId);

    public Cart deleteAll(Cart cart, String productId);

    public Cart clearCart(Cart cart);

    public double tax();

    public double discount();

    public double checkout(Cart cart);

    public double total(Cart cart);

}
