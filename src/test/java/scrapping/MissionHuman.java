package scrapping;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ExcelUtils;

public class MissionHuman {
	
	WebDriver driver=null;
	
	@Test
	public void MissionHumanScrapping() throws IOException {	
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		driver.get(Config.URL);
		
		//// table ////
		
	 String text=driver.findElement(By.id("dtBasicExample_info")).getText();
	 System.out.println(text); //Showing 1 to 10 of 567 entries
		
	 ///No.of pages
	Integer total_pages=Integer.valueOf(text.substring(text.indexOf("of")+3,text.indexOf("entries")-1));
	System.out.println("Total no.of Pages:"+total_pages);
	
	//// write excel headers
	ExcelUtils xlUtils=new ExcelUtils(Config.EXCEL_PATH,Config.MissionHumansheetName);
	
	xlUtils.setCellData(0,1,"District");
	xlUtils.setCellData(0,2,"Institution");
	xlUtils.setCellData(0,3,"Covid Beds(Total)");
	xlUtils.setCellData(0,4,"Covid Beds(Occupied)");
	xlUtils.setCellData(0,5,"Covid Beds(Vacant)");
	xlUtils.setCellData(0,6,"Oxygen Supported Beds(Total)");
	xlUtils.setCellData(0,7,"Oxygen Supported Beds(Occupied)");
	xlUtils.setCellData(0,8,"Oxygen Supported Beds(Vacant)");
	xlUtils.setCellData(0,9,"Non-Oxygen Supported Beds(Total)");
	xlUtils.setCellData(0,10,"Non-Oxygen Supported Beds(Occupied)");
	xlUtils.setCellData(0,11,"Non-Oxygen Supported Beds(Vacant)");
	xlUtils.setCellData(0,12,"ICU Beds(Total)");
	xlUtils.setCellData(0,13,"ICU Beds(Occupied)");
	xlUtils.setCellData(0,14,"ICU Beds(Vacant)");
	xlUtils.setCellData(0,15,"Ventilators(Total)");
	xlUtils.setCellData(0,16,"Ventilators(Occupied)");
	xlUtils.setCellData(0,17,"Ventilators(Vacant)");
	xlUtils.setCellData(0,18,"Last Updated");
	xlUtils.setCellData(0,19,"Contact Number");
	xlUtils.setCellData(0,20,"Remarks");
	
	int excelRow=0;
	/// To retrieve all values from the table
	for(int p=1;p<=total_pages;p++) {
		
		int rows=driver.findElements(By.xpath("//table[@id='dtBasicExample']/tbody/tr")).size();
		System.out.println("No.of Rows in a page:"+rows);
		
		int cols=driver.findElements(By.xpath("//table[@id='dtBasicExample']/tbody/tr[1]/td")).size();
		System.out.println("No.of Columns in a page:"+cols);
		
	for(int r=1;r<=rows;r++) {
		for(int c=1;c<=cols;c++) {
			String data=driver.findElement(By.xpath("//table[@id='dtBasicExample']/tbody/tr["+r+"]/td["+c+"]")).getText();
		    System.out.println(data);
		    xlUtils.setCellData((r+excelRow), c, data);
		}
		}
	excelRow=excelRow+rows;
//	System.out.println("Excel Increment:" +excelRow);
		driver.findElement(By.xpath("//a[@id='dtBasicExample_next']")).click();	
	    System.out.println("WebScrapping Done successfully");
	
	}
	driver.close();	
	
	}
	
   }
