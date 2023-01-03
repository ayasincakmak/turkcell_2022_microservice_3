package com.works.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    private Long accountID;
    private Long productID;

}
