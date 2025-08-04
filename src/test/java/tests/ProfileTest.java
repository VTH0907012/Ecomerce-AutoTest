package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.Admin.CategoryPage;
import pages.Admin.DashboardPage;
import pages.Client.CheckoutPage;
import pages.Client.ProfilePage;
import pages.LoginPage;
import utils.BaseTest;

public class ProfileTest extends BaseTest {
    ProfilePage profilePage;

    @BeforeClass
    public void loginAndNavigateToCategoryPage() throws InterruptedException {
        super.setUp(); // setup driver

        LoginPage loginPage = new LoginPage(driver);
        CheckoutPage checkoutPage = loginPage.login("thanhhieu@gmail.com", "123456");
        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());
    }
    //   Bug
//    @Test
//    public void testChagneInfo() throws InterruptedException {
//        profilePage = new ProfilePage(driver);
//        profilePage.navigateToProfile();
//
//        profilePage.ChangeInfo("testname1", null);
//        Assert.assertTrue(profilePage.CheckInfo("testname1","test@gmail.com"),"Không thay đổi sau khi cập nhật");
//    }

//    @Test
//    public void testChangePassword() throws InterruptedException{
//        profilePage = new ProfilePage(driver);
//        profilePage.navigateToProfile();
//        Thread.sleep(1000);
//        profilePage.changePass("123","123456");
//        profilePage.logut();
//        Thread.sleep(1000);
//        LoginPage loginPage = new LoginPage(driver);
//        CheckoutPage checkoutPage = loginPage.login("test@gmail.com", "123456");
//        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());
//    }

    @Test
    public void CancelOrder() throws InterruptedException{
        profilePage = new ProfilePage(driver);
        profilePage.navigateToProfile();
        Thread.sleep(1000);
        profilePage.cancelOrder("688768ea691676df3d794550");
        Thread.sleep(1000);
        String statusOrder = profilePage.checkStatusOrder("688768ea691676df3d794550");
        Assert.assertTrue(statusOrder.equals("Đã hủy"),"Đơn hàng sai trạng thái");

    }

}
