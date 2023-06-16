package com.example.Scraper.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "stocks")
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //primary key
    private String name;
    private String symbol;
    private String price;
}
