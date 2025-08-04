package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.Client.CartPage;
import pages.Client.HomePage;
import pages.Client.ProductDetailPage;
import utils.BaseTest;

public class CartTest extends BaseTest {

    @Test(priority = 1)
    public void testAddToCart() throws InterruptedException {
        // Mở trang Home -> vào chi tiết -> chọn sản phẩm
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHome();
        Thread.sleep(2000);
        ProductDetailPage productDetail = homePage.viewProduct("Apple iMac M1 24-inch 2021");
        Thread.sleep(2000);

        // Thêm vào giỏ
        productDetail.addToCart(1);
        Thread.sleep(1000);
        CartPage cartPage = productDetail.navCart();
        Thread.sleep(1000);

        //Trang cart
        CartPage cart = new CartPage(driver);
        Assert.assertTrue(cart.isProductInCart("Apple iMac M1 24-inch 2021"));

    }

    @Test(priority = 2)
    public void testUdpateQuantity() throws InterruptedException {
        CartPage cart = new CartPage(driver);
        Thread.sleep(1000);
        // Tăng số lượng
        cart.increaseQuantity("Apple iMac M1 24-inch 2021");
        Assert.assertEquals(cart.getCurrentQuantity("Apple iMac M1 24-inch 2021"), "2");
        Thread.sleep(1000);
        // Giảm số lượng
        cart.decreaseQuantity("Apple iMac M1 24-inch 2021");
        Assert.assertEquals(cart.getCurrentQuantity("Apple iMac M1 24-inch 2021"), "1");
    }

    @Test(priority = 3)
    public void testRemoveProductFromCart() throws InterruptedException {
        CartPage cart = new CartPage(driver);
        cart.removeProductFromCart("Apple iMac M1 24-inch 2021");
        Assert.assertFalse(cart.isProductInCart("Apple iMac M1 24-inch 2021"));
    }
}
