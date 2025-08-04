package pages.Client;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.Admin.DashboardPage;
import utils.BasePage;

public class CheckoutPage extends BasePage {
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    //Locator Checkout
    By checkoutTitle = By.xpath("//h1[normalize-space()='Thanh toán']");


    //Chuyển hướng đến Dashboard
    By btnAccount = By.cssSelector("button[class='flex flex-col items-center text-xs text-gray-700 hover:text-blue-600 group']");
    By linkAdmin = By.xpath("//a[contains(text(),'Trang quản trị')]");

    public DashboardPage navAdmimPage() throws InterruptedException {
        Thread.sleep(500);
        click(btnAccount);
        Thread.sleep(2000);
        click(linkAdmin);
        Thread.sleep(2000);
        return new DashboardPage(driver);
    }


    public boolean isCheckoutPageDisplayed() {
        return isDisplayed(checkoutTitle);
    }

    By inputName = By.xpath("//input[@name='fullName']");
    By inputPhone = By.xpath("//input[@name='phone']");
    By inputAddress = By.xpath("//input[@name='address']");
    By inputNote = By.xpath("//textarea[@placeholder='Ví dụ: Giao hàng giờ hành chính...']");
    By btnPayment = By.xpath("//button[contains(text(),'Đặt hàng ngay')]");

    public void fillShippingDetails(String name, String phone, String address, String note, String methodValue) throws InterruptedException {

        sendKeys(inputName, name);
        Thread.sleep(1000);
        sendKeys(inputPhone, phone);
        Thread.sleep(1000);
        sendKeys(inputAddress, address);
        Thread.sleep(1000);
        sendKeys(inputNote, note);
        Thread.sleep(1000);

        //Method payment
        WebElement paymentRadio = driver.findElement(
                By.cssSelector("input[name='paymentMethod'][value='" + methodValue + "']")
        );
        if (!paymentRadio.isSelected()) {
            paymentRadio.click();
        }

    }

    public OrderSuccessPage placeOrder() {
        click(btnPayment);
        return new OrderSuccessPage(driver);
    }

    By iconAccount = By.xpath("//button[@class='flex flex-col items-center text-xs text-gray-700 hover:text-blue-600 group']");
    By linkLogout = By.xpath("//button[contains(text(),'Đăng xuất')]");

    public void logout() throws InterruptedException {
        click(iconAccount);
        Thread.sleep(1000); // nên thay bằng waitVisible nếu có
        click(linkLogout);
    }


}
