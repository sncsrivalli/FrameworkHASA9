package testngImplementation;

import java.util.Map;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import genericLibraries.BaseClass;
import genericLibraries.IConstantPath;
import genericLibraries.TabNames;

public class CreateContactWithOrgTest extends BaseClass{

	@Test
	public void createContactWithOrgTest() {
		SoftAssert soft = new SoftAssert();
		
		//home.clickContact();
		home.clickRequiredTab(web, TabNames.CONTACTS);
		soft.assertTrue(driver.getTitle().contains("Contacts"));
		
		contacts.clickPlusButton();
		soft.assertTrue(createContact.getPageHeader().contains("Creating"));
		
		Map<String, String> map = excel.readDataFromExcel("Create Contact With Organization", "ContactsTestData");
		String contactName = map.get("Last Name")+javaUtil.generateRandomNumber(100);
		createContact.setContactName(contactName);
		createContact.selectExistingOrganization(web, map.get("Organization Name"));
		createContact.clickSaveButton();
		
		soft.assertTrue(newContact.getPageHeader().contains(contactName));
		
		newContact.clickContactsLink();
		soft.assertTrue(contacts.getNewContact().equals(contactName));
		if(contacts.getNewContact().equals(contactName)) 
			excel.setDataToExcel("Create Contact With Organization", "Pass", IConstantPath.EXCEL_FILE_PATH, "ContactsTestData");
		else 
			excel.setDataToExcel("Create Contact With Organization", "Fail", IConstantPath.EXCEL_FILE_PATH, "ContactsTestData");
		
		soft.assertAll();
	}

}
