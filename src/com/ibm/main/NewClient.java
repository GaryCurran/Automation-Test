package com.ibm.main;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NewClient extends BaseSiteCreation {

	@Before()
	public void setupTest() throws IOException {
		super.setupTest();
	}

	@Test
	public void addNewClient() {

		System.out.println("-----Launch BR SiteCreation-----");

		SiteCreationTasks.navigateToLoginPage(getUrl());
		SiteCreationTasks.login(getUsername(), getPassword(), getClientIdentifier(), getUrl());
		//SiteCreationTasks.selectEnvironment(getEnv());
//		SiteCreationTasks.navigateToAddClient();
//		SiteCreationTasks.addClientDetails();
//		SiteCreationTasks.logout();
	}

	//@AfterClass
	public static void applicationClose() {
		SiteCreationTasks.tearDown();
	}

}
