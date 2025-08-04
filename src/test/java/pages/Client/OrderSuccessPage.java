package pages.Client;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BasePage;

public class OrderSuccessPage extends BasePage {
    public OrderSuccessPage(WebDriver driver) {
        super(driver);
    }

    By titleOrderSuccess = By.xpath("//h1[contains(text(),'Đặt hàng thành công!')]");
    By codeOrder = By.xpath("//span[text()='Mã đơn hàng:']/parent::p");

    public String codeOrderSuccess() {
        String fullText = getText(codeOrder);
        return fullText.replace("Mã đơn hàng:", "").trim();
    }
}
