package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.Admin.DashboardPage;
import pages.Admin.OrderPage;
import pages.Client.CheckoutPage;
import pages.LoginPage;
import utils.BaseTest;
import utils.ExcelUtils;

import java.util.List;

public class OrderTest extends BaseTest {
    OrderPage orderPage;
    String excelPath = "src/test/resources/testdata/EcommerceTestData.xlsx";

    @DataProvider(name = "orderChangeStatusData")
    public Object[][] getOrderChangeStatusData() {
        return getDataFromSheet("ChangeStatusOrder");
    }

    private Object[][] getDataFromSheet(String sheetName) {
        List<List<String>> data = ExcelUtils.getDataWithoutHeader(excelPath, sheetName);
        Object[][] result = new Object[data.size()][data.get(0).size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i).toArray();
        }
        return result;
    }

    @BeforeClass
    public void loginAndNavigateToOrderPage() throws InterruptedException {
        super.setUp();
        LoginPage loginPage = new LoginPage(driver);
        CheckoutPage checkoutPage = loginPage.login("thanhhieu@gmail.com", "0914549857L");
        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());

        DashboardPage dashboardPage = checkoutPage.navAdmimPage();
        Assert.assertTrue(dashboardPage.isAtDashboard());

        dashboardPage.navOrderManager();
        orderPage = new OrderPage(driver);
    }

    @Test(dataProvider = "orderChangeStatusData", priority = 1)
    public void testChangeStatusOrder(String idOrder, String status, String expectedResult) throws InterruptedException {
        test.info("testChangeStatusOrder - Mã đơn " + idOrder + " [" + expectedResult + "]");
        try {
            test.info(" Tìm kiếm đơn hàng với mã: " + "#" + idOrder);
            orderPage.clearSearchInput(); // làm sạch input trước mỗi lần test
            boolean isOrderFound = orderPage.searchOrder(idOrder);


            switch (expectedResult) {
                case "success":
                    test.info(" Kỳ vọng tìm thấy đơn hàng và thay đổi trạng thái thành: " + status);
                    Assert.assertTrue(isOrderFound, "Không tìm thấy đơn hàng cần thay đổi.");
                    test.info(" Tiến hành đổi trạng thái đơn hàng...");
                    orderPage.editStatusOrder("#" + idOrder, status);

                    boolean statusUpdated = orderPage.checkStatus(idOrder, status);
                    Assert.assertTrue(statusUpdated, "Trạng thái chưa được cập nhật.");
                    test.pass(" Đổi trạng thái đơn hàng thành công.");
                    break;

                case "not_found":
                    test.info(" Kỳ vọng KHÔNG tìm thấy đơn hàng.");
                    Assert.assertFalse(isOrderFound, "Đơn hàng không nên tồn tại nhưng vẫn tìm thấy.");
                    test.pass(" Không tìm thấy đơn hàng, đúng như mong đợi.");
                    break;

                default:
                    test.fail(" Giá trị expectedResult không hợp lệ: " + expectedResult);
                    Assert.fail("expectedResult không hợp lệ: " + expectedResult);
            }
        } catch (AssertionError e) {
            test.fail(" Test thất bại: " + e.getMessage());
            throw e;
        }
    }
}
