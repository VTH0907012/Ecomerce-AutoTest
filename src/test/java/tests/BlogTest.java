package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.Admin.BlogPage;
import pages.Admin.CategoryPage;
import pages.Admin.DashboardPage;
import pages.Client.CheckoutPage;
import pages.LoginPage;
import utils.BaseTest;
import utils.ExcelUtils;

import java.util.List;

public class BlogTest extends BaseTest {
    BlogPage blogPage;
    String excelPath = "src/test/resources/testdata/EcommerceTestData.xlsx";

    @DataProvider(name = "blogAddData")
    public Object[][] getBlogAddData() {
        return getDataFromSheet("BlogAdd");
    }

    @DataProvider(name = "blogEditData")
    public Object[][] getBlogEditData() {
        return getDataFromSheet("BlogEdit");
    }

    @DataProvider(name = "blogDeleteData")
    public Object[][] getBlogDeleteData() {
        return getDataFromSheet("BlogDelete");
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
    public void loginAndNavigateToBlogPage() throws InterruptedException {
        super.setUp(); // setup driver

        LoginPage loginPage = new LoginPage(driver);
        CheckoutPage checkoutPage = loginPage.login("thanhhieu@gmail.com", "0914549857L");
        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());

        DashboardPage dashboardPage = checkoutPage.navAdmimPage();
        Assert.assertTrue(dashboardPage.isAtDashboard());
        dashboardPage.navBlogManager();
        blogPage = new BlogPage(driver);
    }

    @Test(dataProvider = "blogAddData", priority = 1)
    public void testAddBlog(String title, String content, String imagePath, String expectedResult) throws InterruptedException {
        test.info(" Thêm blog với tiêu đề: " + title);
        try {
            blogPage.addBlog(title, content, imagePath, true);
            Thread.sleep(1000);

            switch (expectedResult) {
                case "success":
                    boolean found = blogPage.searchBlog(title);
                    Assert.assertTrue(found, "Blog không hiển thị sau khi thêm.");
                    test.pass(" Blog đã thêm thành công và hiển thị trong danh sách.");
                    break;

                case "missing_title":
                    boolean titleError = blogPage.isErrorTitleDisplayed();
                    Assert.assertTrue(titleError, "Không hiển thị lỗi khi thiếu tiêu đề.");
                    test.pass(" Hiển thị lỗi đúng khi thiếu tiêu đề.");
                    blogPage.closeModal();
                    break;

                case "missing_content":
                    boolean contentError = blogPage.isErrorContentDisplayed();
                    Assert.assertTrue(contentError, "Không hiển thị lỗi khi thiếu nội dung.");
                    test.pass(" Hiển thị lỗi đúng khi thiếu nội dung.");
                    blogPage.closeModal();
                    break;

                case "missing_image":
                    boolean imageError = blogPage.isErrorImageDisplayed();
                    Assert.assertTrue(imageError, "Không hiển thị lỗi khi thiếu hình ảnh.");
                    test.pass("️ Hiển thị lỗi đúng khi thiếu hình ảnh.");
                    blogPage.closeModal();
                    break;

                default:
                    test.fail(" expectedResult không hợp lệ: " + expectedResult);
                    Assert.fail("expectedResult không hợp lệ: " + expectedResult);
            }

        } catch (AssertionError | Exception e) {
            test.fail("Test thất bại: " + e.getMessage());
            throw e;
        }


    }

    @Test(dataProvider = "blogEditData", priority = 2)
    public void testEditBlog(String title, String content, String imagePath, String title_edit, String expectedResult) throws InterruptedException {
        test.info(" Sửa blog từ tiêu đề: " + title_edit + " sang: " + title);
        try {
            blogPage.searchBlog(title_edit);
            blogPage.editBlog(title, content, imagePath, true, title_edit);
            Thread.sleep(1000);

            switch (expectedResult) {
                case "success":
                    Assert.assertTrue(blogPage.searchBlog(title), "Blog không hiển thị sau khi cập nhật.");
                    test.pass(" Blog cập nhật thành công.");
                    break;

                case "missing_title":
                    Assert.assertTrue(blogPage.isErrorTitleDisplayed(), "Không hiển thị lỗi khi thiếu tiêu đề.");
                    test.pass(" Hiển thị lỗi đúng khi thiếu tiêu đề.");
                    blogPage.closeModal();
                    break;

                case "missing_content":
                    Assert.assertTrue(blogPage.isErrorContentDisplayed(), "Không hiển thị lỗi khi thiếu nội dung.");
                    test.pass(" Hiển thị lỗi đúng khi thiếu nội dung.");
                    blogPage.closeModal();
                    break;

                default:
                    test.fail(" expectedResult không hợp lệ: " + expectedResult);
                    Assert.fail("expectedResult không hợp lệ: " + expectedResult);
            }

        } catch (AssertionError | Exception e) {
            test.fail("Test thất bại: " + e.getMessage());
            throw e;
        }


    }

    @Test(dataProvider = "blogDeleteData", priority = 3)
    public void testDeleteBlog(String title, String expectedResult) throws InterruptedException {
        test.info(" Xoá blog với tiêu đề: " + title);

        try {
            boolean isFoundBefore = blogPage.searchBlog(title);
            Thread.sleep(1000);

            if (isFoundBefore) {
                blogPage.deteleBlog(title);
                test.info(" Đã thực hiện thao tác xoá blog.");
                Thread.sleep(1000);
            } else {
                test.info(" Blog không tồn tại, không cần xoá.");
            }

            boolean blogStillExists = blogPage.searchBlog(title);

            switch (expectedResult) {
                case "success":
                    Assert.assertFalse(blogStillExists, "Blog vẫn hiển thị sau khi xóa.");
                    test.pass(" Blog đã xoá thành công.");
                    break;

                case "not_found":
                    Assert.assertFalse(isFoundBefore, "Không nên tìm thấy blog không tồn tại.");
                    test.pass(" Blog không tồn tại như mong đợi.");
                    break;

                default:
                    test.fail(" expectedResult không hợp lệ: " + expectedResult);
                    Assert.fail("expectedResult không hợp lệ: " + expectedResult);
            }

        } catch (AssertionError | Exception e) {
            test.fail("Test thất bại: " + e.getMessage());
            throw e;
        } 

    }

//Test Basic
//    @Test(priority = 1)
//    public void testAddBlog() throws InterruptedException {
//        blogPage.addBlog(
//                "testtitle",
//                "testcontent",
//                "C:\\Users\\thanh\\Downloads\\img_ip14-plus\\iphone-14-plus-tim-1.jpg", true
//        );
//        Thread.sleep(1000);
//        //categoryPage.searchCategory("testname");
//        boolean result = blogPage.searchBlog("testtitle");
//        Assert.assertTrue(result, "Blog không hiển thị sau khi thêm.");
//    }
//
//    @Test(priority = 2)
//    public void testEditBlog() throws InterruptedException {
//        blogPage.searchBlog("testtitle");
//        blogPage.editBlog(
//                "testtitle1", null, null, true, "testtitle"
//        );
//        Thread.sleep(1000);
//        //categoryPage.searchCategory("testname1");
//        boolean result = blogPage.searchBlog("testtitle1");
//        Assert.assertTrue(result, "Blog không hiển thị sau khi cập nhật.");
//    }
//
//    @Test(priority = 3)
//    public void testDeleteBlog() throws InterruptedException {
//        blogPage.searchBlog("testtitle1");
//        Thread.sleep(1000);
//
//        blogPage.deteleBlog(
//                "testtitle1"
//        );
//        Thread.sleep(1000);
//        boolean result = blogPage.searchBlog("testtitle1");
//        Assert.assertFalse(result, "Blog hiển thị sau khi thêm.");
//    }

}
