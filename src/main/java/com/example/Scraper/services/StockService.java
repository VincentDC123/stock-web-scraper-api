package com.example.Scraper.services;

import com.example.Scraper.model.Stock;

import java.util.List;

public interface StockService {
    Stock createStock(Stock stock);

    List<Stock> getAllStocks();

    boolean deleteStock(Long id);

    Stock getStockById(Long id);

    Stock updateStock(Long id, Stock stock);
}
