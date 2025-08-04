package pages.Client;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BasePage;

public class RegisterPage extends BasePage {

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    By titleRegister = By.xpath("//h2[contains(text(),'Tạo tài khoản mới')]");
    By inputName = By.xpath("//input[@id='name']");
    By inputEmail = By.xpath("//input[@id='email']");
    By inputPassword = By.xpath("//input[@id='password']");
    By btnRegister = By.xpath("//button[contains(text(),'Đăng ký')]");

    public void Register(String name, String email, String password) throws InterruptedException {
        sendKeys(inputName, name);
        Thread.sleep(1000);
        sendKeys(inputEmail, email);
        Thread.sleep(1000);
        sendKeys(inputPassword, password);
        Thread.sleep(1000);
        click(btnRegister);
    }
    public boolean isNameErrorDisplayed() {
        return driver.findElement(By.xpath("//p[contains(text(),'Họ và tên không được để trống')]")).isDisplayed();
    }

    public boolean isEmailErrorDisplayed() {
        return driver.findElement(By.xpath("//p[contains(text(),'Email không được để trống')]")).isDisplayed();
    }

    public boolean isEmailInvalidErrorDisplayed() {
        return driver.findElement(By.xpath("//p[contains(text(),'Email không hợp lệ')]")).isDisplayed();
    }

    public boolean isPasswordErrorDisplayed() {
        return driver.findElement(By.xpath("//p[contains(text(),'Mật khẩu không được để trống')]")).isDisplayed();
    }


}
