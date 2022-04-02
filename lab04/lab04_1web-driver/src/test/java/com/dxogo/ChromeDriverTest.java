package com.dxogo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;

class ChromeDriverTest {

	//static final Logger log = getLogger(lookup().lookupClass());

	private WebDriver driver;

	@BeforeAll
	static void setupClass() {
		//WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	void setup() {
		driver = new ChromeDriver();
	}

	@Test
	void test() {
		// Exercise
		String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
		driver.get(sutUrl);
		String title = driver.getTitle();
		//log.debug("The title of {} is {}", sutUrl, title);

		// Verify
		assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java");
	}

	@AfterEach
	void teardown() {
		driver.quit();
	}
}
