package testngImplementation;

import java.util.Map;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import genericLibraries.BaseClass;
import genericLibraries.IConstantPath;

public class CreateOrganizationWithIndustryAndTypeTest extends BaseClass{

	@Test
	public void createOrganizationWithIndustryAndTypeTest() {
		SoftAssert soft = new SoftAssert();
		
		home.clickOrganization();
		soft.assertTrue(driver.getTitle().contains("Organizations"));
		
		organizations.clickPlusButton();
		soft.assertTrue(createOrganization.getPageHeader().contains("Creating"));
		
		Map<String,String> map = excel.readDataFromExcel("Create Organization With Industry And Type", "OrganizationsTestData");
		
		String orgName = map.get("Organization Name")+javaUtil.generateRandomNumber(100);
		createOrganization.setOrganizationName(orgName);
		createOrganization.selectIndustry(web, map.get("Industry"));
		createOrganization.selectType(web, map.get("Type"));
		createOrganization.clickSaveButton();
		
		soft.assertTrue(newOrganization.getPageHeader().contains(orgName));
		newOrganization.clickOrganizationsLink();
		
		soft.assertTrue(organizations.getNewOrganization().equals(orgName));
		if(organizations.getNewOrganization().equals(orgName))
			excel.setDataToExcel("Create Organization With Industry And Type", "Pass", IConstantPath.EXCEL_FILE_PATH, "OrganizationsTestData");
		else 
			excel.setDataToExcel("Create Organization With Industry And Type", "Fail", IConstantPath.EXCEL_FILE_PATH, "OrganizationsTestData");
		
		soft.assertAll();
	}

}
