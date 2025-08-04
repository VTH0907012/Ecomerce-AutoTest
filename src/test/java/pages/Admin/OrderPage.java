package pages.Admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BasePage;

public class OrderPage extends BasePage {
    public OrderPage(WebDriver driver) {
        super(driver);
    }

    // Locators
    private final By dropdownStatusOrder = By.id("status");
    private final By btnUpdateStatusOrder = By.xpath("//button[contains(text(),'Cập nhật')]");
    private final By inputSearchOrder = By.xpath("//input[@placeholder='Tìm kiếm đơn hàng...']");

    // Edit Order Status
    public void editStatusOrder(String idOrder, String status) throws InterruptedException {
        By editButton = By.xpath("//tbody/tr[td[normalize-space()='" + idOrder + "']]//button[@title='Cập nhật trạng thái']");
        click(editButton);
        Thread.sleep(500); // cho animation hiển thị modal (tối thiểu)
        selectByVisibleText(dropdownStatusOrder, status);
        click(btnUpdateStatusOrder);
    }

    // Search order & check if found
    public boolean searchOrder(String id) {
        String idShort = id.substring(1, 9);
        sendKeys(inputSearchOrder, idShort);
        By resultRow = By.xpath("//td/div[contains(normalize-space(), '" + idShort + "')]");
        return isDisplayed(resultRow);
    }

    // Support for test logic
    public void clearSearchInput() {
        clear(inputSearchOrder);
    }

    // Kiểm tra trạng thái đã cập nhật đúng chưa
    public boolean checkStatus(String id, String status) {
        String idShort =  id.substring(1, 9);
        By statusCell = By.xpath(String.format("//tr[td/div[text()='%s']]/td/span[text()='%s']", idShort, status));
        return isDisplayed(statusCell);
    }

    // Kiểm tra khi không tìm thấy đơn hàng
    public boolean isOrderNotFoundDisplayed() {
        By notFoundMessage = By.xpath("//td[contains(text(),'Không tìm thấy') or contains(text(),'không có đơn')]");
        return isDisplayed(notFoundMessage);
    }
}
