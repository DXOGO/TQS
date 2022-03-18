package com.dxogo;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
// import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {
    
    /* 
        makes the test code and verification error easier to read as 
        parameter names (field names) are used to identify the mocks.
    */
    @Mock
    IStocketMarketService stockmarket;

    /* 
        allows you to mark a field on which an injection is to be performed.
    */
    @InjectMocks
    StocksPortfolio portfolio;

    /* 
        @Mock - creates mocks
        @InjectMocks - creates class objects. 
        Use @InjectMocks to create class instances which needs to be tested in test class 
    */

    @AfterEach
    public void clear() {
        portfolio.clear();
    }

    @Test
    void getTotalValueTest(){
        
        portfolio.addStock(new Stock("Apple", 160));
        portfolio.addStock(new Stock("Ford", 18));
        portfolio.addStock(new Stock("Tesla", 871));

        // return quantity
        when(stockmarket.lookUpPrice("Apple")).thenReturn(2.0);
        when(stockmarket.lookUpPrice("Ford")).thenReturn(3.0);
        when(stockmarket.lookUpPrice("Tesla")).thenReturn(4.0);

        // totalPrice = price_per_stock * stock_quantity
        double t = 160 * 2 + 18 * 3 + 871 * 4;

        // using junit -> 
        // assertEquals(t, portfolio.getTotalValue())

        // using hamcrest ->
        assertThat(portfolio.getTotalValue(), is(t));

        verify(stockmarket, times(3)).lookUpPrice(anyString());
    }
}

