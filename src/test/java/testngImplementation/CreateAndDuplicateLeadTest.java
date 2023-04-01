package testngImplementation;

import java.util.Map;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import genericLibraries.BaseClass;
import genericLibraries.IConstantPath;
import genericLibraries.TabNames;

//Create and duplicate lead

// This is lead test

public class CreateAndDuplicateLeadTest extends BaseClass{

	@Test
	public void createAndDuplicateLeadTest() {
		SoftAssert soft = new SoftAssert();
		
		//home.clickLeadsTab();
		home.clickRequiredTab(web, TabNames.LEADS);
		soft.assertTrue(driver.getTitle().contains("Leads"));	
		
		leads.clickPlusButton();
		soft.assertTrue(createLead.getPageHeader().contains("Creating New Lead"));
				
		Map<String, String> map = excel.readDataFromExcel("Create and Duplicate Lead", "LeadsTestData");
		
		createLead.selectSalutation(web, map.get("First Name Salutation"));
		String leadName = map.get("Last Name")+javaUtil.generateRandomNumber(100);
		createLead.setLastName(leadName);
		createLead.setCompanyName(map.get("Company"));
		createLead.clickSaveButton();
		
		soft.assertTrue(newLeadInfo.getPageHeader().contains(leadName));
		
		newLeadInfo.clickDuplicateButton();
		soft.assertTrue(duplicatingPage.getPageHeader().contains(leadName));
		
		String duplicateLeadName = map.get("New Last Name")+javaUtil.generateRandomNumber(100);
		duplicatingPage.setNewLeadName(duplicateLeadName);
		duplicatingPage.clickSaveButton();
		
		newLeadInfo.clickLeadsLink();
		soft.assertTrue(leads.getNewLead().equals(duplicateLeadName));
		if(leads.getNewLead().equals(duplicateLeadName)) 
			excel.setDataToExcel("Create and Duplicate Lead", "Pass", IConstantPath.EXCEL_FILE_PATH, "LeadsTestData");
		else 
			excel.setDataToExcel("Create and Duplicate Lead", "Fail", IConstantPath.EXCEL_FILE_PATH, "LeadsTestData");
		
		soft.assertAll();
	}
}
