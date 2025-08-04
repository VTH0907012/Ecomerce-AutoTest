package pages.Admin;

import org.openqa.selenium.*;
import org.testng.Assert;
import utils.BasePage;

public class CategoryPage extends BasePage {
    public CategoryPage(WebDriver driver) {
        super(driver);
    }

    //Locator
    By btnAddCate = By.xpath("//button[contains(text(),'Thêm mới')]");

    public void openAddCate() {
        click(btnAddCate);
    }

    //Add Category
    By inputNameCate = By.xpath("//input[@placeholder='Nhập tên danh mục']");
    By inputDescCate = By.xpath("//textarea[@placeholder='Nhập mô tả danh mục']");
    By inputImgCate = By.cssSelector("input[type='file']");
    By btnSubmitAddCate = By.xpath("//button[@class='px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition flex items-center']");

    public void addCategory(String name, String desc, String pathimg) throws InterruptedException {
        openAddCate();
        Thread.sleep(1000);
        if (name != null) {
            sendKeys(inputNameCate, name);
            Thread.sleep(500);
        }
        if (desc != null) {
            sendKeys(inputDescCate, desc);
            Thread.sleep(500);
        }
        if (pathimg != null) {
            removeClassHidden(inputImgCate);
            sendKeys(inputImgCate, pathimg);
            Thread.sleep(500);
        }
        click(btnSubmitAddCate);
    }


    // Check Add Success, Search item current add
    By inputSearchCate = By.xpath("//input[@placeholder='Tìm kiếm danh mục...']");
    By btnSubmitUpdateCate = By.xpath("//button[contains(text(),'Cập nhật')]");

    public boolean searchCategory(String name) {
        sendKeys(inputSearchCate, name);
        By table_td_Cate = By.xpath("//td[@class='px-6 py-4 whitespace-nowrap']/div[text()='" + name + "']");
        return isDisplayed(table_td_Cate);
    }

    public void editCategory(String newName, String desc, String pathimg, String oldName) throws InterruptedException {
        // B1: Click vào nút "Chỉnh sửa" của dòng có tên tương ứng
        By editButton = By.xpath("//tbody/tr[td[normalize-space()='" + oldName + "']]//button[@title='Sửa']");
        click(editButton);
        Thread.sleep(1000);

        // B2: Nhập thông tin mới nếu có

        // Sửa tên
        if (newName != null) {
            clear(inputNameCate);
            sendKeys(inputNameCate, newName.isEmpty() ? Keys.SPACE + "" + Keys.BACK_SPACE : newName);
            blur(inputNameCate);
            Thread.sleep(500);
        }

        // Sửa mô tả
        if (desc != null) {
            clear(inputDescCate);
            sendKeys(inputDescCate, desc);
            blur(inputDescCate);
            Thread.sleep(500);
        }

        // Sửa hình ảnh
        if (pathimg != null && !pathimg.trim().isEmpty()) {
            //clear(inputImgCate);
            removeClassHidden(inputImgCate);
            sendKeys(inputImgCate, pathimg);
            blur(inputImgCate);
            Thread.sleep(500);
        }

        //        // B3: Click nút cập nhật
        //        WebElement updateBtn = driver.findElement(btnSubmitUpdateCate);
        //        updateBtn.click();
            click(btnSubmitUpdateCate);
    }

//    public void editCategory(String newName, String desc, String pathimg, String oldName) throws InterruptedException {
//        // B1: Click vào nút "Chỉnh sửa" của dòng có ID hoặc tên tương ứng
//        By editButton = By.xpath("//tbody/tr[td[normalize-space()='" + oldName + "']]//button[@title='Sửa']");
//        click(editButton);
//        Thread.sleep(1000);
//
//        if (newName != null) {
//            clear(inputNameCate);
//            sendKeys(inputNameCate, newName);
//            blur(inputNameCate);
//            Thread.sleep(500);
//        }
//        if (desc != null) {
//            clear(inputDescCate);
//            sendKeys(inputDescCate, desc);
//            blur(inputDescCate);
//            Thread.sleep(500);
//        }
//        if (pathimg != null) {
//            clear(inputImgCate);
//            removeClassHidden(inputImgCate);
//            sendKeys(inputImgCate, pathimg);
//            blur(inputImgCate);
//            Thread.sleep(500);
//        }
//        clickByJS(btnSubmitUpdateCate);
//
//    }

    //Delete Category
    public void deteleCategory(String name) throws InterruptedException {
        Thread.sleep(1000);
        By btnIdDelete = By.xpath("//tbody/tr[td[normalize-space()='" + name + "']]//button[@title='Xóa']");
        click(btnIdDelete);
        Thread.sleep(1000);
        By btnDelete = By.xpath("//button[normalize-space()='Xoá']");
        click(btnDelete);

    }

    By notiError = By.xpath("//div[@class='go2072408551']"); // k nhập tên danh mục

    public boolean isErrorNameDisplayed() {
        return isDisplayed(notiError); // ví dụ ID
    }

    By btnClose = By.xpath("//button[@class='text-gray-500 hover:text-gray-700']");

    public void closeModal() {
        click(btnClose);
    }
}
