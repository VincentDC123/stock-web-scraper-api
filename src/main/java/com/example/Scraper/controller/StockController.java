package com.example.Scraper.controller;

import com.example.Scraper.model.Stock;
import com.example.Scraper.services.StockService;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.Scraper.services.MutateStock.getDocument;
import static com.example.Scraper.services.MutateStock.populateCompany;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/stocks")
    public Stock createStock(@RequestBody Stock stock) {
        String url = "https://finance.yahoo.com/quote/" + stock.getSymbol() + "/";
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        HtmlPage page = getDocument(url);

        // Gets the name of the company and its stock symbol
        DomElement company = page.getFirstByXPath("//div[@class=\"D(ib) \"]/h1");
        String name = "";
        String[] tempList = company.asNormalizedText().split("[()]");
        for(int i = 0; i < tempList.length-1; i++) {
            name += tempList[i] + " ";
        }
        String symbol = tempList[tempList.length-1];
        // Gets the price
        DomElement price = page.getFirstByXPath("//div[@class=\"D(ib) Mend(20px)\"]/fin-streamer");
        Stock temp = populateCompany(name, symbol, "$"+price.asNormalizedText());
        webClient.close();

        return stockService.createStock(temp);
    }

    @GetMapping("/stocks")
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @DeleteMapping("/stocks/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteStock(@PathVariable Long id) {
        boolean deleted = false;
        deleted = stockService.deleteStock(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stocks/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Stock stock = null;
        stock = stockService.getStockById(id);
        return ResponseEntity.ok(stock);
    }

    @PutMapping("/stocks/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock stock) {
        String url = "https://finance.yahoo.com/quote/" + stock.getSymbol() + "/";
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        HtmlPage page = getDocument(url);
        // Gets the price
        DomElement price = page.getFirstByXPath("//div[@class=\"D(ib) Mend(20px)\"]/fin-streamer");
        webClient.close();
        stock.setPrice(price.asNormalizedText());

        stock = stockService.updateStock(id, stock);
        return ResponseEntity.ok(stock);
    }
}
