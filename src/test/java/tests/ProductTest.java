package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.Admin.CategoryPage;
import pages.Admin.DashboardPage;
import pages.Admin.ProductPage;
import pages.Client.CheckoutPage;
import pages.LoginPage;
import utils.BaseTest;
import utils.ExcelUtils;

import java.util.List;

public class ProductTest extends BaseTest {

    ProductPage productPage;
    String excelPath = "src/test/resources/testdata/EcommerceTestData.xlsx";

    @DataProvider(name = "productAddData")
    public Object[][] getProductAddData() {
        return getDataFromSheet("ProductAdd");
    }

    @DataProvider(name = "productEditData")
    public Object[][] getProductEditData() {
        return getDataFromSheet("ProductEdit");
    }
    @DataProvider(name = "productDeleteData")
    public Object[][] getProductDeleteData() {
        return getDataFromSheet("ProductDelete");
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
    public void loginAndNavigateToProductPage() throws InterruptedException {
        super.setUp(); // setup driver

        LoginPage loginPage = new LoginPage(driver);
        CheckoutPage checkoutPage = loginPage.login("thanhhieu@gmail.com", "0914549857L");
        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed());

        DashboardPage dashboardPage = checkoutPage.navAdmimPage();
        Assert.assertTrue(dashboardPage.isAtDashboard());

        dashboardPage.navProductManager();
        productPage = new ProductPage(driver);
    }

    @Test(dataProvider = "productAddData", priority = 1)
    public void testAddProduct(String name, String price, String disprice, String quantity, String category, String brand, String desc, String rating, String imagePath, String expectedResult) throws InterruptedException {
        test.info("testAddProduct - " + name + " [" + expectedResult + "]");
        try {
            Float parsedPrice = (price == null || price.isEmpty()) ? null : Float.parseFloat(price.replace("f", ""));
            Float parsedDisPrice = (disprice == null || disprice.isEmpty()) ? null : Float.parseFloat(disprice.replace("f", ""));
            Integer parsedQuantity = (quantity == null || quantity.isEmpty()) ? null : Integer.parseInt(quantity);
            Float parsedRating = (rating == null || rating.isEmpty()) ? null : Float.parseFloat(rating.replace("f", ""));

            test.info(" Thêm sản phẩm: " + name);
            productPage.addProduct(name, parsedPrice, parsedDisPrice, parsedQuantity, category, brand, desc, parsedRating, imagePath);
            Thread.sleep(1500);

            switch (expectedResult) {
                case "success":
                    boolean isAdded = productPage.searchProduct(name);
                    Assert.assertTrue(isAdded, " Product không hiển thị sau khi thêm.");
                    break;
                case "missing_name":
                    Assert.assertTrue(productPage.isErrorDisplayed("missing_name"), " Không hiển thị lỗi khi thiếu tên.");
                    productPage.closeModal();
                    Assert.assertFalse(productPage.searchProduct(name));
                    break;
                case "missing_price":
                    Assert.assertTrue(productPage.isErrorDisplayed("missing_price"), " Không hiển thị lỗi khi thiếu giá.");
                    productPage.closeModal();
                    Assert.assertFalse(productPage.searchProduct(name));
                    break;
                case "missing_quantity":
                    Assert.assertTrue(productPage.isErrorDisplayed("missing_quantity"), " Không hiển thị lỗi khi thiếu số lượng.");
                    productPage.closeModal();
                    Assert.assertFalse(productPage.searchProduct(name));
                    break;
                case "missing_category":
                    Assert.assertTrue(productPage.isErrorDisplayed("missing_category"), " Không hiển thị lỗi khi thiếu danh mục.");
                    productPage.closeModal();
                    Assert.assertFalse(productPage.searchProduct(name));
                    break;
                case "missing_brand":
                    Assert.assertTrue(productPage.isErrorDisplayed("missing_brand"), " Không hiển thị lỗi khi thiếu nhãn hiệu.");
                    productPage.closeModal();
                    Assert.assertFalse(productPage.searchProduct(name));
                    break;
                case "missing_images":
                    Assert.assertTrue(productPage.isErrorDisplayed("missing_images"), " Không hiển thị lỗi khi thiếu hình ảnh.");
                    productPage.closeModal();
                    Assert.assertFalse(productPage.searchProduct(name));
                    break;
                default:
                    Assert.fail(" expectedResult không hợp lệ: " + expectedResult);
            }

        } catch (Exception | AssertionError e) {
            test.fail(" Test thất bại: " + e.getMessage());
            throw e;
        }
    }
    @Test(dataProvider = "productEditData", priority = 2)
    public void testEditProduct(String name, String price, String disprice, String quantity, String category, String brand, String desc, String rating, String imagePath, String name_edit, String expectedResult) throws InterruptedException {
        test.info("testEditProduct - " + name + " → " + name_edit + " [" + expectedResult + "]");
        try {
            Float parsedPrice = (price == null || price.isEmpty()) ? null : Float.parseFloat(price.replace("f", ""));
            Float parsedDisPrice = (disprice == null || disprice.isEmpty()) ? null : Float.parseFloat(disprice.replace("f", ""));
            Integer parsedQuantity = (quantity == null || quantity.isEmpty()) ? null : Integer.parseInt(quantity);
            Float parsedRating = (rating == null || rating.isEmpty()) ? null : Float.parseFloat(rating.replace("f", ""));

            productPage.searchProduct("testname");
            productPage.editProduct(name, parsedPrice, parsedDisPrice, parsedQuantity, category, brand, desc, parsedRating, imagePath, name_edit);
            Thread.sleep(1500);

            switch (expectedResult) {
                case "success":
                    Assert.assertTrue(productPage.searchProduct(name), " Product không hiển thị sau khi sửa.");
                    break;
                case "missing_name":
                    Assert.assertTrue(productPage.isErrorDisplayed("missing_name"), " Không hiển thị lỗi khi thiếu tên.");
                    productPage.closeModal();
                    break;
                case "missing_price":
                    Assert.assertTrue(productPage.isErrorDisplayed("missing_price"), " Không hiển thị lỗi khi thiếu giá.");
                    productPage.closeModal();
                    break;
                case "missing_quantity":
                    Assert.assertTrue(productPage.isErrorDisplayed("missing_quantity"), " Không hiển thị lỗi khi thiếu số lượng.");
                    productPage.closeModal();
                    break;
                case "missing_category":
                    Assert.assertTrue(productPage.isErrorDisplayed("missing_category"), " Không hiển thị lỗi khi thiếu danh mục.");
                    productPage.closeModal();
                    break;
                case "missing_brand":
                    Assert.assertTrue(productPage.isErrorDisplayed("missing_brand"), " Không hiển thị lỗi khi thiếu nhãn hiệu.");
                    productPage.closeModal();
                    break;
                case "missing_images":
                    Assert.assertTrue(productPage.isErrorDisplayed("missing_images"), " Không hiển thị lỗi khi thiếu hình ảnh.");
                    productPage.closeModal();
                    break;
                default:
                    Assert.fail(" expectedResult không hợp lệ: " + expectedResult);
            }

        } catch (Exception | AssertionError e) {
            test.fail(" Test thất bại: " + e.getMessage());
            throw e;
        }
    }
    @Test(dataProvider = "productDeleteData", priority = 3)
    public void testDeleteProduct(String name, String expectedResult) throws InterruptedException {
        test.info("testDeleteProduct - " + name + " [" + expectedResult + "]");
        try {
            boolean productExistsBeforeDelete = productPage.searchProduct(name);
            Thread.sleep(1000);
            if (productExistsBeforeDelete) {
                productPage.deteleProduct(name);
                Thread.sleep(1000);
            }

            boolean productStillExists = productPage.searchProduct(name);
            switch (expectedResult) {
                case "success":
                    Assert.assertFalse(productStillExists, " Product vẫn còn sau khi xoá.");
                    break;
                case "not_found":
                    Assert.assertFalse(productExistsBeforeDelete, " Product tồn tại dù expectedResult là not_found.");
                    break;
                default:
                    Assert.fail(" expectedResult không hợp lệ: " + expectedResult);
            }

        } catch (Exception | AssertionError e) {
            test.fail(" Test thất bại: " + e.getMessage());
            throw e;
        }
    }
//    @Test(priority = 1)
//    public void testAddProduct() throws InterruptedException{
//        productPage.addProduct("testname", 100000f,50000f, 40, "Tivi","Sony","testdesc",4f,"C:\\Users\\thanh\\Downloads\\img_ip14-plus\\iphone-14-plus-tim-1.jpg");
//        Thread.sleep(1000);
//
//        boolean result = productPage.searchProduct("testname");
//        Assert.assertTrue(result, "Category không hiển thị sau khi thêm.");
//
//    }
//    @Test(priority = 2)
//    public void testEditCate() throws InterruptedException {
//        productPage.searchProduct("testname");
//        productPage.editProduct(
//                "testname1", null, null, null,null,null,null,null,null,"testname"
//        );
//        Thread.sleep(1000);
//
//        boolean result = productPage.searchProduct("testname1");
//        Assert.assertTrue(result, "Category không hiển thị sau khi thêm.");
//    }
//
//    @Test(priority = 3)
//    public void testDeleteProduct() throws InterruptedException {
//        productPage.searchProduct("testname1");
//        Thread.sleep(1000);
//
//        productPage.deteleProduct(
//                "testname1"
//        );
//        Thread.sleep(1000);
//
//        boolean result = productPage.searchProduct("testname1");
//        Assert.assertFalse(result, "Category hiển thị sau khi thêm.");
//    }


}
