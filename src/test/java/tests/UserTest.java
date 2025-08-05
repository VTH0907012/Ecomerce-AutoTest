package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.Admin.DashboardPage;
import pages.Admin.UserPage;
import pages.Client.CheckoutPage;
import pages.LoginPage;
import utils.BaseTest;
import utils.ExcelUtils;

import java.util.List;

public class UserTest extends BaseTest {
    UserPage userPage;

    String excelPath = "src/test/resources/testdata/EcommerceTestData.xlsx";

    @DataProvider(name = "userAddData")
    public Object[][] getUserAddData() {
        return getDataFromSheet("UserAdd");
    }

    @DataProvider(name = "userBlockData")
    public Object[][] getUserBlockData() {
        return getDataFromSheet("UserBlock");
    }

    @DataProvider(name = "userAssetData")
    public Object[][] getUserAssetData() {
        return getDataFromSheet("UserAsset");
    }

    @DataProvider(name = "userDeleteData")
    public Object[][] getUserDeleteData() {
        return getDataFromSheet("UserDelete");
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
    public void loginAndNavigateToUserPage() throws InterruptedException {
        super.setUp(); // setup driver

        LoginPage loginPage = new LoginPage(driver);
        CheckoutPage checkoutPage = loginPage.login("thanhhieu@gmail.com", "0914549857L");
        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());

        DashboardPage dashboardPage = checkoutPage.navAdmimPage();
        Assert.assertTrue(dashboardPage.isAtDashboard());
        dashboardPage.navUserManager();

        userPage = new UserPage(driver);
    }


    @Test(dataProvider = "userAddData", priority = 1)
    public void testAddUserValidation(String name, String email, String password, String expectedResult) throws InterruptedException {
        test.info("testAddUserValidation - " + name + " [" + expectedResult + "]");
        try {
            userPage.addUser(name, email, password);
            Thread.sleep(1000);

            switch (expectedResult) {
                case "success":
                    Assert.assertTrue(userPage.searchUser(name), "User không hiển thị sau khi thêm.");
                    test.pass("Thêm user thành công.");
                    break;
                case "missing_name":
                    Assert.assertTrue(userPage.isErrorDisplayed("missing_name"), "Không hiển thị lỗi khi thiếu tên.");
                    userPage.closeModal();
                    Assert.assertFalse(userPage.searchUser(name), "User không nên được thêm khi thiếu tên.");
                    test.pass("Bắt lỗi thiếu tên thành công.");
                    break;
                case "missing_email":
                    Assert.assertTrue(userPage.isErrorDisplayed("missing_email"), "Không hiển thị lỗi khi thiếu email.");
                    userPage.closeModal();
                    Assert.assertFalse(userPage.searchUser(name), "User không nên được thêm khi thiếu email.");
                    test.pass("Bắt lỗi thiếu email thành công.");
                    break;
                case "missing_password":
                    Assert.assertTrue(userPage.isErrorDisplayed("missing_password"), "Không hiển thị lỗi khi thiếu mật khẩu.");
                    userPage.closeModal();
                    Assert.assertFalse(userPage.searchUser(name), "User không nên được thêm khi thiếu mật khẩu.");
                    test.pass("Bắt lỗi thiếu mật khẩu thành công.");
                    break;
                case "invalid_email":
                    Assert.assertTrue(userPage.isErrorDisplayed("invalid_email"), "Không hiển thị lỗi email sai định dạng");
                    userPage.closeModal();
                    Assert.assertFalse(userPage.searchUser(name), "User không nên được thêm khi email sai định dạng.");
                    test.pass("Bắt lỗi email sai định dạng thành công.");
                    break;
                default:
                    test.fail("Giá trị expectedResult không hợp lệ.");
                    Assert.fail("expectedResult không hợp lệ: " + expectedResult);
            }
        } catch (AssertionError | Exception e) {
            test.fail("Test thất bại: " + e.getMessage());
            throw e;
        }
    }
    @Test(dataProvider = "userBlockData", priority = 2)
    public void testBlockUser(String name, String expectedResult) throws InterruptedException {
        test.info("testBlockUser - " + name + " [" + expectedResult + "]");
        try {
            // Tìm user trước khi block
            boolean userVisible = userPage.searchUser(name);
            Thread.sleep(1000);

            switch (expectedResult) {
                case "success":
                    Assert.assertTrue(userVisible, "Không tìm thấy user cần block");
                    boolean isBlocked = userPage.blockUser(name);
                    Assert.assertTrue(isBlocked, "User vẫn còn hoạt khi block");
                    test.pass("Block user thành công.");
                    break;

                case "not_found":
                    Assert.assertFalse(userVisible, "User không nên tồn tại nhưng vẫn tìm thấy.");
                    test.pass("Không tìm thấy user như mong đợi.");
                    break;

                default:
                    test.fail("expectedResult không hợp lệ.");
                    Assert.fail("Giá trị expectedResult không hợp lệ: " + expectedResult);
            }
        } catch (AssertionError | Exception e) {
            test.fail("Test thất bại: " + e.getMessage());
            throw e;
        }
    }
    @Test(dataProvider = "userAssetData", priority = 3)
    public void testAssetUser(String name, String expectedResult) throws InterruptedException {
        test.info("testAssetUser - " + name + " [" + expectedResult + "]");
        try {
            // Kiểm tra user có tồn tại không
            boolean userVisible = userPage.searchUser(name);
            Thread.sleep(1000);

            switch (expectedResult) {
                case "success":
                    Assert.assertTrue(userVisible, "Không tìm thấy user cần cấp quyền.");
                    boolean isAsseted = userPage.assetAdmin(name);
                    Assert.assertTrue(isAsseted, "User cấp quyền không thành công.");
                    test.pass("Cấp quyền thành công.");
                    break;

                case "not_found":
                    Assert.assertFalse(userVisible, "User không nên tồn tại nhưng vẫn tìm thấy.");
                    test.pass("Không tìm thấy user như mong đợi.");
                    break;

                default:
                    test.fail("expectedResult không hợp lệ.");
                    Assert.fail("Giá trị expectedResult không hợp lệ: " + expectedResult);
            }
        } catch (AssertionError | Exception e) {
            test.fail("Test thất bại: " + e.getMessage());
            throw e;
        }
    }
    @Test(dataProvider = "userDeleteData", priority = 3)
    public void testDeleteUser(String name, String expectedResult) throws InterruptedException {
        test.info("testDeleteUser - " + name + " [" + expectedResult + "]");
        try {
            boolean userExistsBeforeDelete = userPage.searchUser(name);
            Thread.sleep(1000);
            if (userExistsBeforeDelete) {
                userPage.deteleUser(name);
                Thread.sleep(1000);
            }

            boolean result = userPage.searchUser(name);
            switch (expectedResult) {
                case "success":
                    Assert.assertFalse(result, "User vẫn tồn tại sau khi xoá");
                    test.pass("Xoá user thành công.");
                    break;
                case "not_found":
                    Assert.assertFalse(userExistsBeforeDelete, "User tồn tại dù expectedResult là not_found.");
                    test.pass("Không tìm thấy user như mong đợi.");
                    break;
                default:
                    test.fail("expectedResult không hợp lệ.");
                    Assert.fail("Giá trị expectedResult không hợp lệ: " + expectedResult);
            }
        } catch (AssertionError | Exception e) {
            test.fail("Test thất bại: " + e.getMessage());
            throw e;
        }
    }






    //    @Test(priority = 1)
//    public void testAddUser() throws InterruptedException {
//        userPage.addUser("testname", "test@gmail.com", "123456");
//        Thread.sleep(1000);
//        boolean result = userPage.searchUser("testname");
//        Assert.assertTrue(result, "User không hiển thị sau khi thêm.");
//        Thread.sleep(1000);
//
//    }
//
//    @Test(priority = 2)
//    public void testBlockUser() throws InterruptedException {
//        boolean result = userPage.blockUser("testname");
//        Assert.assertTrue(result, "Block User thành công");
//
//    }
//    @Test(priority = 3)
//    public void testAssetAdminUser() throws InterruptedException {
//        boolean result = userPage.assetAdmin("testname");
//        Assert.assertTrue(result, "Cấp quyền Admin cho User thành công");
//
//    }
//    @Test(priority = 4)
//    public void testDeleteCate() throws InterruptedException {
//        userPage.deteleUser(
//                "testname"
//        );
//        Thread.sleep(1000);
//
//        boolean result = userPage.searchUser("testname");
//        Assert.assertFalse(result, "User hiển thị sau khi thêm.");
//    }

}
