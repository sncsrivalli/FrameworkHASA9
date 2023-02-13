package genericLibraries;

public enum TabNames {
	
	ORGANIZATIONS("Organizations"), SIGNOUT("Sign Out"), LEADS("Leads"), CONTACTS("Contacts");

	private String tabName;
	private TabNames(String tab) {
		this.tabName = tab;
	}
	
	public String getTabname() {
		return tabName;
	}
}
