package com.itvaib.multidatasource.users.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "user", schema = "public")
public class User {
    @Id
    private Integer id;
    private String name;
}
