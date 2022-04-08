package com.dxogo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

//import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.junit.jupiter.api.Assertions.*;

class BlazeDemoTest {

    WebDriver driver;

    @BeforeEach
	void setup() {
        System.setProperty("webdriver.chrome.driver","/home/dxogo/Downloads/chromedriver"); 
        driver = new ChromeDriver(); }

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

        // check that new html element exists (new page)
        driver.findElement(By.cssSelector("html")).click();
        
        // check page title
        assertEquals(driver.findElement(By.cssSelector("h3")).getText(), "Flights from Paris to Rome:");
        
        // select firts flight from table
        driver.findElement(By.cssSelector("tr:nth-child(1) .btn")).click();  

        // fill out form

        driver.findElement(By.id("inputName")).click();
        driver.findElement(By.id("inputName")).sendKeys("Dx");

        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys("Campo da Floribela");
        
        driver.findElement(By.id("city")).click();
        driver.findElement(By.id("city")).sendKeys("Aveiro");

        driver.findElement(By.id("state")).click();
        driver.findElement(By.id("state")).sendKeys("Aveiro");
        
        driver.findElement(By.id("zipCode")).click();
        driver.findElement(By.id("zipCode")).sendKeys("3810-255");

        driver.findElement(By.id("cardType")).click();
        driver.findElement(By.id("cardType")).getCssValue("amex");
        
        driver.findElement(By.id("creditCardNumber")).click();
        driver.findElement(By.id("creditCardNumber")).sendKeys("1234 9034 5678 6789");

        driver.findElement(By.id("cardType")).click();
        driver.findElement(By.id("cardType")).getCssValue("amex");

        driver.findElement(By.id("creditCardMonth")).click();
        driver.findElement(By.id("creditCardMonth")).sendKeys("09");

        driver.findElement(By.id("creditCardYear")).click();
        driver.findElement(By.id("creditCardYear")).sendKeys("2026");
        
        driver.findElement(By.id("nameOnCard")).click();
        driver.findElement(By.id("nameOnCard")).sendKeys("Dx");

        // buy ticket
        driver.findElement(By.cssSelector(".btn-primary")).click();
        driver.findElement(By.cssSelector(".hero-unit")).click();
        
        // purchase verification page
        assertEquals(driver.findElement(By.cssSelector("h1")).getText(), "Thank you for your purchase today!");

        // new step
        assertEquals(driver.getTitle(), "BlazeDemo Confirmation");
    }
}