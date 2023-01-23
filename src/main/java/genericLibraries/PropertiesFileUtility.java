package genericLibraries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class contains reusable methods related to actions performed on Properties file
 * @author QPS-Basavanagudi
 *
 */
public class PropertiesFileUtility {
	
	private Properties property;
	/**
	 * This method is used to initialize Properties file
	 * @param path
	 */
	public void propertyFileInitialization(String path) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		property = new Properties();
		try {
			property.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to fetch the data from properties file
	 * @param key
	 * @return
	 */
	public String fetchProperty(String key) {
		
		return property.getProperty(key);
	}
	
	/**
	 * This method is used to modify properties file
	 * @param key
	 * @param value
	 * @param path
	 * @param message
	 */
	public void modifyPropertiesFile(String key, String value, String path, String message) {
		
		property.put(key, value);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			property.store(fos, message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
