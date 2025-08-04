package pages.Client;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BasePage;

public class CartPage extends BasePage {
    public CartPage(WebDriver driver) {
        super(driver);
    }

    By btnProcesdPayment = By.xpath("//button[contains(text(),'Tiến hành thanh toán')]");

    public CheckoutPage navPayment() {
        click(btnProcesdPayment);
        return new CheckoutPage(driver);
    }

    public boolean isProductInCart(String productName) {
        By product = By.xpath("//h3[normalize-space()='" + productName + "']");
        return isDisplayed(product);
    }

    public void increaseQuantity(String name) {
        By btnIncrease = By.xpath("//h3[contains(text(), '" + name + "')]     /ancestor::div[contains(@class, 'grid-cols-12')]     //button[contains(text(), '+')]");
        click(btnIncrease);
    }

    public void decreaseQuantity(String name) {
        By btndecrease = By.xpath("//h3[contains(text(), '" + name + "')]     /ancestor::div[contains(@class, 'grid-cols-12')]     //button[contains(text(), '-')]");
        click(btndecrease);

    }

    public String getCurrentQuantity(String name) {
        By textQuantity = By.xpath("//h3[contains(text(), '" + name + "')]     /ancestor::div[contains(@class, 'grid-cols-12')]     //span[contains(@class, 'min-w-[30px]')]");
        return getText(textQuantity);
    }

    public void removeProductFromCart(String name) {
        By btnDelete = By.xpath("//div[h3[contains(text(), '" + name + "')]]//button[contains(@class, 'text-red-500')]");
        click(btnDelete);

    }

}
