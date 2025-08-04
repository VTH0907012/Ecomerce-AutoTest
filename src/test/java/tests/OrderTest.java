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
        CheckoutPage checkoutPage = loginPage.login("thanhhieu@gmail.com", "123456");
        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());

        DashboardPage dashboardPage = checkoutPage.navAdmimPage();
        Assert.assertTrue(dashboardPage.isAtDashboard());

        dashboardPage.navOrderManager();
        orderPage = new OrderPage(driver);
    }

    @Test(dataProvider = "orderChangeStatusData", priority = 1)
    public void testChangeStatusOrder(String idOrder, String status, String expectedResult) throws InterruptedException {
        orderPage.clearSearchInput(); // làm sạch input trước mỗi lần test
        boolean isOrderFound = orderPage.searchOrder(idOrder);

        switch (expectedResult) {
            case "success":
                Assert.assertTrue(isOrderFound, "Không tìm thấy đơn hàng cần thay đổi.");
                orderPage.editStatusOrder(idOrder, status);
                Assert.assertTrue(orderPage.checkStatus(idOrder, status), "Trạng thái chưa được cập nhật.");
                break;

            case "not_found":
                Assert.assertFalse(isOrderFound, "Đơn hàng không nên tồn tại nhưng vẫn tìm thấy.");
                //Assert.assertTrue(orderPage.isOrderNotFoundDisplayed(), "Không hiển thị thông báo đơn hàng không tồn tại.");
                break;

            default:
                Assert.fail("expectedResult không hợp lệ: " + expectedResult);
        }
    }
}
