package pages.Admin;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import utils.BasePage;

public class BlogPage extends BasePage {

    public BlogPage(WebDriver driver) {
        super(driver);
    }

    //Locator
    By btnAddBLog = By.xpath("//button[contains(text(),'Thêm mới')]");

    public void openAddBlog() {
        click(btnAddBLog);
    }

    //Add Blog
    By inputTitleBlog = By.xpath("//input[@placeholder='Nhập tiêu đề blog']");
    By inputContentBlog = By.xpath("//textarea[@placeholder='Nhập nội dung blog']");
    By inputImgBlog = By.cssSelector("input[type='file']");
    By inputPublishBlog = By.xpath("//input[@id='publishCheckbox']");
    By btnSubmitAddBlog = By.xpath("//button[@class='px-4 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition flex items-center']");

    public void addBlog(String title, String content, String pathimg, boolean isselect) throws InterruptedException {
        openAddBlog();

        Thread.sleep(1000);
        if (title != null) {
            sendKeys(inputTitleBlog, title);
            Thread.sleep(500);
        }
        if (content != null) {
            sendKeys(inputContentBlog, content);
            Thread.sleep(500);
        }
        if (pathimg != null && !pathimg.trim().isEmpty()) {
            removeClassHidden(inputImgBlog);
            sendKeys(inputImgBlog, pathimg);
            Thread.sleep(500);
        }
        if (isselect == false) {
            click(inputPublishBlog);
        }
        click(btnSubmitAddBlog);
    }


    // Check Add Success, Search item current add
    By inputSearchBLog = By.xpath("//input[@placeholder='Tìm kiếm bài viết...']");
    By btnSubmitUpdateBlog = By.xpath("//button[contains(text(),'Cập nhật')]");

    public boolean searchBlog(String title) {
        sendKeys(inputSearchBLog, title);
        By table_td_Blog = By.xpath("//td[@class='px-6 py-4']/div[text()='" + title + "']");
        return isDisplayed(table_td_Blog);
    }

    public void editBlog(String title, String content, String pathimg, Boolean isselect, String title_edit) throws InterruptedException {
        // B1: Click vào nút "Chỉnh sửa" của dòng có ID hoặc tên tương ứng
        By editButton = By.xpath("//tbody/tr[td[@class='px-6 py-4']/div[text()='" + title_edit + "']]//button[@title='Sửa']");
        click(editButton);
        Thread.sleep(1000);


        if (title != null) {
            //sendKeys(inputTitleBlog, title);
            sendKeys(inputTitleBlog, title.isEmpty() ? Keys.SPACE + "" + Keys.BACK_SPACE : title);

            Thread.sleep(500);
        }
        if (content != null) {
            //sendKeys(inputContentBlog, content);
            sendKeys(inputContentBlog, content.isEmpty() ? Keys.SPACE + "" + Keys.BACK_SPACE : content);

            Thread.sleep(500);
        }
        if (pathimg != null && !pathimg.trim().isEmpty()) {
            removeClassHidden(inputImgBlog);
            sendKeys(inputImgBlog, pathimg);
            Thread.sleep(500);
        }
        if (isselect == false) {
            click(inputPublishBlog);
        }

        // B3: Click nút lưu
        click(btnSubmitUpdateBlog);
    }

    //Delete Category
    public void deteleBlog(String title) throws InterruptedException {
        Thread.sleep(1000);
        By btnIdBlog = By.xpath("//tbody/tr[td[@class='px-6 py-4']/div[text()='" + title + "']]//button[@title='Xóa']");
        click(btnIdBlog);
        Thread.sleep(1000);
        By btnDelete = By.xpath("//button[normalize-space()='Xoá']");
        click(btnDelete);

    }


    public boolean isErrorTitleDisplayed() {
        return isDisplayed(By.xpath("//p[contains(text(),'Tiêu đề là bắt buộc!')]"));
    }

    public boolean isErrorContentDisplayed() {
        return isDisplayed(By.xpath("//p[contains(text(),'Nội dung là bắt buộc')]"));
    }

    public boolean isErrorImageDisplayed() {
        return isDisplayed(By.xpath("//p[contains(text(),'Hình ảnh là bắt buộc')]"));
    }

    public void closeModal() {
        click(By.xpath("//button[@class='text-gray-500 hover:text-gray-700']"));
    }

}
