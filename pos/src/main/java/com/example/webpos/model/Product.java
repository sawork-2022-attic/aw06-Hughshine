package com.example.webpos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cards")
public class Product implements Serializable {
    @Id
    @Column(name = "asin")
    private String id;

    @Column(name = "title")
    private String name;

    @Column(name = "price")
    private String _price;

    private double price;

    @Column(name = "imageURLHighRes")
    private String image;
}
