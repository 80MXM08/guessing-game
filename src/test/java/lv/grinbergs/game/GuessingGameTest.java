package lv.grinbergs.game;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GuessingGameTest {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeEach
	public void setup() {
		WebDriverManager.firefoxdriver().setup();
		driver = new FirefoxDriver();
		driver.get("http://localhost:" + port);
	}

	@Test
	public void testGameFlow() {
		WebElement startButton = driver.findElement(By.className("open-button"));
		startButton.click();

		WebElement nameInput = driver.findElement(By.name("name"));
		nameInput.sendKeys("TestPlayer");
		WebElement submitNameButton = driver.findElement(By.className("btn"));
		submitNameButton.click();

		for (int i = 0; i < 8; i++) {
			WebElement guessInput1 = driver.findElement(By.id("guess0"));
			WebElement guessInput2 = driver.findElement(By.id("guess1"));
			WebElement guessInput3 = driver.findElement(By.id("guess2"));
			WebElement guessInput4 = driver.findElement(By.id("guess3"));

			guessInput1.sendKeys("1");
			guessInput2.sendKeys("2");
			guessInput3.sendKeys("3");
			guessInput4.sendKeys("4");

			WebElement guessButton = driver.findElement(By.id("btn_guess"));
			guessButton.click();

			if (i < 7) {
				WebElement triesLeft = driver.findElement(By.id("triesLeft"));

				int triesLeftValue = Integer.parseInt(triesLeft.getAttribute("value"));
				assertEquals(7 - i, triesLeftValue);
			}
		}

		WebElement gameOverMessage = driver.findElement(By.id("gameOverMessage"));
		assertTrue(gameOverMessage.getText().contains("You win") || gameOverMessage.getText().contains("You lose"));
	}

	@Test
	public void testWin() throws IOException {
		WebElement startButton = driver.findElement(By.className("open-button"));
		startButton.click();

		WebElement nameInput = driver.findElement(By.name("name"));
		nameInput.sendKeys("TestPlayer");
		WebElement submitNameButton = driver.findElement(By.className("btn"));
		submitNameButton.click();

		WebElement guessInput1 = driver.findElement(By.id("guess0"));
		WebElement guessInput2 = driver.findElement(By.id("guess1"));
		WebElement guessInput3 = driver.findElement(By.id("guess2"));
		WebElement guessInput4 = driver.findElement(By.id("guess3"));

		WebElement gameIdInput = driver.findElement(By.name("gameId"));
		Long gameId = Long.parseLong(gameIdInput.getAttribute("value"));

		String secretGuess = retrieveSecretNumber(gameId);

		guessInput1.sendKeys(String.valueOf(secretGuess.charAt(0)));
		guessInput2.sendKeys(String.valueOf(secretGuess.charAt(1)));
		guessInput3.sendKeys(String.valueOf(secretGuess.charAt(2)));
		guessInput4.sendKeys(String.valueOf(secretGuess.charAt(3)));

		WebElement guessButton = driver.findElement(By.id("btn_guess"));
		guessButton.click();

		WebElement gameOverMessage = driver.findElement(By.id("gameOverMessage"));
		assertTrue(gameOverMessage.getText().contains("You win"));
	}

	@AfterEach
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	private String retrieveSecretNumber(Long gameId) throws IOException {
		String secretNumber;
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpGet request = new HttpGet("http://localhost:" + port + "/secret-number?gameId=" + gameId);
			try (CloseableHttpResponse response = httpClient.execute(request)) {
				secretNumber = EntityUtils.toString(response.getEntity()).trim();
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		return secretNumber;
	}
}
