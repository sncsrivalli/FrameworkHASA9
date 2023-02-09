package testngImplementation;

import java.util.Map;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import genericLibraries.BaseClass;
import genericLibraries.IConstantPath;

public class CreateContactsTest extends BaseClass {

	@Test
	public void createContactTest() {
		SoftAssert soft = new SoftAssert();
		
		home.clickContact();
		
		soft.assertTrue(driver.getTitle().contains("Contacts"));
		contacts.clickPlusButton();
		soft.assertTrue(createContact.getPageHeader().contains("Create"));
		
		Map<String, String> map = excel.readDataFromExcel("Create Contact", "ContactsTestData");
		String contactName = map.get("Last Name") + javaUtil.generateRandomNumber(100);
		createContact.setContactName(contactName);
		createContact.clickSaveButton();
		soft.assertTrue(newContact.getPageHeader().contains(contactName));
		
		newContact.clickContactsLink();
		soft.assertTrue(contacts.getNewContact().equals(contactName));
		if (contacts.getNewContact().equals(contactName)) 
			excel.setDataToExcel("Create Contact", "Pass", IConstantPath.EXCEL_FILE_PATH, "ContactsTestData");
		else 
			excel.setDataToExcel("Create Contact", "Fail", IConstantPath.EXCEL_FILE_PATH, "ContactsTestData");
		
		soft.assertAll();
	}

}
