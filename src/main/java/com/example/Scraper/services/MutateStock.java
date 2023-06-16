package com.example.Scraper.services;

import com.example.Scraper.model.Stock;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

public class MutateStock {
    public static HtmlPage getDocument(String url) {
        HtmlPage page = null;
        try (final WebClient webClient = new WebClient()) {
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setPrintContentOnFailingStatusCode(false);
            webClient.getOptions().setJavaScriptEnabled(false);

            page = webClient.getPage(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return page;
    }

    public static Stock populateCompany(String name, String symbol, String price) {
        Stock stock = new Stock(name, symbol, price);

        System.out.println("TEST : ");
        System.out.println("Company Name: " + stock.getName());
        System.out.println("Company Price: " + stock.getPrice());
        System.out.println("Company Symbol: " + stock.getSymbol());
        return stock;
    }
}
