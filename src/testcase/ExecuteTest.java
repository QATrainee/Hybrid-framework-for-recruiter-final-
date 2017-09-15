package testcase;

import java.util.Properties;

import operation.ReadObject;
import operation.UiOperation;

import org.apache.log4j.BasicConfigurator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import excel.ReadExcel;

public class ExecuteTest {
	public static ExtentReports extent;
	public static ExtentTest test;

	public static void main(String args[]) throws Exception {
		WebDriver driver;
		BasicConfigurator.configure();

		// TODO Auto-generated method stub
		String workingDir = System.getProperty("user.dir");

		System.setProperty("webdriver.chrome.driver", workingDir
				+ "\\Resource\\chromedriver.exe");
		driver = new ChromeDriver();

		ReadExcel file = new ReadExcel();
		ReadObject object = new ReadObject();
		Properties allObjects = object.getObjectRepository();
		UiOperation operation = new UiOperation(driver);

		// Read keyword sheet
		Sheet sheet = file.readExcel(System.getProperty("user.dir") + "\\",
				"\\Resource\\recr.xlsx", "Sheet1");
		// Find number of rows in excel file
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		extent = new ExtentReports(System.getProperty("user.dir")
				+ "/ExtentReport.html", true);

		test = extent.startTest("Recruiter hybrid");
		for (int i = 0; i < rowCount + 1; i++) {
			// Loop over all the rows

			Row row = sheet.getRow(i);

			operation.perform(allObjects, row.getCell(0).toString(), row
					.getCell(1).toString(), row.getCell(2).toString(), row
					.getCell(3).toString(), row.getCell(4).toString());
			test.log(LogStatus.INFO, row.getCell(4).toString());

		}
		extent.flush();
		driver.close();
	}

}
