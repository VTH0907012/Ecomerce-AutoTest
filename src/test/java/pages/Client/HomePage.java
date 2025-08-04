package pages.Client;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BasePage;

import java.time.Duration;

public class HomePage extends BasePage {

    // Constructor
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Elements
    By homeLink = By.xpath("//a[@class='text-gray-800 hover:text-blue-600 font-medium px-3 py-2 transition'][contains(text(),'Trang chủ')]");
    String productXpathTemplate = "//h2[text()='%s']/ancestor::div[contains(@class,'product-item')]//a[text()='Xem chi tiết']";

    // Navigate to Home
    public void navigateToHome() {
        click(homeLink);
    }

    // Navigate to Home
    By loginLink = By.xpath("//a[@class='hidden sm:flex flex-col items-center text-xs text-gray-700 hover:text-blue-600']");
    By registerLink = By.xpath("//a[contains(text(),'Đăng ký ngay')]");

    public void navigateRegister() throws InterruptedException {
        click(loginLink);
        Thread.sleep(500);
        click(registerLink);
    }

    // View product by name
    public ProductDetailPage viewProduct(String productName) {
        // Tìm phần tử h3 chứa tên sản phẩm cụ thể
        WebElement productTitle = driver.findElement(
                By.xpath("//section[contains(@class,'py-16 bg-white relative')]//div[contains(@class,'swiper-slide')]//h3[contains(text(),'" + productName + "')]")
        );

        // Tìm phần tử cha của product title để hover
        WebElement productCard = productTitle.findElement(By.xpath("./ancestor::div[contains(@class,'swiper-slide')]"));

        // Hover vào product card để hiển thị icon con mắt
        Actions actions = new Actions(driver);
        actions.moveToElement(productCard).perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement eyeIcon = wait.until(ExpectedConditions.elementToBeClickable(
                productCard.findElement(By.xpath(".//button[contains(@aria-label, 'Quick View')]"))
        ));
        eyeIcon.click();

        // Trả về trang chi tiết sản phẩm
        return new ProductDetailPage(driver);
    }


}
