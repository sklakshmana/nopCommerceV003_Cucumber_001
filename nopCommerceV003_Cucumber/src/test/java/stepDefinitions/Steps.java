package stepDefinitions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import junit.framework.Assert;
import pageObjects.AddcustomerPage;
import pageObjects.LoginPage;
import pageObjects.SearchCustomerPage;

public class Steps extends BaseClass {
	
	@Before
	public void setup() throws IOException
	{
		configProp=new Properties();
		
		FileInputStream fis=new FileInputStream("config.properties");
		configProp.load(fis);
		
		logger=Logger.getLogger("nopComerce");  //Added loger
		PropertyConfigurator.configure("log4j.properties");
		
		String br=configProp.getProperty("browser");
		
		if(br.equalsIgnoreCase("firefox"))
		{
			System.out.println("test1");
			System.setProperty("webdriver.gecko.driver", configProp.getProperty("firefoxpath"));
			driver=new FirefoxDriver();	

		}
		else if (br.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", configProp.getProperty("chromepath"));
			driver=new ChromeDriver();		
		}
		else if (br.equalsIgnoreCase("ie"))
		{
			System.setProperty("webdriver.ie.driver", configProp.getProperty("iepath"));
			driver=new ChromeDriver();	
		}
		logger.info("*******Launching browser*************");
	}
		
	
	
	@Given("User Launch Chrome browser")
	public void user_Launch_Chrome_browser() {
		

		
		lp=new LoginPage(driver);
	}

	@When("User opens URL {string}")
	public void user_opens_URL(String url) {
		logger.info("*******Openning the URL*************");	
		driver.get(url);
	}

	@When("User enters Email as {string} and Password as {string}")
	public void user_enters_Email_as_and_Password_as(String email, String password) {
		logger.info("*******Providing login details*************");
		lp.setUserName(email);
		lp.setPassword(password);
	}

	@When("Click on Login")
	public void click_on_Login() throws InterruptedException {
		lp.clickLogin();
		Thread.sleep(3000);
	}

	@Then("Page Title should be {string}")
	public void page_Title_should_be(String title) {
		if (driver.getPageSource().contains("Login was unsuccessful")) {
			logger.info("*******login failed*************");
			driver.close();
			Assert.assertEquals(title, driver.getTitle());
		} else {
			Assert.assertEquals(title, driver.getTitle());
			logger.info("*******login passed*************");
		}

	}

	@When("User click on Log out link")
	public void user_click_on_Log_out_link() throws InterruptedException {
		lp.clickLogout();
		Thread.sleep(3000);

	}

	@Then("close browser")
	public void close_browser() {
		driver.close();
		logger.info("*******closing browser*************");		

	}	
	
//	Customers Feature methods

		@Then("User can view Dashboard")
		public void user_can_view_Dashboard() throws InterruptedException {
			
			addCust=new AddcustomerPage(driver);
			Assert.assertEquals("Dashboard / nopCommerce administration", addCust.getPageTitle());
			Thread.sleep(3000);
		}

		@When("User click on customers Menu")
		public void user_click_on_customers_Menu() throws InterruptedException {
			Thread.sleep(3000);
			addCust.clickOnCustomersMenu();

		}

		@When("click on customers Menu Item")
		public void click_on_customers_Menu_Item() throws InterruptedException {
			Thread.sleep(2000);
			addCust.clickOnCustomersMenuItem();

		}

		@When("click on Add new button")
		public void click_on_Add_new_button() throws InterruptedException {
			addCust.clickOnAddnew();
			Thread.sleep(2000);
		}

		@Then("User can view Add new customer page")
		public void user_can_view_Add_new_customer_page() {
			Assert.assertEquals("Add a new customer / nopCommerce administration", addCust.getPageTitle());

		}

		@When("User enter customer info")
		public void user_enter_customer_info() throws InterruptedException {
			String email=randomestring()+"@gmail.com";
			
			addCust.setEmail(email);
			addCust.setPassword("test123");
			Thread.sleep(2000);
			
			addCust.setCustomerRoles("Administrators");
			Thread.sleep(3000);
			
			addCust.setManagerOfVendor("Vendor 2");
//			Thread.sleep(3000);			
			addCust.setGender("Male");
			Thread.sleep(3000);			
			addCust.setFirstName("Lakshman");
			addCust.setLastName("Murthy");			
			addCust.setDob("07/05/1985");
			addCust.setCompanyName("basyQA");
			addCust.setAdminContent("This is for testing");
		}

		@When("click on Save button")
		public void click_on_Save_button() throws InterruptedException {
			addCust.clickOnSave();
			Thread.sleep(2000);

		}

		@Then("User can view confirmation message {string}")
		public void user_can_view_confirmation_message(String msg) {
			Assert.assertTrue(driver.findElement(By.tagName("body")).getText().contains("The new customer has been added successfully"));

		}
		
		//steps for searching a customer using Email ID.................
		
		@When("Enter customer Email")
		public void enter_customer_Email() {
			searchCust=new SearchCustomerPage(driver);			
			searchCust.setEmail("victoria_victoria@nopCommerce.com");
		}

		@When("Click on search button")
		public void click_on_search_button() throws InterruptedException {
			searchCust.clickSearch();
			Thread.sleep(3000);
		}

		@Then("User should found Email in the search table")
		public void user_should_found_Email_in_the_search_table() {
			
			boolean status=searchCust.searchCustomerByEmail("victoria_victoria@nopCommerce.com");			
			Assert.assertEquals(true, status);

		}
	
	

}
