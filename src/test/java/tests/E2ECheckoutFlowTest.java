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

        try {
            test.info("Bắt đầu đăng nhập...");
            LoginPage loginPage = new LoginPage(driver);
            CheckoutPage checkoutPage = loginPage.login("thanhhieu@gmail.com", "0914549857L");
            Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());
            test.pass("Đăng nhập thành công.");

            test.info("Mở trang chủ và chọn sản phẩm.");
            HomePage homePage = new HomePage(driver);
            homePage.navigateToHome();
            Thread.sleep(2000);
            ProductDetailPage productDetail = homePage.viewProduct("Apple iMac M1 24-inch 2021");
            Thread.sleep(2000);

            test.info("Thêm sản phẩm vào giỏ hàng.");
            productDetail.addToCart(1);
            Thread.sleep(1000);
            CartPage cartPage = productDetail.navCart();
            Thread.sleep(1000);

            test.info("Tiến hành thanh toán.");
            checkoutPage = cartPage.navPayment();
            checkoutPage.fillShippingDetails("Võ Thanh Hiếu", "0912345678", "123 Lê Lợi", "Không", "cod");
            Thread.sleep(1000);

            test.info("Đặt hàng và kiểm tra kết quả.");
            OrderSuccessPage successPage = checkoutPage.placeOrder();
            String orderCode = successPage.codeOrderSuccess();
            Assert.assertTrue(orderCode != null && !orderCode.trim().isEmpty(), "Không hiện mã đơn hàng");
            test.pass("Đặt hàng thành công. Mã đơn: " + orderCode);

            test.info("Kiểm tra đơn hàng trong trang admin.");
            DashboardPage dashboardPage = checkoutPage.navAdmimPage();
            Assert.assertTrue(dashboardPage.isAtDashboard());
            dashboardPage.navOrderManager();
            OrderPage orderPage = new OrderPage(driver);

            Assert.assertTrue(orderPage.searchOrder(orderCode), "Không tìm thấy đơn hàng vừa đặt");
            test.pass("Đơn hàng xuất hiện trong hệ thống admin.");

        } catch (AssertionError | Exception e) {
            test.fail("Test thất bại: " + e.getMessage());
            throw e;
        }
    }
}
