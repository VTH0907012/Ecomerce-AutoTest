package tests;


import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.Client.CheckoutPage;
import pages.Admin.DashboardPage;
import pages.LoginPage;
import utils.BaseTest;
import utils.ExcelUtils;

import java.util.List;

public class LoginTest extends BaseTest {
    LoginPage loginPage;

    String excelPath = "src/test/resources/testdata/EcommerceTestData.xlsx";

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        List<List<String>> data = ExcelUtils.getDataWithoutHeader(excelPath, "Login");
        Object[][] result = new Object[data.size()][data.get(0).size()];

        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i).toArray();
        }
        return result;
    }
//    @Test basic
//    public void testValidLogin() throws InterruptedException {
//        loginPage = new LoginPage(driver);
//        CheckoutPage checkoutPage = loginPage.login("thanhhieu@gmail.com", "123456");
//        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), "Checkout page not displayed!");
//
//    }

// Test sử dụng datatest
//    @Test(dataProvider = "loginData")
//    public void loginTest(String testCaseId, String email, String password, String expectedResult) throws InterruptedException {
//        loginPage = new LoginPage(driver);
//
//        CheckoutPage checkoutPage = loginPage.login(email, password);
//
//        switch (expectedResult.toLowerCase()) {
//            case "success":
//                Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), "Expected checkout page, but it wasn't displayed");
//                checkoutPage.logout();
//                break;
//            case "missing_email":
//            case "invalid_password":
//                Assert.assertTrue(loginPage.isErrorDisplayed(), "Expected error message not displayed");
//                break;
//
//            default:
//                Assert.fail("Unknown expected result: " + expectedResult);
//        }
//    }

    @Test(dataProvider = "loginData")
    public void loginTest(String email, String password, String expectedResult) throws InterruptedException {
        test.info("Test Đăng nhập với: " + email + " / " + password);
        try {
            loginPage = new LoginPage(driver);
            CheckoutPage checkoutPage = loginPage.login(email, password);
            expectedResult = expectedResult.toLowerCase();

            switch (expectedResult) {
                case "success":
                    test.info(" Kỳ vọng: đăng nhập thành công và chuyển đến trang thanh toán");
                    Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), " Không hiển thị trang thanh toán!");
                    test.pass(" Đăng nhập thành công, đã vào trang thanh toán");
                    checkoutPage.logout();
                    break;

                case "missing_email":
                case "invalid_password":
                    test.info(" Kỳ vọng: hiển thị thông báo lỗi vì " + expectedResult);
                    Assert.assertTrue(loginPage.isErrorDisplayed(), " Không hiển thị thông báo lỗi");
                    test.pass(" Hiển thị thông báo lỗi đúng như kỳ vọng");
                    break;

                default:
                    test.fail(" Giá trị expectedResult không hợp lệ: " + expectedResult);
                    Assert.fail("ExpectedResult không hợp lệ: " + expectedResult);
            }
        } catch (Exception | AssertionError e) {
            test.fail(e.getMessage());
            throw e;
        }

    }
}