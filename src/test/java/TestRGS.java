import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TestRGS {

    private WebDriverWait wait;
    private WebDriver driver;

    @Before
    public void before(){

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10, 1000);

        String baseUrl = "http://www.rgs.ru";
        driver.get(baseUrl);

    }
    @Test
    public void testRGS(){

        //Выбор пункта меню "Компаниям"
        String baseMenuXpath = "//a[text() = 'Компаниям' and contains(@href, 'companies')]";
        WebElement baseMenu = driver.findElement(By.xpath(baseMenuXpath));
        waitUtilElementToBeClickable(baseMenu);
        baseMenu.click();

        //Проверка нажатия "Компания"
        String activeBaseMenuXpath = "//a[text() = 'Компаниям' and contains(@class, 'active')]";
        WebElement activeBaseMenu = driver.findElement(By.xpath(activeBaseMenuXpath));
        waitUtilElementToBeVisible(activeBaseMenu);
        Assert.assertTrue("Клик по кнопке \"Компаниям\" не осуществлен", activeBaseMenu.getAttribute("class").contains("active"));

        //Переключение на ifame всплывающего окна
        String searchIframeXpath = "//iframe[@id = 'fl-616371']";
        By searchIframe = By.xpath(searchIframeXpath);
        driver.switchTo().frame(driver.findElement(searchIframe));

        //Закрытие всплывающего окна
        String closeSubscriptionWindowsXpath = "//div[@data-fl-track = 'click-close-login']";
        WebElement closeSubscriptionWindows = driver.findElement(By.xpath(closeSubscriptionWindowsXpath));
        waitUtilElementToBeClickable(closeSubscriptionWindows);
        closeSubscriptionWindows.click();

        //Переключение на основной iframe
        driver.switchTo().defaultContent();

        //Выбор пункта подменю "Здоровье"
        String subMenuHealthXpath = "//span[text() = 'Здоровье' and contains(@class, 'padding')]";
        WebElement subMenuHealth = driver.findElement(By.xpath(subMenuHealthXpath));
        waitUtilElementToBeClickable(subMenuHealth);
        subMenuHealth.click();

        //Проверка нажатия "Здоровье"
        WebElement activeSubMenuHealth = subMenuHealth.findElement(By.xpath("./parent::li"));
        waitUtilElementToBeVisible(activeSubMenuHealth);
        Assert.assertTrue("Клик по кнопке \"Здоровье\" не осуществлен", activeSubMenuHealth.getAttribute("class").contains("active"));

        //Выбор пункта подменю "Добровольное медицинское страхование"
        String subMenuInsuranceXpath = "//a[text() = 'Добровольное медицинское страхование']";
        WebElement sumMenuInsurance = driver.findElement(By.xpath(subMenuInsuranceXpath));
        waitUtilElementToBeClickable(sumMenuInsurance);
        sumMenuInsurance.click();

        //Проверка наличия заголовка
        String titleHealthXpath = "//h1[contains(text(), 'Добровольное медицинское страхование')]";
        WebElement titleHealth = driver.findElement(By.xpath(titleHealthXpath));
        waitUtilElementToBeVisible(titleHealth);
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Добровольное медицинское страхование", titleHealth.getText());

        //Закрытие сообщения Cookie
        String cookieMessageXpath = "//button[@class = 'btn--text' and contains(text(), 'Хорошо')]";
        WebElement cookieMessage = driver.findElement(By.xpath(cookieMessageXpath));
        waitUtilElementToBeClickable(cookieMessage);
        cookieMessage.click();

        //Нажатие на кнопку "Отправить заявку"
        String sendRequestXpath = "//span[text() = 'Отправить заявку']";
        WebElement sendRequest = driver.findElement(By.xpath(sendRequestXpath));
        waitUtilElementToBeClickable(sendRequest);
        sendRequest.click();

        //Проверка наличия заголовка
        String titlePolicyXpath = "//h2[contains(@class, 'title--h2') and contains(text(), 'Оперативно перезвоним')]";
        WebElement titlePolicy = driver.findElement(By.xpath(titlePolicyXpath));
        waitUtilElementToBeVisible(titlePolicy);
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Оперативно перезвоним\n" +
                        "для оформления полиса", titlePolicy.getText());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // заполнить поля данными
        String fieldXPath = "//input[@name='%s']";
        String addressXpath = "//input[@class='vue-dadata__input']";
        fillInputField(driver.findElement(By.xpath(String.format(fieldXPath, "userName"))), "qwertyqwerty");
        fillInputFieldForPhone(driver.findElement(By.xpath(String.format(fieldXPath, "userTel"))), "0000000000");
        fillInputField(driver.findElement(By.xpath(String.format(fieldXPath, "userEmail"))), "qwertyqwerty");
        fillInputField(driver.findElement(By.xpath(addressXpath)), "qwertyqwerty");

        
        //Клик по checkBox
        String checkBoxXpath = "//div[contains(@class, 'form__checkbox')]";
        WebElement checkBox = driver.findElement(By.xpath(checkBoxXpath));
        scrollToElementJs(checkBox);
        waitUtilElementToBeVisible(checkBox);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelector('label.checkbox',':before').click();");
        WebElement checkBoxChecked = checkBox.findElement(By.xpath("./label[contains(@class, 'checkbox')]"));
        waitUtilElementToBeVisible(checkBoxChecked);
        Assert.assertTrue(checkBoxChecked.getAttribute("class").contains("is-checked"));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Нажатие по кнопке "Свяжитесь со мной"
        String contactMeXpath = "//button[contains(@class, 'form__button-submit')]";
        WebElement contactMe = driver.findElement(By.xpath(contactMeXpath));
        waitUtilElementToBeClickable(contactMe);
        contactMe.click();

        //Проверка наличия сообщения об ошибке
        String errEmailXpath = "//span[text() = 'Введите корректный адрес электронной почты']";
        WebElement errEmail = driver.findElement(By.xpath(errEmailXpath));
        waitUtilElementToBeVisible(errEmail);
        Assert.assertEquals("Проверка ошибки у поля не была пройдена",
                "Введите корректный адрес электронной почты", errEmail.getText());

        String errAddressXpath = "//span[text() = 'Поле обязательно']";
        WebElement errAddress = driver.findElement(By.xpath(errAddressXpath));
        waitUtilElementToBeVisible(errAddress);
        Assert.assertEquals("Проверка ошибки у поля не была пройдена",
                "Поле обязательно", errAddress.getText());
    }

    @After
    public void after(){
        driver.quit();
    }

    private void scrollToElementJs(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void waitUtilElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void waitUtilElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void waitUtilElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    private void fillInputField(WebElement element, String value) {
        scrollToElementJs(element);
        waitUtilElementToBeClickable(element);
        element.click();
        element.clear();
        element.sendKeys(value);
        boolean checkFlag = wait.until(ExpectedConditions.attributeContains(element, "value", value));
        Assert.assertTrue("Поле было заполнено некорректно", checkFlag);
    }

    private void fillInputFieldForPhone(WebElement element, String value) {
        scrollToElementJs(element);
        waitUtilElementToBeClickable(element);
        element.click();
        element.clear();
        element.sendKeys(value);
        String val = element.getAttribute("value").replaceAll("\\D", "");
        Assert.assertEquals("Поле было заполнено некорректно", val, "7" + value);
    }

}