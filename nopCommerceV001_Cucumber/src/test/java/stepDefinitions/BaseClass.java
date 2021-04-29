package stepDefinitions;

import java.util.Properties;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pageObjects.Add_customerpage;
import pageObjects.LoginPage;
import pageObjects.Search_Customerpage;

public class BaseClass {

	public WebDriver driver;
	public LoginPage lp;
	public Add_customerpage addCust;
	public Search_Customerpage searchCust;
	public static Logger logger;
	public Properties configProp;
	
	
	//Created for generating random string for Unique email
	public static String randomestring() {
		String generatedString1 = RandomStringUtils.randomAlphabetic(5);
		return (generatedString1);
	}
}


