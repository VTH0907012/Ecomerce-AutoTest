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
        CheckoutPage checkoutPage = loginPage.login("thanhhieu@gmail.com", "0914549857L");
        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());

        DashboardPage dashboardPage = checkoutPage.navAdmimPage();
        Assert.assertTrue(dashboardPage.isAtDashboard());
        dashboardPage.navCategoryManager();

        categoryPage = new CategoryPage(driver);
    }

    @Test(dataProvider = "categoryAddData", priority = 1)
    public void testAddCate(String name, String desc, String imagePath, String expectedResult) throws InterruptedException {
        test.info("testAddCate - " + name + " [" + expectedResult + "]");
        try {
            categoryPage.addCategory(name, desc, imagePath);
            Thread.sleep(1000);

            switch (expectedResult) {
                case "success":
                    boolean isAdded = categoryPage.searchCategory(name);
                    Assert.assertTrue(isAdded, "Category không hiển thị sau khi thêm.");
                    test.pass("Category đã được thêm thành công.");
                    break;

                case "missing_name":
                    boolean isNameErrorMissing = categoryPage.isErrorNameDisplayed();
                    Assert.assertTrue(isNameErrorMissing, "Không hiển thị lỗi khi thiếu tên.");
                    categoryPage.closeModal();
                    boolean isFoundMissing = categoryPage.searchCategory(name);
                    Assert.assertFalse(isFoundMissing, "Category không nên được thêm khi thiếu tên.");
                    test.pass("Hiển thị lỗi khi thiếu tên.");
                    break;

                case "existing_name":
                    boolean isNameErrorDuplicate = categoryPage.isErrorNameDisplayed();
                    Assert.assertTrue(isNameErrorDuplicate, "Không hiển thị lỗi khi trùng tên.");
                    categoryPage.closeModal();
                    boolean isFoundDuplicate = categoryPage.searchCategory(name);
                    Assert.assertTrue(isFoundDuplicate, "Category trùng tên không nên được thêm mới.");
                    test.pass("Không thêm mới khi tên đã tồn tại.");
                    break;

                default:
                    Assert.fail("expectedResult không hợp lệ: " + expectedResult);
            }
        } catch (Exception | AssertionError e) {
            test.fail(e.getMessage());
            throw e;
        }
    }

    @Test(dataProvider = "categoryEditData", priority = 2)
    public void testEditCate(String oldName, String desc, String imagePath, String newName, String expectedResult) throws InterruptedException {
        test.info("testEditCate - Sửa " + oldName + " thành " + newName + " [" + expectedResult + "]");

        try {
            categoryPage.searchCategory(oldName);
            categoryPage.editCategory(newName, desc, imagePath, oldName);
            Thread.sleep(1000);

            switch (expectedResult) {
                case "success":
                    boolean found = categoryPage.searchCategory(newName);
                    Assert.assertTrue(found, "Category không hiển thị sau khi sửa.");
                    test.pass("Sửa category thành công.");
                    break;

                case "missing_name":
                    categoryPage.closeModal();
                    Assert.assertTrue(categoryPage.isErrorNameDisplayed(), "Không hiển thị lỗi khi thiếu tên mới.");
                    test.pass("Hiển thị lỗi khi thiếu tên mới.");
                    break;

                case "existing_name":
                    categoryPage.closeModal();
                    Assert.assertTrue(categoryPage.isErrorNameDisplayed(), "Không hiển thị lỗi khi tên bị trùng.");
                    test.pass("Không sửa được khi tên bị trùng.");
                    break;

                default:
                    Assert.fail(" expectedResult không hợp lệ: " + expectedResult);
            }
        } catch ( Exception | AssertionError e) {
            test.fail(e.getMessage());
            throw e;
        }
    }


    @Test(dataProvider = "categoryDeleteData", priority = 3)
    public void testDeleteCate(String name, String expectedResult) throws InterruptedException {
        test.info("testDeleteCate - Xoá " + name + " [" + expectedResult + "]");
        try {
            boolean categoryExistsBeforeDelete = categoryPage.searchCategory(name);
            Thread.sleep(1000);

            if (categoryExistsBeforeDelete) {
                categoryPage.deteleCategory(name);
                Thread.sleep(1000);
            }

            boolean categoryStillExists = categoryPage.searchCategory(name);


            switch (expectedResult) {
                case "success":
                    Assert.assertFalse(categoryStillExists, "Category vẫn còn sau khi xoá.");
                    test.pass("Xoá category thành công.");
                    break;
                case "not_found":
                    Assert.assertFalse(categoryExistsBeforeDelete, "Category tồn tại dù expectedResult là not_found.");
                    test.pass("Không có category để xoá, đúng như mong đợi.");
                    break;
                default:
                    Assert.fail("Giá trị expectedResult không hợp lệ: " + expectedResult);
            }
        } catch (Exception | AssertionError e) {
            test.fail(e.getMessage());
            throw e;
        }
    }


// Test Basic
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
