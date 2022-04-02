package com.dxogo;

//import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;


public class InstallDriversTest {

    @Test
    public void chromeSession() {
       // WebDriverManager.chromedriver().setup();

        System.setProperty("webdriver.chrome.driver","/home/dxogo/Downloads/chromedriver");
        ChromeDriver driver = new ChromeDriver();

        driver.quit();
    }
}