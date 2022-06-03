package com.example.webpos.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.webpos.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
public class ProductRepository {  // don't know why, CrudRepo not work, has to parse the result normally.

    String url = "jdbc:mysql://localhost:3306/aw06";
    String user = "root";
    String password = "hugh";
    String table = "cards";
    private Connection connection;

    public List<Product> findAll() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM cards LIMIT 20;");
            List<Product> products = new ArrayList<Product>();
            while (rs.next()) {
                try {
                products.add(new Product(rs.getString("asin"), rs.getString("title"), rs.getString("price"),
                Double.parseDouble(rs.getString("price").substring(1)),
                        rs.getString("imageURLHighRes")));
                } catch (java.lang.NumberFormatException e) {
                    continue;
                }
            }
            connection.close();
            return products;
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
    }
}
