import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;



public class CommonFunctions{

	public static FileInputStream fis = null;
	public static Properties config = null;
	public static Properties objectRepo = null;
	public static ExcelUtility excel = null;
	public static WebDriver wbDv = null;
	public static EventFiringWebDriver driver = null;
	public static boolean isLoggedIn=false; 

	@BeforeSuite
	public static void initalize() {

		try {
			// Checking the Operating System
			if (System.getProperty("os.name").contains("Windows")) {

				fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties");
				config = new Properties();
				config.load(fis);

				excel = new ExcelUtility(System.getProperty("user.dir") + "\\src\\test\\resources\\Test-Cases.xlsx");

			}else {

				fis = new FileInputStream(System.getProperty("user.dir") + File.separator +  "/src/test/resources/config.properties");
				config = new Properties();
				config.load(fis);

				excel = new ExcelUtility(System.getProperty("user.dir") + File.separator + "/src/test/resources/Test-Cases.xlsx");

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@AfterSuite
	public static void closeBrowser(){

		driver.close();
	}


	public Object[][] getData(String sheetName) {



		if (!(excel.isSheetExist(sheetName))) {



			excel = null;

			return new Object[1][0];

		}



		int rows = excel.getRowCount(sheetName) - 1;



		if (rows <= 0) {



			Object[][] testData = new Object[1][0];

			return testData;

		}



		rows = excel.getRowCount(sheetName);

		int cols = excel.getColumnCount(sheetName);



		Object[][] data = new Object[rows - 1][cols];



		for (int rowNum = 2; rowNum <= rows; ++rowNum) {



			for (int colNum = 0; colNum < cols; ++colNum) {



				data[(rowNum - 2)][colNum] = excel.getCellData(sheetName, colNum, rowNum);

			}

		}



		return data;

	}


}
