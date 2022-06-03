package com.example.batch.service;

import com.example.batch.model.Product;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductWriter implements ItemWriter<Product>, StepExecutionListener {
    
    String url = "jdbc:mysql://localhost:3306/aw06?createDatabaseIfNotExist=true";
    String user = "root";
    String password = "hugh";
    String table = "cards";
    private Connection connection;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        try {
            connection = DriverManager.getConnection(url, user, password);
            Statement stmt = connection.createStatement();
            stmt.execute(
                String.format(
                    "CREATE TABLE IF NOT EXISTS %s(main_cat varchar(40), title varchar(100), asin char(10) not null primary key, category varchar(40), price varchar(40), imageURLHighRes varchar(120))default charset=utf8;", table)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void write(List<? extends Product> list) throws Exception {
        Statement st = connection.createStatement();
        try {
            for (Product product : list) {
                String main_cat = product.getMain_cat();
                String price = product.getPrice();
                String title = product.getTitle();
                String asin = product.getAsin();
                List<String> categories = product.getCategory();
                List<String> urls = product.getImageURLHighRes();
                if (price == null || price == "" || url == null || urls.isEmpty()
                    || categories == null || categories.isEmpty()) {
                    continue;
                }
                String category = categories.get(0);
                String url = urls.get(0);
                try {
                    st.execute(String.format("INSERT INTO %s(main_cat, title, asin, category, price, imageURLHighRes) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", table, main_cat, title, asin, category, price, url));    
                }  catch (SQLIntegrityConstraintViolationException e) {
                    continue;
                }         
            }
        } catch (SQLException e) {
            // e.printStackTrace();
        } finally {
            st.close();
        }
 }
}
