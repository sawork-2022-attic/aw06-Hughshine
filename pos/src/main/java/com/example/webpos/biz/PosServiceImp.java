package com.example.webpos.biz;

import com.example.webpos.db.PosDB;
import com.example.webpos.model.Cart;
import com.example.webpos.model.Item;
import com.example.webpos.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class PosServiceImp implements PosService, Serializable {

    private PosDB posDB;

    @Autowired
    public void setPosDB(PosDB posDB) {
        this.posDB = posDB;
    }


    @Override
    public Product randomProduct() {
        return products().get(ThreadLocalRandom.current().nextInt(0, products().size()));
    }

    @Override
    public Cart add(Cart cart, Product product, int amount) {
        return add(cart, product.getId(), amount);
    }

    @Override
    public Cart add(Cart cart, String productId, int amount) {

        Product product = posDB.getProduct(productId);
        if (product == null) return cart;

        cart.addItem(new Item(product, amount));
        return cart;
    }

    @Override
    public List<Product> products() {
        return posDB.getProducts();
    }

    @Override
    public Cart deleteOne(Cart cart, String productId) {
        Product product = posDB.getProduct(productId);
        if (product == null) return cart;
        cart.deleteOneProduct(product);
        return cart;
    }

    @Override
    public Cart deleteAll(Cart cart, String productId) {
        Product product = posDB.getProduct(productId);
        if (product == null) return cart;
        cart.deleteAllProduct(product);
        return cart;
    }

    @Override
    public Cart clearCart(Cart cart) {
        if (cart != null) {
            cart.getItems().clear();
        }
        return cart;
    }

    @Override
    public double checkout(Cart cart) {
        if (cart == null) {
            return 0;
        }
        double total = cart.total();
        clearCart(cart);
        return total;
    }

    @Override
    public double total(Cart cart) {
        if (cart == null) {
            return 0;
        }
        return cart.total();
    }


    @Override
    public double discount() {
        return 0.05;
    }

    @Override
    public double tax() {
        return 0.05;
    }

}
