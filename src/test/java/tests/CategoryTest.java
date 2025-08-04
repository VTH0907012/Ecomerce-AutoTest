package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.Admin.CategoryPage;
import pages.Admin.DashboardPage;
import pages.Client.CheckoutPage;
import pages.LoginPage;
import utils.BaseTest;
import utils.ExcelUtils;

import java.util.List;

public class CategoryTest extends BaseTest {
    CategoryPage categoryPage;


    String excelPath = "src/test/resources/testdata/EcommerceTestData.xlsx";

    @DataProvider(name = "categoryAddData")
    public Object[][] getCategoryAddData() {
        return getDataFromSheet("CategoryAdd");
    }

    @DataProvider(name = "categoryEditData")
    public Object[][] getCategoryEditData() {
        return getDataFromSheet("CategoryEdit");
    }
    @DataProvider(name = "categoryDeleteData")
    public Object[][] getCategoryDeleteData() {
        return getDataFromSheet("CategoryDelete");
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
        CheckoutPage checkoutPage = loginPage.login("thanhhieu@gmail.com", "123456");
        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());

        DashboardPage dashboardPage = checkoutPage.navAdmimPage();
        Assert.assertTrue(dashboardPage.isAtDashboard());
        dashboardPage.navCategoryManager();

        categoryPage = new CategoryPage(driver);
    }

    @Test(dataProvider = "categoryAddData", priority = 1)
    public void testAddCate(String name, String desc, String imagePath, String expectedResult) throws InterruptedException {
        categoryPage.addCategory(name, desc, imagePath);
        Thread.sleep(1000);

        switch (expectedResult) {
            case "success":
                boolean isAdded = categoryPage.searchCategory(name);
                Assert.assertTrue(isAdded, "Category không hiển thị sau khi thêm.");
                break;

            case "missing_name":
                boolean isNameErrorMissing = categoryPage.isErrorNameDisplayed();
                Assert.assertTrue(isNameErrorMissing, "Không hiển thị lỗi khi thiếu tên.");
                categoryPage.closeModal();
                boolean isFoundMissing = categoryPage.searchCategory(name);
                Assert.assertFalse(isFoundMissing, "Category không nên được thêm khi thiếu tên.");
                break;

            case "existing_name":
                boolean isNameErrorDuplicate = categoryPage.isErrorNameDisplayed();
                Assert.assertTrue(isNameErrorDuplicate, "Không hiển thị lỗi khi trùng tên.");
                categoryPage.closeModal();
                boolean isFoundDuplicate = categoryPage.searchCategory(name);
                Assert.assertTrue(isFoundDuplicate, "Category trùng tên không nên được thêm mới (vẫn chỉ nên có 1 bản).");
                break;

            default:
                Assert.fail("expectedResult không hợp lệ: " + expectedResult);
        }
    }

    @Test(dataProvider = "categoryEditData", priority = 2)
    public void testEditCate(String oldName, String desc, String imagePath, String newName, String expectedResult) throws InterruptedException {
        // B1: Tìm danh mục cần sửa
        categoryPage.searchCategory(oldName);

        // B2: Thực hiện sửa danh mục với dữ liệu được truyền vào
        categoryPage.editCategory(newName, desc, imagePath, oldName);

        Thread.sleep(1000);

        switch (expectedResult) {
            case "success":
                boolean found = categoryPage.searchCategory(newName);
                Assert.assertTrue(found, "Category không hiển thị sau khi sửa.");
                break;

            case "missing_name":
                categoryPage.closeModal(); // Giả sử bạn có nút đóng form modal
                Assert.assertTrue(categoryPage.isErrorNameDisplayed(), "Không hiển thị lỗi khi thiếu tên mới.");
                break;

            case "existing_name":
                categoryPage.closeModal();
                Assert.assertTrue(categoryPage.isErrorNameDisplayed(), "Không hiển thị lỗi khi tên bị trùng.");
                break;

            default:
                Assert.fail(" expectedResult không hợp lệ: " + expectedResult);
        }
    }


    @Test(dataProvider = "categoryDeleteData", priority = 3)
    public void testDeleteCate(String name, String expectedResult) throws InterruptedException {
        boolean categoryExistsBeforeDelete = categoryPage.searchCategory(name);
        Thread.sleep(1000);

        // Nếu category tồn tại thì tiến hành xoá
        if (categoryExistsBeforeDelete) {
            categoryPage.deteleCategory(name);
            Thread.sleep(1000);
        }

        boolean categoryStillExists = categoryPage.searchCategory(name);
        switch (expectedResult) {
            case "success":
                Assert.assertFalse(categoryStillExists, "Category vẫn còn sau khi xoá.");
                break;
            case "not_found":
                Assert.assertFalse(categoryExistsBeforeDelete, "Category tồn tại dù expectedResult là not_found.");
                break;
            default:
                Assert.fail("Giá trị expectedResult không hợp lệ: " + expectedResult);
        }
    }

//    @Test(priority = 1)
//    public void testAddCate(String name, String desc, String imagePath) throws InterruptedException {
//        categoryPage.addCategory(
//                "testname",
//                "testdesc",
//                "C:\\Users\\thanh\\Downloads\\img_ip14-plus\\iphone-14-plus-tim-1.jpg"
//        );
//        Thread.sleep(1000);
//        boolean result = categoryPage.searchCategory("testname");
//        Assert.assertTrue(result, "Category không hiển thị sau khi thêm.");
//    }
//    @Test(priority = 2)
//    public void testEditCate() throws InterruptedException {
//        categoryPage.searchCategory("testname");
//        categoryPage.editCategory(
//                "testname1", null, null, "testname"
//        );
//        Thread.sleep(1000);
//        boolean result = categoryPage.searchCategory("testname1");
//        Assert.assertTrue(result, "Category không hiển thị sau khi thêm.");
//    }
//
//    @Test(priority = 3)
//    public void testDeleteCate() throws InterruptedException {
//        categoryPage.searchCategory("testname1");
//        Thread.sleep(1000);
//        categoryPage.deteleCategory(
//                "testname1"
//        );
//        Thread.sleep(1000);
//        boolean result = categoryPage.searchCategory("testname1");
//        Assert.assertFalse(result, "Category hiển thị sau khi thêm.");
//    }


}
