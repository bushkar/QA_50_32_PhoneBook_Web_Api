package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.WDListener;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppManager {
    public final static Logger logger =
            LoggerFactory.getLogger(AppManager.class);
    static String browser = System.getProperty("browser", "chrome");
    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        logger.info("Start testing " + LocalDate.now() + " : " + LocalTime.now());
//        driver = new ChromeDriver();
        switch (browser.toLowerCase()) {
            case "firefox":
                driver = new FirefoxDriver();
                System.out.println("Use FireFox");
                break;
            case "edge":
                driver = new EdgeDriver();
                System.out.println("Use Edge");
                break;
            case "chrome":
                driver = new ChromeDriver();
                System.out.println("Use Chrome");
                break;
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverListener webDriverListener = new WDListener();
        driver = new EventFiringDecorator<>(webDriverListener).decorate(driver);
    }

    //    @AfterMethod(enabled = false)
    @AfterMethod(enabled = true, alwaysRun = true)
    public void tearDown() {
        logger.info("Stop testing " + LocalDate.now() + " : " + LocalTime.now());
        if (driver != null)
            driver.quit();
    }
}
