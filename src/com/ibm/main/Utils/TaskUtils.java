package com.ibm.main.Utils;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import com.ibm.main.BaseSiteCreation;

public class TaskUtils {

	String parentWindowHandle;

	public void diverWait(int delay) {
		try {
			TimeUnit.SECONDS.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static WebDriver getDriver() {
		return BaseSiteCreation.getDriver();
	}

	public void windowMax() {
		getDriver().manage().window().maximize();
	}

	public void switchNewWindow(String newtitle) {

		parentWindowHandle = getDriver().getWindowHandle();
		System.out.println("Switching from default window to " + "'" + newtitle + "'" + " window");
		for (String winHandle : getDriver().getWindowHandles()) {
			getDriver().switchTo().window(winHandle);
		}

	}

	public void switchParentWindow() {

		System.out.println("Switching back to default window");
		getDriver().switchTo().window(parentWindowHandle);
	}
}
