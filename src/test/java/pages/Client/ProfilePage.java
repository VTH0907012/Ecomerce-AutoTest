package pages.Client;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.BasePage;

import java.util.List;

public class ProfilePage extends BasePage {
    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    By iconAccount = By.xpath("//button[@class='flex flex-col items-center text-xs text-gray-700 hover:text-blue-600 group']");
    By linkProfile = By.xpath("//a[contains(text(),'Tài khoản của tôi')]");
    By optionInfo = By.xpath("//button[contains(text(),'Thông tin tài khoản')]");

    public void navigateToProfile() throws InterruptedException {
        click(iconAccount);
        Thread.sleep(1000); // nên thay bằng waitVisible nếu có
        click(linkProfile);
        Thread.sleep(1000);

    }

    By inputName = By.xpath("//input[@name='name']");
    By inputEmail = By.xpath("//input[@name='email']");
    By btnSaveChange = By.xpath("//button[contains(text(),'Lưu thay đổi')]");

    public void changeInfo(String name, String email) throws InterruptedException {
        click(optionInfo);
        Thread.sleep(1000);

        if (name != null) {

            sendKeys(inputName, name.isEmpty() ? Keys.SPACE + "" + Keys.BACK_SPACE : name);
            //sendKeys(inputName, name);
        }
        Thread.sleep(1000);
        if (email != null) {
            sendKeys(inputEmail, email.isEmpty() ? Keys.SPACE + "" + Keys.BACK_SPACE : email);
            //sendKeys(inputEmail, email);
        }
        Thread.sleep(1000);

        click(btnSaveChange);

    }

    public Boolean checkInfo(String name, String email) throws InterruptedException {

        String name_curent = getValue(inputName);
        Thread.sleep(1000);
        String email_curent = getValue(inputEmail);
        Thread.sleep(1000);
        if (name_curent.equals(name) && email_curent.equals(email)) {
            return true;
        }
        return false;
    }


    By inputOldPass = By.xpath("//input[@placeholder='Mật khẩu hiện tại']");
    By inputNewPass = By.xpath("//input[@placeholder='Mật khẩu mới']");
    By btnSaveChangePass = By.cssSelector("button[class='px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700']");
    By linkChangePass = By.xpath("//button[contains(text(),'Đổi mật khẩu')]");

    public void changePass(String password_current, String password_new) throws InterruptedException {
        click(optionInfo);
        Thread.sleep(1000);

        click(linkChangePass);
        Thread.sleep(1000);

        if (password_current != null) {
            sendKeys(inputOldPass, password_current);
        }

        Thread.sleep(1000);
        if (password_new != null) {
            sendKeys(inputNewPass, password_new);
        }
        Thread.sleep(1000);
        click(btnSaveChangePass);
        Thread.sleep(1000);
    }

    By linkLogout = By.xpath("//button[contains(text(),'Đăng xuất')]");

    public void logut() throws InterruptedException {
        click(iconAccount);
        Thread.sleep(1000); // nên thay bằng waitVisible nếu có
        click(linkLogout);
    }

    By optionOrderHistory = By.xpath("//button[contains(text(),'Lịch sử đơn hàng')]");
    By btnConfirmCancel = By.xpath("//button[normalize-space()='Xoá']");

    public void navigateToHistoryOrder() throws InterruptedException {

        click(optionOrderHistory);
    }

    public void cancelOrder(String codeOder) throws InterruptedException {
        //click(optionOrderHistory);

        Thread.sleep(1000);
        By btnCancel = By.xpath("//tr[td[contains(text(), '" + codeOder + "')]]//button[contains(text(),'Hủy đơn')]");
        //tr[td[contains(text(), "68876b11691676df3d794565")]]//button[contains(text(), "Hủy đơn")]
        Thread.sleep(1000);
        click(btnCancel);
        Thread.sleep(1000);
        click(btnConfirmCancel);
    }

    public String checkStatusOrder(String codeOrder) throws InterruptedException {
        By statusOrder = By.xpath("//tr[td[contains(text(), '" + codeOrder + "')]]/td[3]");
        Thread.sleep(1000);
        return getText(statusOrder);
    }


    public boolean isNameErrorDisplayed() {
        return isDisplayed(By.xpath("//p[contains(text(), 'Họ tên không được để trống')]"));
    }

    public boolean isEmailErrorDisplayed() {
        return isDisplayed(By.xpath("//p[contains(text(),'Email không được để trống')]"));
    }

    public boolean isEmailInvalidDisplayed() {
        return isDisplayed(By.xpath("//p[contains(text(),'Email không hợp lệ')]"));
    }


    public boolean isMissingCurrentPasswordMessageDisplayed() {
        return isDisplayed(By.xpath("//p[contains(text(),'Vui lòng nhập mật khẩu hiện tại')]"));
    }

    public boolean isMissingNewPasswordMessageDisplayed() {
        return isDisplayed(By.xpath("//p[contains(text(),'Vui lòng nhập mật khẩu mới')]"));
    }

    public boolean isIncorrectPasswordMessageDisplayed() {
        return isDisplayed(By.xpath("//div[@class='go2072408551']"));
    }

    By btnCancel = By.xpath("//button[contains(text(),'Huỷ')]");

    public void closeModal() {
        click(btnCancel);
    }

    public boolean searchOrder(String idOrder) {
        By order = By.xpath("//td[normalize-space()='" + idOrder + "']");
        return isDisplayed(order);
    }

}
