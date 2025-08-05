package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.Client.CheckoutPage;
import pages.Client.HomePage;
import pages.Client.RegisterPage;
import pages.LoginPage;
import utils.BaseTest;
import utils.ExcelUtils;

import java.util.List;

public class RegisterTest extends BaseTest {
    //RegisterPage registerPage;
//    @Test
//    public void testRegister() throws InterruptedException {
//        // Mở trang Home -> chọn sản phẩm
//        HomePage homePage = new HomePage(driver);
//        homePage.navigateRegister();
//        Thread.sleep(2000);
//
//        RegisterPage registerPage = new RegisterPage(driver); // 🔧 Sửa chỗ này: thêm dòng khởi tạo
//        registerPage.Register("testname","test@gmail.com","123456");
//        Thread.sleep(1000);
//
//        LoginPage loginPage = new LoginPage(driver);
//        CheckoutPage checkoutPage = loginPage.login("test@gmail.com", "123456");
//        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());
//
//    }

    String excelPath = "src/test/resources/testdata/EcommerceTestData.xlsx";

    @DataProvider(name = "registerData")
    public Object[][] getRegisterData() {
        return getDataFromSheet("Register");
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


    @Test(dataProvider = "registerData", priority = 1)
    public void testRegisterValidation(String name, String email, String password, String expectedResult) throws InterruptedException {
        // Khởi tạo test cho ExtentReports
        test.info(" Test Đăng ký - " + name + " [" + expectedResult + "]");
        try {
            HomePage homePage = new HomePage(driver);
            homePage.navigateRegister();
            test.info(" Điều hướng tới trang đăng ký");

            Thread.sleep(1000);
            RegisterPage registerPage = new RegisterPage(driver);
            registerPage.Register(name, email, password);
            test.info(" Điền thông tin đăng ký: Tên = " + name + ", Email = " + email + ", Password = " + password);
            Thread.sleep(1000);

            switch (expectedResult) {
                case "success":
                    LoginPage loginPage = new LoginPage(driver);
                    CheckoutPage checkoutPage = loginPage.login(email, password);
                    Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), " Không chuyển đến trang thanh toán sau khi đăng ký thành công.");
                    test.pass(" Đăng ký thành công và đăng nhập chuyển đến trang thanh toán.");
                    checkoutPage.logout();
                    break;

                case "missing_name":
                    Assert.assertTrue(registerPage.isNameErrorDisplayed(), " Không hiển thị lỗi thiếu tên.");
                    test.pass(" Hiển thị lỗi thiếu tên như mong đợi.");
                    break;

                case "missing_email":
                    Assert.assertTrue(registerPage.isEmailErrorDisplayed(), " Không hiển thị lỗi thiếu email.");
                    test.pass(" Hiển thị lỗi thiếu email như mong đợi.");
                    break;

                case "invalid_email":
                    Assert.assertTrue(registerPage.isEmailInvalidErrorDisplayed(), " Không hiển thị lỗi định dạng email sai.");
                    test.pass(" Hiển thị lỗi định dạng email sai như mong đợi.");
                    break;

                case "missing_password":
                    Assert.assertTrue(registerPage.isPasswordErrorDisplayed(), " Không hiển thị lỗi thiếu mật khẩu.");
                    test.pass("️ Hiển thị lỗi thiếu mật khẩu như mong đợi.");
                    break;

                default:
                    test.fail(" Kết quả mong đợi không hợp lệ: " + expectedResult);
                    Assert.fail("expectedResult không hợp lệ: " + expectedResult);
            }
        } catch (Exception e) {
            test.fail(" Lỗi xảy ra trong quá trình kiểm thử: " + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }


}
