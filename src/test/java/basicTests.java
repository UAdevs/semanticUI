/**
 * Created by Dev on 14.12.2017.
 */
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;
import  conf.*;
import java.util.*;

import org.openqa.selenium.support.ui.WebDriverWait;

public class basicTests {
    private static WebDriver driver;
    private static String currentUrl;
    private static  UrlBuilder urlBuilder = new UrlBuilder();
    private static Env currentEnv = Env.STAGE;
    private static ConsoleLogger logger = new ConsoleLogger();

    private boolean subCaseResult(WebElement element,String tagValue){
        String text = element.getText();
        boolean subResult = false;
        if(text.equals(tagValue)){
            logger.log("Pass");
            subResult = true;
        }
        else if (!text.equals(tagValue)){
            logger.log("Fail");
        }
        return subResult;
    }

    private boolean containsWarning(WebElement element){
        List<WebElement> tdList = element.findElements(By.tagName("td"));
        boolean result = false;
        for (WebElement td:tdList ) {
            List<WebElement> iList = td.findElements(By.cssSelector("i[class='attention icon']"));
            if(iList.size()>0){result = true;}
        }
        if(result){logger.log("Pass");}
        if(!result){logger.log("Fail");}
        return result;
    }

    private static void sleep(int mSec) {
        try {
            Thread.sleep(mSec);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @BeforeClass
    public static void setup() {
        //-------------setting up Browser configuration ------------------------
        // System.setProperty("webdriver.chrome.driver", "Install/Dev/chromedriver_win32");
        driver = new WebDriverManager().getWebDriver(SupportedBrowsers.CHROME);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void tableTest(){
        currentUrl = urlBuilder.getUrl(currentEnv,Page.TABLE);
        driver.get(currentUrl);
        logger.log("--------------Test: table test-------------------");
        if(driver.getTitle().equals("Table | Semantic UI")) {
            logger.log("page '" + driver.getTitle() + "'  was opened");
        }
        List<WebElement> trListPositiveNegative =  driver.findElements(By.xpath("//h4[text()='Positive / Negative']/../table[@class='ui celled table']/tbody/tr"));
        boolean case1 = false;
        boolean case2_1 = false;
        boolean case2_2 = false;
        boolean case3_1 = false;
        boolean case3_2 = false;
        boolean result = false;
        for (WebElement tr : trListPositiveNegative) {
            WebElement cell = tr.findElement(By.xpath("td[1]"));
            if (cell.getText().equals("Jimmy")) {
                logger.log("Case: Jimmy - Approved");
                case1 = subCaseResult(tr.findElement(By.xpath("td[2]")), "Approved");
            }
        }
        for (WebElement tr : trListPositiveNegative) {
            WebElement cell = tr.findElement(By.xpath("td[1]"));
            if (cell.getText().equals("No Name Specified")) {
                logger.log("Case: No Name Specified Status - Unknown");
                case2_1 = subCaseResult(tr.findElement(By.xpath("td[2]")), "Unknown");
                logger.log("Case: No Name Specified Notes - None");
                case2_2 = subCaseResult(tr.findElement(By.xpath("td[3]")), "None");
            }
        }
        for (WebElement tr : trListPositiveNegative) {
            WebElement cell = tr.findElement(By.xpath("td[1]"));
            if (cell.getText().equals("Jill")) {
                logger.log("Case: Jill Status - Unknown");
                case2_1 = subCaseResult(tr.findElement(By.xpath("td[2]")), "Unknown");
                logger.log("Case: Jill Notes - None");
                case2_2 = subCaseResult(tr.findElement(By.xpath("td[3]")), "None");
            }
        }

        List<WebElement> trListWarning =  driver.findElements(By.xpath("//h4[text()='Warning']/../table[@class='ui celled table']/tbody/tr"));

        for (WebElement tr : trListWarning) {
            WebElement cell = tr.findElement(By.xpath("td[1]"));
            if (cell.getText().equals("Jimmy")) {
                logger.log("Case: Jimmy contains Warning");
                case3_1 = containsWarning(tr);
            }
            if (cell.getText().equals("Jamie")) {
                logger.log("Case: Jamie contains Warning");
                case3_2 = containsWarning(tr);
            }
        }
        Assert.assertEquals(true,case1 & case2_1 & case2_2 & case3_1 & case3_2);
    }

    @Test
    public void dropdownTest() {
        logger.log("--------------Test: dropdownTest-------------------");
        currentUrl = urlBuilder.getUrl(currentEnv, Page.DROPDOWN);
        driver.get(currentUrl);
        if (driver.getTitle().equals("Dropdown | Semantic UI")) {
            logger.log("page '" + driver.getTitle() + "'  was opened");
            sleep(2000);
            //----- first Gender dropdown ----------------------------------------------------------------------
            String firstDropdownText = "Female";
            boolean firstDropdownResult = false;
            WebElement genderParentDropDown = driver.findElement(By.xpath("//input[@name='gender']/.."));
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,450)", "");
            genderParentDropDown.click();
            WebElement item = genderParentDropDown.findElement(By.xpath("./div[@class='menu transition visible']" +
                    "/div[@data-value='0']"));
            item.click();
            WebElement selectedItem = genderParentDropDown.findElement(By.xpath("./div[@class='text']"));
            // WebElement selected = genderParentDropDown.findElement(By.xpath("./div[@class='text']/div[@class='item active selected']"));another possible selection
            if (selectedItem.getText().equals(firstDropdownText)) {
                logger.log("'" + firstDropdownText + "'" + " item is selected");
                firstDropdownResult = true;
            }
            //----- second Gender dropdown ----------------------------------------------------------------------
            String secondDropdownText = "Male";
            boolean secondDropdownResult = false;
            WebElement genderDropDown = driver.findElement(By.xpath("//div[@class='another dropdown example']/div[@class='ui dropdown selection']")); //div[@class='ui dropdown selection
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,150)", "");
            genderDropDown.click();
            WebElement genderItem = driver.findElement(By.xpath("//div[@class='another dropdown example']" +
                    "/div/div[@class='menu transition visible']/div[@data-value='1']"));
            genderItem.click();
            WebElement selectedItem2 = driver.findElement(By.xpath("//div[@class='another dropdown example visible']/" +
                    "div[@class='ui dropdown selection']/div[@class='text']"));
            if (selectedItem2.getText().equals(secondDropdownText)) {
                logger.log("'" + secondDropdownText + "'" + " item is selected");
                secondDropdownResult = true;
            }
            //----- select friend dropdown ----------------------------------------------------------------------
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,150)", "");
            String friendDropdownText = "Christian";
            boolean friendDropdownResult = false;
            WebElement friendDropdown = driver.findElement(By.cssSelector("div[class='ui fluid selection dropdown']"));
            friendDropdown.click();
            WebElement friendItem = driver.findElement(By.xpath("//div[@class='ui fluid selection dropdown active visible']" +
                    "/div[@class='menu transition visible']/div[@data-value='christian']"));
            friendItem.click();

            WebElement friendItemSelected = driver.findElement(By.xpath("//div[@class='another dropdown example']" +
                    "/div[@class='ui fluid selection dropdown']/div[@class='text']"));
            if (friendItemSelected.getText().equals(friendDropdownText)) {
                logger.log("'" + friendDropdownText + "'" + " item is selected");
                friendDropdownResult = true;
            }
            Assert.assertEquals(true, firstDropdownResult & secondDropdownResult & friendDropdownResult);
        }
    }

    @Test
    public void checkBoxTest(){
        //--------------------------------------preconditions-----------------------------------------------------
        logger.log("--------------Test: checkBoxTest-------------------");
        currentUrl = urlBuilder.getUrl(currentEnv, Page.CHECKBOX);
        driver.get(currentUrl);
        //-------------------------------------- checkBox --------------------------------------------------------
        boolean checkBoxChecked = false;
        WebElement checkBox = driver.findElement(By.xpath("//label[text()='Make my profile visible']/.."));
        String classValueUnchecked = checkBox.getAttribute("class");
        checkBox.click();
        if(!classValueUnchecked.equals(checkBox.getAttribute("class"))){
            checkBoxChecked = true;
            logger.log(" 'Make my profile visible' checkBox value was changed");
        }
        //-------------------------------------- horizontal radio group------------------------------------------------
        boolean horizontRadioChecked = false;
        WebElement hRadio = driver.findElement(By.xpath("//div[@class='inline fields']" +
                "/div[@class='field']/div/label[text()='Once a day']/.."));  //div[@class='ui radio checkbox']

        String classValueUncheckedhRadio = hRadio.getAttribute("class");
        hRadio.click();
        if(!classValueUncheckedhRadio.equals(hRadio.getAttribute("class"))){
            horizontRadioChecked = true;
            logger.log(" 'Once a day' horizontal radio button was checked");
        }
        //-------------------------------------- vertical radio group----------------------------------------------
        boolean verticalRadioChecked = false;
        WebElement vRadio = driver.findElement(By.xpath("//div[@class='grouped fields']" +
                "/div[@class='field']/div/label[text()='Once a day']/..")); //div[@class='ui radio checkbox']
        String classValueUncheckedvRadio = hRadio.getAttribute("class");
        vRadio.click();
        if(!classValueUncheckedhRadio.equals(hRadio.getAttribute("class"))){
            verticalRadioChecked = true;
            logger.log(" 'Once a day' vertical radio button was checked");
        }
        //-------------------------------------- Accept terms and conditions slider --------------------------------
        boolean acceptSliderValue = false;
        WebElement acceptSlider = driver.findElement(By.xpath("//label[text()='Accept terms and conditions']/.."));
        String classValueUncheckedSlider = acceptSlider.getAttribute("class");
        acceptSlider.click();
        if(!classValueUncheckedSlider.equals(acceptSlider.getAttribute("class"))){
            acceptSliderValue = true;
            logger.log(" 'Accept terms and conditions' slider was checked");
        }
        //------------------------- Outbound Throughput slider group -----------------------------------------------
        boolean sliderGroupValue = false;
        WebElement sliderGroupItem = driver.findElement(By.xpath("//div[@class='grouped fields']" +
                "/div[@class='field']/div/label[text()='10mbps max']/.."));
        String classValuesliderGroup = sliderGroupItem.getAttribute("class");
        sliderGroupItem.click();
        if(!classValuesliderGroup.equals(sliderGroupItem.getAttribute("class"))){
            sliderGroupValue = true;
            logger.log(" 'Outbound Throughput slider group' was checked");
        }
        //------------------------- Subscribe to weekly newsletter toggle---------------------------------------------------
        boolean toggleValue = false;
        WebElement subscribeToggle = driver.findElement(By.xpath("//label[text()='Subscribe to weekly newsletter']/.."));
        String clasSubscribeToggle = subscribeToggle.getAttribute("class");
        subscribeToggle.click();
        if(!clasSubscribeToggle.equals(subscribeToggle.getAttribute("class"))){
            toggleValue = true;
            logger.log(" 'Subscribe to weekly newsletter toggle' was checked");
        }
        Assert.assertEquals(true, checkBoxChecked & horizontRadioChecked & verticalRadioChecked &
                acceptSliderValue & sliderGroupValue & toggleValue);
    }
    @AfterClass
    public void tearDown() {
             driver.quit();
    }
}
