package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.Client.CheckoutPage;
import utils.BasePage;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    By emailField = By.xpath("//input[@id='email']");
    By passwordField = By.xpath("//input[@id='password']");
    By loginButton = By.xpath("//button[contains(text(),'Đăng nhập')]");
    By loginLink = By.xpath("//a[@class='hidden sm:flex flex-col items-center text-xs text-gray-700 hover:text-blue-600']");

    public void navLogin() {
        click(loginLink);
    }

    public CheckoutPage login(String email, String password) throws InterruptedException {
        navLogin();
        Thread.sleep(1000);
        sendKeys(emailField, email);
        Thread.sleep(1000);
        sendKeys(passwordField, password);
        Thread.sleep(1000);
        click(loginButton);
        Thread.sleep(2000);
        return new CheckoutPage(driver);
    }

    By notiError = By.xpath("//div[@id='_rht_toaster']");
    public boolean isErrorDisplayed() {
        return isDisplayed(notiError); // ví dụ ID
    }
}
