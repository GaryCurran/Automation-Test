package com.ibm.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ibm.main.UIMap;
import com.ibm.main.Utils.TaskUtils;

public class AppObjects extends TaskUtils {

	public WebElement passwordTextBox() {
		return getDriver().findElement(By.cssSelector(UIMap.passwordTextBox));
	}

	public WebElement clientTextBox() {
		return getDriver().findElement(By.cssSelector(UIMap.clientTextBox));
	}

	public WebElement userNameTextBox() {
		return getDriver().findElement(By.cssSelector(UIMap.userNameTextBox));
	}

	public WebElement loginButton() {
		return getDriver().findElement(By.cssSelector(UIMap.btnLogin));
	}

	public WebElement cancelButton() {
		return getDriver().findElement(By.cssSelector(UIMap.btnCancel));
	}

	public WebElement saveButton() {
		return getDriver().findElement(By.cssSelector(UIMap.btnSave));
	}

	public WebElement logOffButton() {
		return getDriver().findElement(By.cssSelector(UIMap.btnLogOff));
	}

	public WebElement envirnoment() {
		return getDriver().findElement(By.cssSelector(UIMap.envirnoment));
	}

	public WebElement admin() {
		return getDriver().findElement(By.cssSelector(UIMap.admin));
	}

	public WebElement manageClients() {
		return getDriver().findElement(By.linkText(UIMap.manageClients));
	}

	public WebElement addNewClient() {
		return getDriver().findElement(By.cssSelector(UIMap.addNewClient));
	}

	public WebElement clientNameTextBox() {
		return getDriver().findElement(By.cssSelector(UIMap.clientNameText));
	}

	public WebElement mainFrame() {
		return getDriver().findElement(By.cssSelector(UIMap.mainFrame));
	}

	public WebElement navFrame() {
		return getDriver().findElement(By.cssSelector(UIMap.navFrame));
	}

	public String newClientWindow() {
		return UIMap.addNewClientWindow;
	}

}
