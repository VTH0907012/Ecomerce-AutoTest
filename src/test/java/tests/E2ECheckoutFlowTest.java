package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.Admin.DashboardPage;
import pages.Admin.OrderPage;
import pages.Client.*;
import pages.LoginPage;
import utils.BaseTest;

public class E2ECheckoutFlowTest extends BaseTest {

    @Test
    public void testFullCheckoutFlow() throws InterruptedException {
        // Login
        LoginPage loginPage = new LoginPage(driver);
        CheckoutPage checkoutPage = loginPage.login("thanhhieu@gmail.com", "123456");
        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());

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

        //Thanh toán
        checkoutPage = cartPage.navPayment();
        checkoutPage.fillShippingDetails("Võ Thanh Hiếu", "0912345678", "123 Lê Lợi", "Không", "cod");
        Thread.sleep(1000);

        // Kiểm tra thanh toán thành công?
        OrderSuccessPage successPage = checkoutPage.placeOrder();
        String orderCode = successPage.codeOrderSuccess();
        Assert.assertTrue(orderCode != null && !orderCode.trim().isEmpty(), "Không hiện mã đơn hàng");

        // Kiểm tra đơn hàng bên quản trị viên
        DashboardPage dashboardPage = checkoutPage.navAdmimPage();
        Assert.assertTrue(dashboardPage.isAtDashboard());
        dashboardPage.navOrderManager();
        OrderPage orderPage = new OrderPage(driver);

        Assert.assertTrue(orderPage.searchOrder(orderCode), "Không tìm thấy đơn hàng vừa đặt");
        Thread.sleep(1000);


    }
}
