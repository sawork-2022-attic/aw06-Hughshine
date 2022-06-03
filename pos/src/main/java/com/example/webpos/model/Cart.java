package com.example.webpos.model;

import lombok.Data;
import org.springframework.stereotype.Component;
// import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class Cart implements Serializable {

    private List<Item> items = new ArrayList<>();

//    public boolean addItem(Item item) {
//        return items.add(item);
//    }

    public double getTotal() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getQuantity() * items.get(i).getProduct().getPrice();
        }
        return total;
    }
    public boolean addItem(Item item) {
        for (int i = 0; i < items.size(); i++) {
            Item _item = items.get(i);
            if (_item.getProduct().getId().equals(item.getProduct().getId())) {
                _item.setQuantity(_item.getQuantity() + item.getQuantity());
                return true;
            }
        }
        return items.add(item);
    }
    public boolean deleteOneProduct(Product product) {
        for (int i = 0; i < items.size(); i++) {
            Item _item = items.get(i);
            if (_item.getProduct().getId().equals(product.getId())) {
                if (_item.getQuantity() == 1) {
                    items.remove(i);
                } else {
                    _item.setQuantity(_item.getQuantity()-1);
                }
                return true;
            }
        }
        return false;
    }

    public boolean deleteAllProduct(Product product) {
        for (int i = 0; i < items.size(); i++) {
            Item _item = items.get(i);
            if (_item.getProduct().getId().equals(product.getId())) {
                items.remove(i);
                return true;
            }
        }
        return false;
    }

    public double total() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getQuantity() * items.get(i).getProduct().getPrice();
        }
        return total;
    }
}
