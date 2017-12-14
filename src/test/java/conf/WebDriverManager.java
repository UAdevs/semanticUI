package conf;

/**
 * Created by Dev on 14.12.2017.
 */
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;


public class WebDriverManager {
    private WebDriver driver;
    public WebDriver getWebDriver(SupportedBrowsers browser) {
        switch (browser){
            case CHROME:
                driver = new ChromeDriver();
                break;
            case FF:
                driver = new FirefoxDriver();
                break;
            case EDGE:
                driver = new EdgeDriver();
                break;
            default:
                driver = new ChromeDriver();
                break;
        }
        return driver;
    }
}
