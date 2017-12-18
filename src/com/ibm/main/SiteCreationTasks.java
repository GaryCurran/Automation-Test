package com.ibm.main;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ibm.main.Utils.TaskUtils;
import com.ibm.objects.AppObjects;

public class SiteCreationTasks extends TaskUtils {

	static WebElement we;
	static TaskUtils tasks = new TaskUtils();
	static AppObjects obj = new AppObjects();

	public static void navigateToLoginPage(String url) {
		System.out.println("Navigating to WorkBench: " + url);
		getDriver().navigate().to(url);
	}

	public static void login(String username, String password, String clientIdentify, String url) {
		System.out.println("Logging in to: \n" + url);

		we = obj.userNameTextBox();
		we.clear();
		we.sendKeys(username);

		we = obj.clientTextBox();
		we.clear();
		we.sendKeys(clientIdentify);

		we = obj.passwordTextBox();
		we.clear();
		we.sendKeys(password);

		we = obj.loginButton();
		we.click();

		tasks.diverWait(5);
	}

	public static void selectEnvironment(String env) {
		System.out.println("Selecting " + env + " as the environment");

		getDriver().switchTo().frame(obj.navFrame());
		we = obj.envirnoment();

		Select select = new Select(we);
		WebElement element = select.getFirstSelectedOption();
		System.out.println("Default environment is " + element.getText());
		boolean envFound = false;
		if (!element.getText().equals(env)) {
			System.out.println("Changing environment from " + element.getText() + " to " + env);
			select = new Select(we);
			List<WebElement> allOptions = select.getOptions();

			for (WebElement weEnvironment : allOptions) {
				if (weEnvironment.getText().equals(env)) {
					System.out.println("Setting Environemnt To " + env);
					weEnvironment.click();
					envFound = true;
					break;
				}
			}
			if (!envFound) {
				System.out.println("Could not find " + env + " as an option");
			}

			getDriver().switchTo().defaultContent();
			getDriver().switchTo().frame(obj.mainFrame());

			// Wait for "Welcome to Workbench" to re-appear
			WebDriverWait waitForPageTitle = new WebDriverWait(getDriver(), 50);
			waitForPageTitle.until(ExpectedConditions.visibilityOfElementLocated(By
					.cssSelector("body > center > table > tbody > tr > td")));
			tasks.diverWait(3);

			WebDriverWait waitFoNavBar = new WebDriverWait(getDriver(), 30);
			waitFoNavBar.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#nav")));
			getDriver().switchTo().frame(obj.navFrame());

		} else {
			System.out.println("Default environment is required environment therefore, no action needed");
		}

		tasks.diverWait(3);
	}

	public static void navigateToAddClient() {
		System.out.println("Navigating to Add New Client");

		we = obj.admin();
		we.click();

		getDriver().switchTo().defaultContent();
		getDriver().switchTo().frame(obj.mainFrame());

		getDriver().findElement(By.linkText(UIMap.manageClients)).click();
		// //we.findElement(By.linkText(lText)).click();
		// we = obj.manageClients();
		// we.click();

		we = obj.addNewClient();
		we.click();

		tasks.switchNewWindow("Add new client");
		tasks.windowMax();
		tasks.diverWait(3);

	}

	public static void addClientDetails() {
		System.out.println("Adding New Client details");

		we = obj.clientNameTextBox();
		we.sendKeys("Test");

		we = obj.cancelButton();
		we.click();

		tasks.switchParentWindow();

		tasks.diverWait(3);
	}

	public static void logout() {
		System.out.println("Logging out of Work Bench");

		getDriver().switchTo().frame(obj.navFrame());

		we = obj.logOffButton();
		we.click();

		tasks.diverWait(3);
	}

	public static void tearDown() {
		System.out.println("Finishing tests and closing the browser");
		if (getDriver() != null) {
			getDriver().quit();
		}
	}

}
