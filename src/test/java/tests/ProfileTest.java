package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.Admin.CategoryPage;
import pages.Admin.DashboardPage;
import pages.Client.CheckoutPage;
import pages.Client.ProfilePage;
import pages.LoginPage;
import utils.BaseTest;
import utils.ExcelUtils;

import java.util.List;

public class ProfileTest extends BaseTest {
    ProfilePage profilePage;
    String excelPath = "src/test/resources/testdata/EcommerceTestData.xlsx";

    @DataProvider(name = "changeInfoData")
    public Object[][] getChangeInfoData() {
        return getDataFromSheet("ChangeInfoUser");
    }

    @DataProvider(name = "changePasswordData")
    public Object[][] getChangePasswordData() {
        return getDataFromSheet("ChangePassword");
    }

    @DataProvider(name = "cancelOrderData")
    public Object[][] getCancelOrderData() {
        return getDataFromSheet("CancelOrder");
    }

    // Dùng chung method xử lý
    private Object[][] getDataFromSheet(String sheetName) {
        List<List<String>> data = ExcelUtils.getDataWithoutHeader(excelPath, sheetName);
        Object[][] result = new Object[data.size()][data.get(0).size()];
        for (int i = 0; i < data.size(); i++) {
            result[i] = data.get(i).toArray();
        }
        return result;
    }


    @BeforeClass
    public void loginAndNavigateToCategoryPage() throws InterruptedException {
        super.setUp(); // setup driver

        LoginPage loginPage = new LoginPage(driver);
        CheckoutPage checkoutPage = loginPage.login("test2@gmail.com", "0914549857L");
        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());
    }

    @Test(dataProvider = "changeInfoData", priority = 1)
    public void testChangeInfo(String name, String email, String expectedResult) throws InterruptedException {
        test.info("testChangeInfo - " + name + " / " + email + " [" + expectedResult + "]");

        try {
            profilePage = new ProfilePage(driver);
            profilePage.navigateToProfile();

            profilePage.changeInfo(name, email);
            Thread.sleep(1000);
            switch (expectedResult) {
                case "success":
                    Assert.assertTrue(profilePage.checkInfo(name, email), "Thông tin không được cập nhật đúng.");
                    test.pass("Thông tin được cập nhật thành công.");
                    break;
                case "missing_name":
                    Assert.assertTrue(profilePage.isNameErrorDisplayed(), "Không hiển thị lỗi thiếu tên.");
                    test.pass("Hiển thị lỗi thiếu tên.");
                    break;
                case "missing_email":
                    Assert.assertTrue(profilePage.isEmailErrorDisplayed(), "Không hiển thị lỗi thiếu email.");
                    test.pass("Hiển thị lỗi thiếu email.");
                    break;
                case "invalid_email":
                    Assert.assertTrue(profilePage.isEmailInvalidDisplayed(), "Không hiển thị lỗi định dạng email.");
                    test.pass("Hiển thị lỗi thiếu email.");
                    break;
                default:
                    test.fail("expectedResult không hợp lệ: " + expectedResult);
                    Assert.fail("expectedResult không hợp lệ.");
            }
        } catch (AssertionError | Exception e) {
            test.fail("Test thất bại: " + e.getMessage());
            throw e;
        }


    }

    @Test(dataProvider = "changePasswordData", priority = 2)
    public void testChangePassword(String name, String email, String currentPass, String newPass, String expectedResult) throws InterruptedException {
        test.info("testChangePassword - " + email + " / " + currentPass + " -> " + newPass + " [" + expectedResult + "]");

        try {
            profilePage = new ProfilePage(driver);
            profilePage.navigateToProfile();
            Thread.sleep(1000);

            profilePage.changePass(currentPass, newPass);
            Thread.sleep(1000);

            switch (expectedResult) {
                case "success":
                    profilePage.logut();
                    Thread.sleep(1000);
                    LoginPage loginPage = new LoginPage(driver);
                    CheckoutPage checkoutPage = loginPage.login(email, newPass);
                    Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), "Đăng nhập không thành công sau khi đổi mật khẩu.");
                    test.pass("Đổi mật khẩu và đăng nhập lại thành công.");
                    break;

                case "missing_password_current":
                    Assert.assertTrue(profilePage.isMissingCurrentPasswordMessageDisplayed(), "Không hiển thị lỗi thiếu mật khẩu cũ.");
                    profilePage.closeModal();
                    test.pass("Hiển thị lỗi thiếu mật khẩu cũ.");
                    break;

                case "missing_password_new":
                    Assert.assertTrue(profilePage.isMissingNewPasswordMessageDisplayed(), "Không hiển thị lỗi thiếu mật khẩu mới.");
                    profilePage.closeModal();
                    test.pass("Hiển thị lỗi thiếu mật khẩu mới.");
                    break;

                case "password_notcorrect":
                    Assert.assertTrue(profilePage.isIncorrectPasswordMessageDisplayed(), "Không hiển thị lỗi sai mật khẩu hiện tại.");
                    profilePage.closeModal();
                    test.pass("Hiển thị lỗi sai mật khẩu hiện tại.");
                    break;

                default:
                    test.fail("expectedResult không hợp lệ: " + expectedResult);
                    Assert.fail("Trường hợp không hợp lệ: " + expectedResult);
            }
        } catch (AssertionError | Exception e) {
            test.fail("Test thất bại: " + e.getMessage());
            throw e;
        }

    }

    @Test(dataProvider = "cancelOrderData", priority = 3)
    public void CancelOrder(String idOrder, String expectedResult) throws InterruptedException {
        test.info("CancelOrder - ID: " + idOrder + " [" + expectedResult + "]");

        try {
            profilePage = new ProfilePage(driver);
            profilePage.navigateToProfile();
            Thread.sleep(1000);
            profilePage.navigateToHistoryOrder();
            Thread.sleep(1000);

            boolean isOrderBefore = profilePage.searchOrder(idOrder);
            if (isOrderBefore) {
                profilePage.cancelOrder(idOrder);
                Thread.sleep(1000);
            }

            switch (expectedResult) {
                case "success":
                    String statusOrder = profilePage.checkStatusOrder(idOrder);
                    Assert.assertEquals(statusOrder, "Đã hủy", "Đơn hàng chưa bị hủy.");
                    test.pass("Đơn hàng đã được hủy thành công.");
                    break;

                case "not_found":
                    Assert.assertFalse(isOrderBefore, "Đơn hàng không tồn tại nhưng vẫn tìm thấy.");
                    test.pass("Không tìm thấy đơn hàng như mong đợi.");
                    break;

                default:
                    test.fail("expectedResult không hợp lệ: " + expectedResult);
                    Assert.fail("expectedResult không hợp lệ: " + expectedResult);
            }
        } catch (AssertionError | Exception e) {
            test.fail("Test thất bại: " + e.getMessage());
            throw e;
        }

    }
}
