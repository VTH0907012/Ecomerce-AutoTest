package pages.Client;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.BasePage;

public class ProductDetailPage extends BasePage {
    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public void addToCart(int quantity) {
        // Định vị nút tăng
        WebElement increaseBtn = driver.findElement(By.xpath("//button[normalize-space()='+']"));

        // Click nút tăng số lượng (quantity - 1) lần
        for (int i = 1; i < quantity; i++) {
            increaseBtn.click();
        }

        // Click nút "Thêm vào giỏ"
        WebElement addToCartBtn = driver.findElement(By.xpath("//button[contains(text(),'Thêm vào giỏ hàng')]"));
        addToCartBtn.click();

    }

    By btnDetailCart = By.xpath("//button[contains(text(),'Chi tiết giỏ hàng')]");

    public CartPage navCart() {
        click(btnDetailCart);
        return new CartPage(driver);
    }
}
