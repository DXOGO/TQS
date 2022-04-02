package com.dxogo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

//import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SeleniumJupiter.class)
class BlazeDemoTest {

    WebDriver driver;

    @BeforeEach
	void setup() { driver = new ChromeDriver(); }

    @AfterEach
    void tearDown() { driver.close(); }

    @Test
    public void websiteTest() {
        // open site
        driver.get("https://blazedemo.com/");
        driver.manage().window().setSize(new Dimension(550, 692));
        
        // hq element
        driver.findElement(By.cssSelector("h1")).click();
        assertEquals(driver.findElement(By.cssSelector("h1")).getText(), "Welcome to the Simple Travel Agency!");
        
        // p element
        driver.findElement(By.cssSelector("p")).click();
        assertEquals(driver.findElement(By.cssSelector("p")).getText(), "The is a sample site you can test with BlazeMeter!");

        // fromPort
        {
            WebElement dropdown = driver.findElement(By.name("fromPort"));
            dropdown.findElement(By.xpath("//option[. = 'Paris']")).click();
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(1)")).click();
        
        // toPort
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = 'Rome']")).click();
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(4) > option:nth-child(2)")).click();

        // button to 'find flights'
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }
}