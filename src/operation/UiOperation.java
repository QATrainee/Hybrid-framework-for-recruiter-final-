package operation;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UiOperation {
	static WebDriver driver;
	static Logger log = Logger.getLogger("demo");

	public UiOperation(WebDriver driver) {
		this.driver = driver;
	}

	public void perform(Properties p, String operation, String objectName,
			String objectType, String value, String description)
			throws Exception {
		switch (operation.toUpperCase()) {
		case "CLICK":

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(
					ExpectedConditions.presenceOfElementLocated(this.getObject(
							p, objectName, objectType))).click();
			log.info(description);
			break;

		case "SETTEXT":

			WebDriverWait wait1 = new WebDriverWait(driver, 30);
			wait1.until(
					ExpectedConditions.presenceOfElementLocated(this.getObject(
							p, objectName, objectType))).sendKeys(
					p.getProperty(value));
			log.info(description);
			break;
		case "ALERT":

			String mytitle = "Top Jobs for Smart Talent | TechFetch.com";
			if (!driver.getTitle().equals(mytitle)) {
				driver.findElement(By.xpath(".//*[@id='alertify-ok']")).click();
				Thread.sleep(3000);
				driver.findElement(By.id("txtpwd")).sendKeys("TV@567");
				Thread.sleep(2000);
				driver.findElement(By.id("btnSubmit")).click();
			}
			break;

		case "DEFAULT":
			driver.switchTo().defaultContent();
			log.info(description);
			break;

		case "FRAME":

			driver.switchTo().defaultContent();
			WebDriverWait wait2 = new WebDriverWait(driver, 30);
			wait2.until(ExpectedConditions
					.frameToBeAvailableAndSwitchToIt(value));
			log.info(description);

			break;

		case "GOTOURL":

			driver.get(p.getProperty("url"));
			log.info(description);
			break;

		default:
			break;
		}
	}

	/**
	 * Find element BY using object type and value
	 * 
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @return
	 * @throws Exception
	 */
	private By getObject(Properties p, String objectName, String objectType)
			throws Exception {

		if (objectType.equalsIgnoreCase("XPATH")) {

			return By.xpath(p.getProperty(objectName));
		}

		else if (objectType.equalsIgnoreCase("CLASSNAME")) {

			return By.className(p.getProperty(objectName));

		}

		else if (objectType.equalsIgnoreCase("NAME")) {

			return By.name(p.getProperty(objectName));

		}

		else if (objectType.equalsIgnoreCase("CSS")) {

			return By.cssSelector(p.getProperty(objectName));

		}

		else if (objectType.equalsIgnoreCase("LINK")) {

			return By.linkText(p.getProperty(objectName));

		}

		else if (objectType.equalsIgnoreCase("PARTIALLINK")) {

			return By.partialLinkText(p.getProperty(objectName));

		} else if (objectType.equalsIgnoreCase("ID")) {

			return By.id(p.getProperty(objectName));

		} else {
			throw new Exception("Wrong object type");
		}
	}
}