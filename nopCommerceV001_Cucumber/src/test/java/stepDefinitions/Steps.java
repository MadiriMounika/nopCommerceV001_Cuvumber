package stepDefinitions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageObjects.Add_customerpage;
import pageObjects.LoginPage;
import pageObjects.Search_Customerpage;

public class Steps extends BaseClass {
	
	@Before
	public void setup() throws IOException
	{
		//Logger
				logger=Logger.getLogger("nopComemrce"); //Added logger
				PropertyConfigurator.configure("Log4j.properties");//Added logger
				
				//Reading properties
				configProp=new Properties();
				FileInputStream configPropfile=new FileInputStream("config.properties");
				configProp.load(configPropfile);
				
				String br=configProp.getProperty("browser");
				
				if(br.equals("chrome"))
				{
				System.setProperty("webdriver.chrome.driver",configProp.getProperty("chromepath"));
				driver=new ChromeDriver();
				}
				else if (br.equals("firefox")) {
					System.setProperty("webdriver.gecko.driver",configProp.getProperty("firefoxpath"));
					driver = new FirefoxDriver();
				}
		
		
	          	logger.info("****Launching browser***");
	           }
	
	@Given("User Launch Chrome browser")
	public void user_Launch_Chrome_browser() {
		
		
		lp=new LoginPage(driver);
	}

	@When("User opens URL {string}")
	public void user_opens_URL(String url) {
	  logger.info("******** Opening URL*********");
	  driver.get(url);
	  driver.manage().window().maximize();
	}

	@When("User enters Email as {string} and Password as {string}")
	public void user_enters_Email_as_and_Password_as(String email, String password) {
		logger.info("******** Providing login details*********");
	    lp.setUserName(email);
	    lp.setPassword(password);
	}

	@When("Click on Login")
	public void click_on_Login() throws InterruptedException {
		logger.info("******** started login*********");
	   lp.clickLogin();
	   Thread.sleep(3000);
	}

	@Then("Page Title should be {string}")
	public void page_Title_should_be(String title) throws InterruptedException {
	   
		if (driver.getPageSource().contains("Login was unsuccessful.")) {
			driver.close();
			logger.info("******** Login passed*********");
			Assert.assertTrue(false);
		} else {
			logger.info("******** Login failed*********");
			Assert.assertEquals(title, driver.getTitle());
		}
		Thread.sleep(3000);
		
	}

	@When("User click on Log out link")
	public void user_click_on_Log_out_link() throws InterruptedException {
		logger.info("******** Click on logout link*********");
		lp.clickLogout();
		Thread.sleep(3000);
	}

	@Then("close browser")
	public void close_browser() {
		logger.info("********closing browser********");
	   driver.quit();
	}
	
	//Customer feature step definitions
	
	@Then("User can view Dashboard")
	public void user_can_view_Dashboard() {
     
		addCust = new Add_customerpage(driver);
	    Assert.assertEquals("Dashboard / nopCommerce administration", addCust.getPageTitle());
	}

	@When("User clicks on customers menu")
	public void user_clicks_on_customers_menu() throws InterruptedException {
		Thread.sleep(3000);
	    addCust.clickOnCustomersMenu();
	}

	@When("click on customers menu item")
	public void click_on_customers_menu_item() throws InterruptedException {
		Thread.sleep(2000);
	    addCust.clickOnCustomersMenuItem();
	}
	@When("click on add new button")
	public void click_on_add_new_button() throws InterruptedException {
	    addCust.clickOnAddnew();
	    Thread.sleep(2000);
	}

	@Then("user can view add new customer page")
	public void user_can_view_add_new_customer_page() {
	   Assert.assertEquals("Add a new customer / nopCommerce administration", addCust.getPageTitle());
	}

	@When("user enter customer info")
	public void user_enter_customer_info() throws InterruptedException {
		
		logger.info("********Adding new customer*********");
		logger.info("********Proving customer details*********");
		
	   String email=randomestring()+"@gmail.com";
	   addCust.setEmail(email);
	   addCust.setPassword("test234");
	// Registered - default
			// The customer cannot be in both 'Guests' and 'Registered' customer roles
			// Add the customer to 'Guests' or 'Registered' customer role
			addCust.setCustomerRoles("Guest");
			Thread.sleep(3000);

			addCust.setManagerOfVendor("Vendor 2");
			addCust.setGender("FeMale");
			addCust.setFirstName("honey");
			addCust.setLastName("sri");
			addCust.setDob("7/05/1980"); // Format: D/MM/YYY
			addCust.setCompanyName("busyQA");
			addCust.setAdminContent("This is for testing.........");
		}
	   
	

	@When("click on save button")
	public void click_on_save_button() throws InterruptedException {
		logger.info("********Saving customer data*********");
		addCust.clickOnSave();
	    Thread.sleep(2000);
	}

	@Then("user can view confirmation message {string}")
	public void user_can_view_confirmation_message(String message) {
		Assert.assertTrue(driver.findElement(By.tagName("body")).getText()
				.contains("The new customer has been added successfully"));
	}

//steps for searching a customer using Email ID
	

	@When("enter customer Email")
	public void enter_customer_Email() {
		logger.info("********Searching customer by email id*********");
	    searchCust= new Search_Customerpage(driver);
	    searchCust.setEmail("victoria_victoria@nopCommerce.com");
	}

	@When("click on search button")
	public void click_on_search_button() throws InterruptedException {
	   searchCust.clickSearch();
	   Thread.sleep(2000);
	}

	@Then("user should found Email in the search table")
	public void user_should_found_Email_in_the_search_table() {
		 boolean status=searchCust.searchCustomerByEmail("victoria_victoria@nopCommerce.com");
		   	Assert.assertEquals(true, status);
	
	}

	//steps for searching a customer using Name
	
	@When("enter customer Firstname")
	public void enter_customer_Firstname() {
		logger.info("********Searching customer by Name*********");
		searchCust=new Search_Customerpage(driver);
		searchCust.setFirstName("Victoria");
	}

	@When("enter customer Lastname")
	public void enter_customer_Lastname() {
		searchCust.setLastName("Terces");
	}

	@Then("user should found Name in the search table")
	public void user_should_found_Name_in_the_search_table() {
		boolean status=searchCust.searchCustomerByName("Victoria Terces");
		Assert.assertEquals(true, status);
	}


	
}


