import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Meta {
public static String filePath = "C:\\Users\\pankajkumar.y\\Documents\\Workspace 2\\MetaData\\URLdata.xlsx";
	
	public static void main(String[] args) throws IOException {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\pankajkumar.y\\Documents\\Cucumber Workspace\\Meta\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setHeadless(true);
		WebDriver driver = new ChromeDriver(options);
		
		FileInputStream fis = new FileInputStream(filePath);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheet("Sheet1");
		int lastRowIndex = sh.getLastRowNum();
		for (int i = 0; i <= lastRowIndex; i++) {
			Row row = sh.getRow(i);
//			System.out.println("Execution row:"+row);
			String actURL = row.getCell(0).getStringCellValue();
			driver.get(actURL);
			//Meta Data
//			String expTitle = driver.getTitle();
//			String sheetExpTitle = row.getCell(1).getStringCellValue();			
//			if(!expTitle.equalsIgnoreCase(sheetExpTitle)) {
//				System.out.println(i+". "+actURL+"=======Fail");
//				System.out.println(driver.getTitle());
//				writeFailURLs(actURL);
//			}
			
			//Content Description				
			WebElement element = null;
			try {				
			    element = driver.findElement(By.xpath("//meta[@name='description']"));				
			}
			catch (Exception e) {				
				element=null;			
		}
			
			if(element!=null) {
				String sheetExpDescription = row.getCell(1).getStringCellValue();			
				if(!element.getAttribute("content").equalsIgnoreCase(sheetExpDescription)) {
					System.out.println(i+". "+actURL+"=======Fail");
//					System.out.println(driver.getTitle());
					writeFailURLs(actURL);
				}
			}
			else
			 {				
				System.out.println(i+". "+actURL+"=======Fail");
//				System.out.println(driver.getTitle());
				writeFailURLs(actURL);
		}
			
		}

	}
	
	public static void writeFailURLs(String value) throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream(filePath);
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheet("Sheet2");
		int lastRowIndex = sh.getLastRowNum();
		Row row = sh.createRow(lastRowIndex+1);
		Cell cell = row.createCell(0);
		cell.setCellValue(value);
		FileOutputStream fos = new FileOutputStream(filePath);
		wb.write(fos);
		wb.close();		
	}

}
