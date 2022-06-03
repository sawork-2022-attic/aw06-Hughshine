package com.example.webpos.web;

import com.example.webpos.biz.PosService;
// import com.example.webpos.db.MysqlPosDB;
import com.example.webpos.model.Cart;
import com.example.webpos.model.Item;
import com.example.webpos.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class PosController {

    @Autowired
    private HttpSession session;

    private PosService posService;

    private Cart getCart() {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            saveCart(cart);
        }
        return cart;
    }

    private void saveCart(Cart cart) {
        session.setAttribute("cart", cart);
    }

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @GetMapping("/")
    public String pos(Model model) {
        // MysqlPosDB repository = new MysqlPosDB();
        // System.out.println(repository.getProducts());
        Cart cart = getCart();
        double discount = posService.discount();
        double tax = posService.tax();
        double total = posService.total(cart);
        model.addAttribute("products",
                posService.products()
        );
        // System.out.println("NULL???: " + cart == null);
        model.addAttribute("cart", cart);
        model.addAttribute("tax", "" + tax*100 + "%");
        model.addAttribute("discount", "" + discount*100 + "%");
        model.addAttribute("subtotal", total);
        model.addAttribute("total", total * (1 - discount) * (1 + tax));
        return "index";
    }

    @GetMapping("/add/{id}")
    public String addByGet(@PathVariable(name = "id") String id, Model model) {
        Cart cart = getCart();
        posService.add(cart, id, 1);
        saveCart(cart);
        return "redirect:/";
    }

    @GetMapping("/sub/{id}")
    public String sub(@PathVariable("id") String id, Model model) {
        Cart cart = getCart();
        posService.deleteOne(cart, id);
        saveCart(cart);
        return "redirect:/";
    }
    @GetMapping("/removeAll/{id}")
    public String removeAll(@PathVariable("id") String id, Model model) {
        Cart cart = getCart();
        posService.deleteAll(cart, id);
        saveCart(cart);
        return "redirect:/";
    }

    @GetMapping("/clearAll/")
    public String clearAll(Model model) {
        Cart cart = getCart();
        posService.clearCart(cart);
        saveCart(cart);
        return "redirect:/";
    }

    @GetMapping("/charge/")
    public String charge(Model model) {
        Cart cart = getCart();
        posService.checkout(cart);
        saveCart(cart);
        return "redirect:/";
    }
}
