package com.itvaib.multidatasource.product.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product", schema = "public")
@Data
public class Product {
    @Id
    private Integer id;
    private String name;
}
