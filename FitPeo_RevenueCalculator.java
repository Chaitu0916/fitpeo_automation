package Assignment;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class FitPeo_RevenueCalculator {
	
	//user defined methods
	static void ScrollDown(WebDriver driver, WebElement Element)
	{
		 //Create JavascriptExecutor instance
		JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", Element);
	}
	

	public static void main(String[] args) {
		
		//opening the browser
		WebDriver driver = new ChromeDriver();
		
		//adding the implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//Maximize the browser window
		driver.manage().window().maximize();
		
		//launching application URL
		driver.get("https://www.fitpeo.com/");
		
		//verify the title of the page
		if(driver.getTitle().equals("Remote Patient Monitoring (RPM) - fitpeo.com"))
		{
			System.out.println("we are on the fitpeo.Page");
		}
		else
		{	
			System.out.println("we are not on the fitpeo.Page");
			System.exit(1);
		}
		
		//Test Step 1: Navigate to the Home Page
		WebElement HomePage_button = driver.findElement(By.xpath("//div[text()='Home']"));
		HomePage_button.isDisplayed();
		HomePage_button.click();
		
		//Test Step 2: Navigate to the RevenueCalculatorPage
		WebElement RevenueCalculatorPage_button = driver.findElement(By.xpath("//div[text()='Revenue Calculator']"));
		RevenueCalculatorPage_button.isDisplayed();
		RevenueCalculatorPage_button.click();
		
		//Test Step 3: Scroll Down to the slider selection
		WebElement Element = driver.findElement(By.xpath("//h4[text()='Medicare Eligible Patients']"));
		ScrollDown(driver,Element);
		
		//Test Step 4: Adjust the slider
		WebElement textBox = driver.findElement(By.xpath("//input[@type='number']"));

		WebElement Slider = driver.findElement(By.xpath("//span[contains(@class,'MuiSlider-thumb')]"));
		Slider.click();
		
		//Perform mouse/keyboard actions
		Actions mouse = new Actions(driver);
		while(true)
		{
			if(textBox.getAttribute("value").equals("820"))
			{
				break;
			}
			
			mouse.keyDown(Keys.ARROW_RIGHT).keyUp(Keys.ARROW_RIGHT).perform();
		}
		
		//Test Step 5: update the value in the text field
				mouse.keyDown(Keys.TAB).keyUp(Keys.TAB).keyDown(Keys.BACK_SPACE).keyUp(Keys.BACK_SPACE).perform();
				textBox.sendKeys("560");
				
						
				//Test Step 6: Validate slider value
				System.out.println("Slider location X:" + Slider.getLocation().getX()+"Slider location Y:" + Slider.getLocation().getY());
			
				if(textBox.getAttribute("value").equals("560"))
				{
					System.out.println("Slider value updated to 560");
				}
				else
				{
					System.out.println("Slider value not updated");
				}
		
		//to set back the value cntrl+z
			mouse.keyDown(Keys.CONTROL).keyDown("z").keyUp("z").keyUp(Keys.DOWN).perform();
		
		//Test step 7: Select CPT codes
		String arr[] = {"CPT-99091","CPT-99453","CPT-99454","CPT-99474"};
		
		for(String CPT: arr)
		{
			WebElement Select_Checkbox = driver.findElement(By.xpath("//div[contains(@class,'MuiBox-root')]//p[text()='"+CPT+"']/following::input[@type='checkbox'][1]"));
			Select_Checkbox.click();
		}
		
		//Verify the results
		 WebElement Recurring_Reimbursement = driver.findElement(By.xpath("//p[contains(@class, 'MuiTypography-root')][4]"));
		 String Total_Recurring_Reimbursement = Recurring_Reimbursement.getText().replaceAll("[^\\d]", "");
		 System.out.println("Total_Recurring_Reimbursement: "+ Total_Recurring_Reimbursement);
		 
		 if(Total_Recurring_Reimbursement.equals("110700"))
		 {
			 System.out.println("Total Recurring Reimbursement for all Patients Per Month:"+ Total_Recurring_Reimbursement);
			 System.out.println("Test Passed");
			 driver.quit();
		 }
		 else
		 {
			 System.out.println("Test Failed");
		 }
		// As per the given test case by you, if we are providing sliding scale value 560 instaed of 820 we are not getting the correct recurring reimbursement value . Kindly           requesting you to please check on this
	}

}
