package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.Admin.CategoryPage;
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
        userPage.addUser(name, email, password);
        Thread.sleep(1000);

        switch (expectedResult) {
            case "success":
                Assert.assertTrue(userPage.searchUser(name), "User không hiển thị sau khi thêm.");
                break;
            case "missing_name":
                boolean isNameErrorMissing = userPage.isErrorDisplayed("missing_name");
                Assert.assertTrue(isNameErrorMissing, "Không hiển thị lỗi khi thiếu tên.");
                userPage.closeModal();
                boolean isFoundMissing_MissName = userPage.searchUser(name);
                Assert.assertFalse(isFoundMissing_MissName, "Product không nên được thêm khi thiếu tên.");
                break;
            case "missing_email":
                boolean isEmailErrorMissing = userPage.isErrorDisplayed("missing_email");
                Assert.assertTrue(isEmailErrorMissing, "Không hiển thị lỗi khi thiếu email.");
                userPage.closeModal();
                boolean isFoundMissing_MissEmail = userPage.searchUser(name);
                Assert.assertFalse(isFoundMissing_MissEmail, "Product không nên được thêm khi thiếu email.");
                break;
            case "missing_password":
                boolean isPassErrorMissing = userPage.isErrorDisplayed("missing_password");
                Assert.assertTrue(isPassErrorMissing, "Không hiển thị lỗi khi thiếu mật khẩu.");
                userPage.closeModal();
                boolean isFoundMissing_MissPassword = userPage.searchUser(name);
                Assert.assertFalse(isFoundMissing_MissPassword, "Product không nên được thêm khi thiếu mật khẩu.");
                break;
            case "invalid_email":
                boolean isEmailErrorInvalid = userPage.isErrorDisplayed("invalid_email");
                Assert.assertTrue(isEmailErrorInvalid, "Không hiển thị lỗi khi email sai định dạng");
                userPage.closeModal();
                boolean isFoundMissing_InvalidEmail = userPage.searchUser(name);
                Assert.assertFalse(isFoundMissing_InvalidEmail, "Product không nên được thêm khi email sai định dạng.");
                break;
            default:
                Assert.fail("expectedResult không hợp lệ: " + expectedResult);
        }
    }

    @Test(dataProvider = "userBlockData", priority = 2)
    public void testBlockUser(String name, String expectedResult) throws InterruptedException {
        boolean result = userPage.blockUser(name);
        Thread.sleep(1000);
        switch (expectedResult) {
            case "success":
                Assert.assertTrue(result, "User vẫn còn hoạt khi block");
                break;
            case "not_found":
                Assert.assertFalse(result, "User không tồn tại");
                break;
            default:
                Assert.fail("Giá trị expectedResult không hợp lệ: " + expectedResult);
        }

    }

    @Test(dataProvider = "userAssetData", priority = 3)
    public void testAssetUser(String name, String expectedResult) throws InterruptedException {
        boolean result = userPage.assetAdmin(name);
        Thread.sleep(1000);
        switch (expectedResult) {
            case "success":
                Assert.assertTrue(result, "User cấp quyền không thành công ");
                break;
            case "not_found":
                Assert.assertFalse(result, "User không tồn tại");
                break;
            default:
                Assert.fail("Giá trị expectedResult không hợp lệ: " + expectedResult);
        }

    }

    @Test(dataProvider = "userDeleteData", priority = 3)
    public void testDeleteUser(String name, String expectedResult) throws InterruptedException {
        boolean userExistsBeforeDelete = userPage.searchUser(name);
        Thread.sleep(1000);
        if (userExistsBeforeDelete) {
            userPage.deteleUser(name);
            Thread.sleep(1000);
        }
//        userPage.deteleUser(name);
//        Thread.sleep(1000);
        boolean result = userPage.searchUser(name);
        switch (expectedResult) {
            case "success":
                Assert.assertFalse(result, "User vẫn tồn tại sau khi xoá");
                break;
            case "not_found":
                Assert.assertFalse(userExistsBeforeDelete, "User tồn tại dù expectedResult là not_found.");
                break;
            default:
                Assert.fail("Giá trị expectedResult không hợp lệ: " + expectedResult);
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
