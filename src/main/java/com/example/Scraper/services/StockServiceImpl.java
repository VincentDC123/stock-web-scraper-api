package com.example.Scraper.services;

import com.example.Scraper.entities.StockEntity;
import com.example.Scraper.model.Stock;
import com.example.Scraper.repository.StockRepository;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService{

    private StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Stock createStock(Stock stock) {
        StockEntity stockEntity = new StockEntity();

        BeanUtils.copyProperties(stock, stockEntity);
        stockRepository.save(stockEntity);

        return null;
    }

    @Override
    public List<Stock> getAllStocks() {
        List<StockEntity> stockEntities = stockRepository.findAll();

        List<Stock> stocks = stockEntities
                .stream()
                .map(s -> new Stock(
                        s.getId(),
                        s.getName(),
                        s.getSymbol(),
                        s.getPrice()))
                .collect(Collectors.toList());
        return stocks;
    }

    @Override
    public boolean deleteStock(Long id) {
        StockEntity stock = stockRepository.findById(id).get();
        stockRepository.delete(stock);
        return true;
    }

    @Override
    public Stock getStockById(Long id) {
        StockEntity stockEntity = stockRepository.findById(id).get();
        Stock stock = new Stock();
        BeanUtils.copyProperties(stockEntity, stock);
        return stock;
    }

    @Override
    public Stock updateStock(Long id, Stock stock) {
        StockEntity stockEntity = stockRepository.findById(id).get();

        stockEntity.setName(stock.getName());
        stockEntity.setSymbol(stock.getSymbol());
        stockEntity.setPrice(stock.getPrice());

        stockRepository.save(stockEntity);
        return stock;
    }
}
