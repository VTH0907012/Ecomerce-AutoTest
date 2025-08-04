package pages.Admin;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import utils.BasePage;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    //Add Product
    By btnAddProduct = By.xpath("//button[contains(text(),'Thêm sản phẩm')]");

    public void openAddProduct() {
        click(btnAddProduct);
    }

    By inputNameProduct = By.xpath("//input[@placeholder='Nhập tên sản phẩm']");
    By inputPriceProduct = By.xpath("//input[@placeholder='Nhập giá sản phẩm']");
    By inputPriceDisProduct = By.xpath("//input[@placeholder='Nhập giá khuyến mãi']");
    By inputQuantityProduct = By.xpath("//input[@placeholder='Nhập số lượng']");

    By dropdownCateProduct = By.xpath("//select[@name='category']");
    // selectCateProduct = new Select(driver.findElement(dropdownCateProduct));

    By dropdownBrandProduct = By.xpath("//select[@name='brand']");
    //Select selectBrandProduct = new Select(driver.findElement(dropdownBrandProduct));

    By inputDescProduct = By.xpath("//textarea[@placeholder='Nhập mô tả sản phẩm']");
    By inputRatingProduct = By.xpath("//input[@placeholder='Nhập đánh giá từ 0-5']");
    By inputImageProduct = By.xpath("//input[@type='file']");
    By bntAddProduct = By.xpath("//button[normalize-space()='Thêm']");

    public void addProduct(String name, Float price, Float disprice, Number quantity, String category, String brand, String desc, Float rating, String image) throws InterruptedException {

        openAddProduct();
        Thread.sleep(1000);

        if (name != null) {
            sendKeys(inputNameProduct, name);
            Thread.sleep(1000);
        }
        if (price != null) {
            sendKeys(inputPriceProduct, price);
            Thread.sleep(1000);
        }
        if (disprice != null) {
            sendKeys(inputPriceDisProduct, disprice);
            Thread.sleep(1000);
        }
        if (quantity != null) {
            sendKeys(inputQuantityProduct, quantity);
            Thread.sleep(1000);
        }
        if (category != null && category != "") {
            selectByVisibleText(dropdownCateProduct, category);
            Thread.sleep(1000);
        }
        if (brand != null && brand !="") {
            selectByVisibleText(dropdownBrandProduct, brand);
            Thread.sleep(1000);
        }
        if (desc != null) {
            sendKeys(inputDescProduct, desc);
            Thread.sleep(1000);
        }
        if (rating != null) {
            sendKeys(inputRatingProduct, rating);
            Thread.sleep(1000);
        }
        if (image != null && image != "") {
            removeClassHidden(inputImageProduct);
            sendKeys(inputImageProduct, image);
            Thread.sleep(1000);
        }
        click(bntAddProduct);

    }

    By inputSearchProduct = By.xpath("//input[@placeholder='Tìm kiếm sản phẩm...']");

    public boolean searchProduct(String name) {
        if (name != null && name != "") {
            sendKeys(inputSearchProduct, name);
            By table_td_Product = By.xpath("//td[normalize-space()='" + name + "']");
            return isDisplayed(table_td_Product);
        }
        return false;

    }


    //Delete Product
    public void deteleProduct(String name) throws InterruptedException {
        Thread.sleep(1000);
        By btnIdDelete = By.xpath("//tbody/tr[td[normalize-space()='" + name + "']]//button[@title='Xóa']");
        click(btnIdDelete);
        Thread.sleep(1000);
        By btnDelete = By.xpath("//button[normalize-space()='Xoá']");
        click(btnDelete);

    }

    //Edit Product

    By btnUpdateProduct = By.xpath("//button[contains(text(),'Cập nhật')]");

    public void editProduct(String name, Float price, Float disprice, Number quantity, String category, String brand, String desc, Float rating, String image, String name_edit) throws InterruptedException {
        By editButton = By.xpath("//tbody/tr[td[normalize-space()='" + name_edit + "']]//button[@title='Sửa']");

        click(editButton);
        Thread.sleep(1000);

        if (name != null) {
            sendKeys(inputNameProduct, name.isEmpty() ? Keys.SPACE + "" + Keys.BACK_SPACE : name);
            //sendKeys(inputNameProduct, name);
            Thread.sleep(1000);
        }
        if (price != null) {
            sendKeys(inputPriceProduct, price);
            Thread.sleep(1000);
        }
        if (disprice != null) {
            sendKeys(inputPriceDisProduct, disprice);
            Thread.sleep(1000);
        }
        if (quantity != null) {
            sendKeys(inputQuantityProduct, quantity);
            Thread.sleep(1000);
        }
        if (category != null) {
            selectByVisibleText(dropdownCateProduct, category);
            Thread.sleep(1000);
        }
        if (brand != null) {
            selectByVisibleText(dropdownBrandProduct, brand);
            Thread.sleep(1000);
        }
        if (desc != null) {
            sendKeys(inputDescProduct, desc);
            Thread.sleep(1000);
        }
        if (rating != null) {
            sendKeys(inputRatingProduct, rating);
            Thread.sleep(1000);
        }
        if (image != null) {
            removeClassHidden(inputImageProduct);
            sendKeys(inputImageProduct, image);
            Thread.sleep(1000);
        }
        click(btnUpdateProduct);

    }

    By errorName = By.xpath("//p[contains(text(),'Tên sản phẩm không được để trống')]");
    By errorPrice = By.xpath("//p[contains(text(),'Giá phải lớn hơn 0')]");
    By errorQuantity = By.xpath("//p[contains(text(),'Số lượng lớn hơn 0')]");
    By errorCategory = By.xpath("//p[contains(text(),'Vui lòng chọn danh mục')]");
    By errorBrand = By.xpath("//p[contains(text(),'Vui lòng chọn thương hiệu')]");
    By errorImages = By.xpath("//p[contains(text(),'Vui lòng chọn ít nhất 1 hình ảnh')]");

    public boolean isErrorDisplayed(String errorType) {
        By locator;
        switch (errorType) {
            case "missing_name":
                locator = By.xpath("//p[contains(text(),'Tên sản phẩm không được để trống')]");
                break;
            case "missing_price":
                locator = By.xpath("//p[contains(text(),'Giá phải lớn hơn 0')]");
                break;
            case "missing_quantity":
                locator = By.xpath("//p[contains(text(),'Số lượng lớn hơn 0')]");
                break;
            case "missing_category":
                locator = By.xpath("//p[contains(text(),'Vui lòng chọn danh mục')]");
                break;
            case "missing_brand":
                locator = By.xpath("//p[contains(text(),'Vui lòng chọn thương hiệu')]");
                break;
            case "missing_images":
                locator = By.xpath("//p[contains(text(),'Vui lòng chọn ít nhất 1 hình ảnh')]");
                break;
            default:
                return false;
        }
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    By btnCLose = By.xpath("//button[@class='text-gray-500 hover:text-gray-700']");

    public void closeModal() {
        click(btnCLose);
    }
}
