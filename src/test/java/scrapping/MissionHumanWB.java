package scrapping;

import java.io.IOException;
import java.time.Duration;

import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ExcelUtils;

public class MissionHumanWB {

	WebDriver driver=null;
		
		@Test
		public void MissionHumanScrappingWestBengal() throws IOException, InterruptedException {	
			WebDriverManager.chromedriver().setup();
			WebDriver driver=new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.manage().window().maximize();
			driver.get(Config.WBURL);
			
			//// write excel headers
			ExcelUtils xlUtils=new ExcelUtils(Config.WBEXCEL_PATH,Config.WBsheetName);
			
			xlUtils.setCellData(0,0,"Hospital Name");
			xlUtils.setCellData(0,1,"No.of Oxygen Bed");
			xlUtils.setCellData(0,2,"No.of ICU with Vent");
			xlUtils.setCellData(0,3,"Address");
			xlUtils.setCellData(0,4,"Phone number");
			xlUtils.setCellData(0,5,"Last Updated");
			
			//// To click all links 
		for(int i=1;i<12;i++) {
				Thread.sleep(1000);
		   		driver.findElement(By.cssSelector("button[class='btn btn-primary']")).sendKeys(Keys.ENTER);
		
			}
		
			int rows=driver.findElements(By.tagName("tr")).size();
			System.out.println("No.of Rows in a page:"+rows);
		
			for(int r=1;r<=rows;r++) {
				
			
		    WebElement hosp_name=driver.findElement(By.xpath("//tbody/tr["+r+"]/td//div[@class='p-1']"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,100)", "");
			js.executeScript("arguments[0].click()", hosp_name);
		//	Thread.sleep(1000);
		//	hosp_name.click(); 
									
			System.out.println("Name of Hospital:" +hosp_name.getText());
			WebElement oxygenbed=driver.findElement(By.xpath("(//tr[1]/td[@class='text-center'])[2]"));
            System.out.println("The no.of Oxygen bed:" +oxygenbed.getText());
            WebElement ICU_vent = driver.findElement(By.xpath("(//td[@class='text-center'])[4]"));
            System.out.println("The no.of ICU bed with Ventilator:" +ICU_vent.getText());
            WebElement phone_num = driver.findElement(By.xpath("//td/p/span[contains(text(),'Phone')]"));
			System.out.println("Phone Number:" +phone_num.getText());
           	WebElement Address=	driver.findElement(By.xpath("//td/p/span[contains(text(),'Address')]"));
			System.out.println("Address:" +Address.getText());
          	WebElement last_updated = driver.findElement(By.cssSelector("span[class='text-muted font-weight-bold']"));
          	System.out.println("Last updated:" +last_updated.getText());
         
          	xlUtils.setCellData(r,0,hosp_name.getText());
			xlUtils.setCellData(r,1,oxygenbed.getText());
			xlUtils.setCellData(r,2,ICU_vent.getText());
			xlUtils.setCellData(r,3,Address.getText());
			xlUtils.setCellData(r,4,phone_num.getText());
			xlUtils.setCellData(r,5,last_updated.getText());
			
			js.executeScript("arguments[0].click()", hosp_name);
			}
			System.out.println("WebScrapping Done successfully");
			driver.close();
			
	}
   }
