package com.dxogo;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    
    private List<Stock> stocks = new ArrayList<Stock>();
    private IStocketMarketService stockmarket;

    public StocksPortfolio(IStocketMarketService stockmarket){
        this.stockmarket = stockmarket;
    }

    public void addStock(Stock stock){ this.stocks.add(stock); } 

    public double getTotalValue(){ 
        
        double totalPrice = 0;

        for (Stock s: this.stocks) {
            totalPrice += (stockmarket.lookUpPrice(s.getLabel()) * s.getQuantity());
        }

        return totalPrice;
    }

    public void clear() { this.stocks.clear(); }
}
