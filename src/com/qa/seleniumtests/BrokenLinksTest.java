package com.qa.seleniumtests;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class BrokenLinksTest{
	
	@Test
	public void verifyToFindTheBrokenLinksTest()throws IOException{
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		WebDriver driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://makemysushi.com/404?");
		
		//Get all the links in the login page
		//All the a tags
		List<WebElement> loginPageLinks=driver.findElements(By.xpath("//a"));
		// a list will have all the images as well
		loginPageLinks.addAll(driver.findElements(By.tagName("img")));
		
		//Active links list
		List<WebElement> activeLinks = new ArrayList<WebElement>();
		
		//Get the total no: of links
		System.out.println("The total no: of all links in the login page are: "+loginPageLinks.size());
		
		//iterate through all the links. Exclude all the links and images which do not have href attributes
		for(int i=0; i< loginPageLinks.size();i++){
			System.out.println(loginPageLinks.get(i).getAttribute("href"));
			if(loginPageLinks.get(i).getAttribute("href") != null && (!loginPageLinks.get(i).getAttribute("href").contains("javascript"))){
				activeLinks.add(loginPageLinks.get(i));
				//System.out.println("The list of active links are: "+activeLinks.get(i).getAttribute("href"));
			}
		}
		System.out.println("===========================================================================================");
		//get the size of active links list
		System.out.println("List of active links in the login page are: "+activeLinks.size());
		
		//check the href urls with http connection api for active links only
		for(int j=0; j<activeLinks.size();j++){
				//System.out.println(activeLinks.get(j).getAttribute("href"));
				String responseMsg;
				try {
					HttpURLConnection connection= (HttpURLConnection) new URL(activeLinks.get(j).getAttribute("href")).openConnection();
					connection.connect();
					responseMsg = connection.getResponseMessage();
					connection.disconnect();
					System.out.println(activeLinks.get(j).getAttribute("href")+"-->"+responseMsg);
				} catch (Exception e) {
					e.printStackTrace();
				}		
		}
		driver.quit();
		
	}
	
}