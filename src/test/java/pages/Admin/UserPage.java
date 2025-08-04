package pages.Admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utils.BasePage;

public class UserPage extends BasePage {
    public UserPage(WebDriver driver) {
        super(driver);
    }

    //Add User
    //Locator
    By btnAddUser = By.xpath("//button[contains(text(),'Thêm mới')]");

    public void openAddUser() {
        click(btnAddUser);
    }

    By inputName = By.xpath("//input[@placeholder='Nhập tên người dùng']");
    By inputEmail = By.xpath("//input[@placeholder='Nhập email']");
    By inputPasword = By.xpath("//input[@placeholder='Nhập mật khẩu']");
    By btnSaveUser = By.xpath("//button[@class='px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition flex items-center']");

    public void addUser(String name, String email, String password) throws InterruptedException {
        openAddUser();
        Thread.sleep(1000);
        if (name != null) {
            sendKeys(inputName, name);
            Thread.sleep(500);
        }
        if (email != null) {
            sendKeys(inputEmail, email);
            Thread.sleep(500);
        }
        if (password != null) {
            sendKeys(inputPasword, password);
            Thread.sleep(500);
        }
        click(btnSaveUser);
    }

    By inputSearchUser = By.xpath("//input[@placeholder='Tìm kiếm người dùng...']");

    public boolean searchUser(String name) {
        sendKeys(inputSearchUser, name);
        By table_td_User = By.xpath("//td[@class='px-6 py-4 whitespace-nowrap']/div[text()='" + name + "']");
        return isDisplayed(table_td_User);
    }


    //Delete User
    public void deteleUser(String name) throws InterruptedException {
        Thread.sleep(1000);
        By btnIdDelete = By.xpath("//tbody/tr[td[normalize-space()='" + name + "']]//button[@title='Xóa']");
        click(btnIdDelete);
        Thread.sleep(1000);
        By btnDelete = By.xpath("//button[normalize-space()='Xoá']");
        click(btnDelete);
    }

    //Change Status

    public Boolean blockUser(String name) throws InterruptedException {
        if (name != null && name != "") {

            sendKeys(inputSearchUser, name);
            Thread.sleep(1000);

            By blockButton = By.xpath("//tbody/tr[td[normalize-space()='" + name + "']]//button[@title='Khóa']");
            click(blockButton);
            Thread.sleep(1000);

            String xpath = String.format("//tr[td/div[text()='%s']]/td/span[text()='%s']", name, "Đã khóa");
            By locatorStatusUser = By.xpath(xpath);
            String statusUser = getText(locatorStatusUser);

            if (statusUser.equals("Đã khóa")) {
                return true;
            }
            return false;
        }
        return false;


    }

    public Boolean assetAdmin(String name) throws InterruptedException {
        if (name != null && name != "") {

            sendKeys(inputSearchUser, name);
            Thread.sleep(1000);

            By blockButton = By.xpath("//tbody/tr[td[normalize-space()='" + name + "']]//button[@title='Cấp quyền admin']");
            click(blockButton);
            Thread.sleep(1000);

            String xpath = String.format("//tr[td/div[text()='%s']]/td/span[text()='%s']", name, "Admin");
            By locatorRoleUser = By.xpath(xpath);
            String roleUser = getText(locatorRoleUser);

            if (roleUser.equals("Admin")) {
                return true;
            }
            return false;

        }
        return false;
    }


    public boolean isErrorDisplayed(String errorType) {
        By locator;
        switch (errorType) {
            case "missing_name":
                locator = By.xpath("//p[contains(text(),'Tên người dùng không được để trống.')]");
                break;
            case "missing_email":
                locator = By.xpath("//p[contains(text(),'Email không được để trống.')]");
                break;
            case "missing_password":
                locator = By.xpath("//p[contains(text(),'Mật khẩu không được để trống.')]");
                break;
            case "invalid_email":
                locator = By.xpath("//p[contains(text(),'Email không đúng định dạng.')]");
                break;
            default:
                return false;
        }
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    By btnCLose = By.xpath("//button[@class='text-gray-500 hover:text-gray-700']");

    public void closeModal() {
        click(btnCLose);
    }
}
