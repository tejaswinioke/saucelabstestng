package test.java.com.testing;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
 
public class SampleSauceTest {
 
  public static final String USERNAME = "tejaoke";
  public static final String ACCESS_KEY = "f59fd23d-dae7-492d-8bfa-b6a1763c3ebe";
  public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
 
  public static void main(String[] args) throws Exception {
 
    DesiredCapabilities caps = DesiredCapabilities.chrome();
//    caps.setCapability("platform", "Windows XP");
//    caps.setCapability("version", "43.0");
// 
    WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
 
    /**
     * Goes to Sauce Lab's guinea-pig page and prints title
     */
 
    driver.get("https://saucelabs.com/test/guinea-pig");
    System.out.println("title of page is: " + driver.getTitle());
 
    driver.quit();
  }
}