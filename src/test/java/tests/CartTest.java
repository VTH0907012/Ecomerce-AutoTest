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
        test.info(" Kiểm tra thêm sản phẩm vào giỏ hàng");
        try {
            HomePage homePage = new HomePage(driver);
            homePage.navigateToHome();
            test.info(" Đã điều hướng về trang chủ");

            Thread.sleep(2000);
            ProductDetailPage productDetail = homePage.viewProduct("Apple iMac M1 24-inch 2021");
            test.info(" Xem chi tiết sản phẩm: Apple iMac M1 24-inch 2021");

            Thread.sleep(2000);
            productDetail.addToCart(1);
            test.info(" Đã thêm 1 sản phẩm vào giỏ hàng");

            Thread.sleep(1000);
            CartPage cartPage = productDetail.navCart();
            Thread.sleep(1000);

            CartPage cart = new CartPage(driver);
            boolean isInCart = cart.isProductInCart("Apple iMac M1 24-inch 2021");
            Assert.assertTrue(isInCart, " Sản phẩm không có trong giỏ hàng sau khi thêm");
            test.pass(" Sản phẩm đã hiển thị trong giỏ hàng");
        } catch (AssertionError | Exception e) {
            test.fail("Test thất bại: " + e.getMessage());
            throw e;
        }

    }

    @Test(priority = 2)
    public void testUdpateQuantity() throws InterruptedException {
        test.info(" Kiểm tra cập nhật số lượng sản phẩm trong giỏ");

        try {
            CartPage cart = new CartPage(driver);
            Thread.sleep(1000);

            cart.increaseQuantity("Apple iMac M1 24-inch 2021");
            test.info(" Tăng số lượng sản phẩm lên 2");
            Assert.assertEquals(cart.getCurrentQuantity("Apple iMac M1 24-inch 2021"), "2");

            cart.decreaseQuantity("Apple iMac M1 24-inch 2021");
            test.info(" Giảm số lượng sản phẩm về lại 1");
            Assert.assertEquals(cart.getCurrentQuantity("Apple iMac M1 24-inch 2021"), "1");

            test.pass(" Cập nhật số lượng thành công");
        } catch (AssertionError | Exception e) {
            test.fail("Test thất bại: " + e.getMessage());
            throw e;
        }


    }

    @Test(priority = 3)
    public void testRemoveProductFromCart() throws InterruptedException {
        test.info(" Xoá sản phẩm khỏi giỏ hàng");
        try {
            CartPage cart = new CartPage(driver);
            cart.removeProductFromCart("Apple iMac M1 24-inch 2021");

            boolean isStillInCart = cart.isProductInCart("Apple iMac M1 24-inch 2021");
            Assert.assertFalse(isStillInCart, " Sản phẩm vẫn còn trong giỏ sau khi xoá");

            test.pass("🗑 Đã xoá sản phẩm khỏi giỏ hàng thành công");
        } catch (AssertionError | Exception e) {
            test.fail("Test thất bại: " + e.getMessage());
            throw e;
        }

    }
}