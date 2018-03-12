package test.java.com.testing;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NewTest {
	
	 public static final String USERNAME = "tejaoke";
	  public static final String ACCESS_KEY = "f59fd23d-dae7-492d-8bfa-b6a1763c3ebe";
	  public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
	  private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	  private ThreadLocal<String> sessionId = new ThreadLocal<String>();
	
	  @DataProvider(name = "hardCodedBrowsers", parallel = true)
	    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
	        return new Object[][]{
	               // new Object[]{"MicrosoftEdge", "14.14393", "Windows 10"},
	                new Object[]{"firefox", "49.0", "Windows 10"},
	                new Object[]{"internet explorer", "11.0", "Windows 7"},
	                //new Object[]{"safari", "10.0", "OS X 10.11"},
	                new Object[]{"chrome", "54.0", "Windows 7"},
	               // new Object[]{"firefox", "latest-1", "Windows 7"},
	        };
	    }
	  
	  protected void createDriver(String browser, String version, String os, String methodName)
	            throws MalformedURLException, UnexpectedException {
	        DesiredCapabilities capabilities = new DesiredCapabilities();

	        // set desired capabilities to launch appropriate browser on Sauce
	        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
	        capabilities.setCapability(CapabilityType.VERSION, version);
	        capabilities.setCapability(CapabilityType.PLATFORM, os);
	        capabilities.setCapability("name", methodName);

	       

	        // Launch remote browser and set it as the current thread
	        webDriver.set(new RemoteWebDriver(
	                new URL("https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub"),
	                capabilities));

	        // set current sessionId
	        String id = ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
	        sessionId.set(id);
	    }
	
	  public WebDriver getWebDriver() {
	        return webDriver.get();
	    }
	  
  @Test(dataProvider = "hardCodedBrowsers")
  public void f(String browser, String version, String os, Method methodName) throws UnexpectedException, MalformedURLException {
	  
	  //create webdriver session
      this.createDriver(browser, version, os, methodName.getName());
      WebDriver driver = this.getWebDriver();

	  driver.get("https://saucelabs.com/test/guinea-pig");
	    System.out.println("title of page is: " + driver.getTitle());
	 
	   
  }
  
  @Test(dataProvider = "hardCodedBrowsers")
  public void f1(String browser, String version, String os, Method methodName) throws UnexpectedException, MalformedURLException {
	  
	  //create webdriver session
      this.createDriver(browser, version, os, methodName.getName());
      WebDriver driver = this.getWebDriver();

	 driver.get("https://saucelabs.com");
	    System.out.println("url of page is: " + driver.getCurrentUrl());
	 
	    

  }
  
  @BeforeClass
  public void beforeClass() throws Exception{
//	  DesiredCapabilities caps = DesiredCapabilities.chrome();
//    caps.setCapability("platform", "Windows XP");
//    caps.setCapability("version", "43.0");
// 
     //driver = new RemoteWebDriver(new URL(URL), caps);
  }

  @AfterMethod
  public void afterClass(ITestResult result) {
	  ((JavascriptExecutor) webDriver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
      webDriver.get().quit();
  }

}
