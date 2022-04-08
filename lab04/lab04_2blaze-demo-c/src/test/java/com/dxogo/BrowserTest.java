package com.dxogo;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

@ExtendWith(SeleniumJupiter.class)
class BrowserTest {

    ChromeDriver driver;

    // Dependency Injection of browser in Constructor
    BrowserTest(ChromeDriver driver) { this.driver = driver; }

    @Test
    void testGlobalChrome() {
        driver.get("https://bonigarcia.github.io/selenium-jupiter/");
        assertThat(driver.getTitle(), containsString("Selenium-Jupiter"));
    }

    // Dependency Injection of browser in parameters
    @Test
    void testWithChrome(ChromeDriver chromeDriver) {
        chromeDriver.get("https://www.ua.pt");
        assertThat(chromeDriver.getTitle(), containsString("Universidade de Aveiro"));
    }

    @Test
    void testWithFirefox(FirefoxDriver firefoxDriver) {
        firefoxDriver.get("https://www.google.com/");
        assertThat(firefoxDriver.getTitle(), is("Google"));
    }

    @Test
    void testWithChromeAndFirefox(ChromeDriver chromeDriver, FirefoxDriver firefoxDriver) {
        chromeDriver.get("https://www.youtube.com/");
        assertThat(chromeDriver.getTitle(), is("YouTube"));

        firefoxDriver.get("https://www.youtube.com/");
        assertThat(firefoxDriver.getTitle(), is("YouTube"));

    }

    @Test
    void testWithSafari(SafariDriver safariDriver) {
        safariDriver.get("https://www.apple.com/");
        assertThat(safariDriver.getTitle(), is("Apple"));
    }

}