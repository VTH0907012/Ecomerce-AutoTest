package pages.Admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BasePage;

public class DashboardPage extends BasePage {

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    //Locator Dashboard
    By titleDasboard = By.xpath("//h2[contains(text(),'\uD83D\uDCCA Thống kê doanh thu')]");
    public boolean isAtDashboard() {
        return isDisplayed(titleDasboard);
    }

    //Menu
    By linkCategory = By.xpath("//a[@href='/admin/categories']");

    //CategoryManager
    By titleCategory = By.xpath("//h2[contains(text(),'Quản lý Danh mục')]");
    public void navCategoryManager() throws InterruptedException {
        click(linkCategory);
        Thread.sleep(1000);
    }
    public boolean atCategoryManager() {
        return isDisplayed(titleCategory);
    }

    //ProductManagaer
    By linkProduct = By.xpath("//a[@href='/admin/products']");
    By titleProduct = By.xpath("//h2[contains(text(),'Quản lý Sản phẩm')]");

    public void navProductManager() throws InterruptedException {
        click(linkProduct);
        Thread.sleep(1000);
    }
    public boolean atProductManager() {
        return isDisplayed(titleProduct);
    }

    //OrderManagaer
    By linkOrder = By.xpath("//a[@href='/admin/orders']");
    By titleOrder = By.xpath("//h2[contains(text(),'Quản lý Đơn hàng')]");

    public void navOrderManager() throws InterruptedException {
        click(linkOrder);
        Thread.sleep(1000);
    }
    public boolean atOrderManager() {
        return isDisplayed(titleOrder);
    }
    //UserManagaer
    By linkUser = By.xpath("//a[@href='/admin/users']");
    By titleUser = By.xpath("//h2[contains(text(),'Quản lý Người dùng')]");

    public void navUserManager() throws InterruptedException {
        click(linkUser);
        Thread.sleep(1000);
    }
    public boolean atUserManager() {
        return isDisplayed(titleUser);
    }

    // BlogManagaer
    By linkBlog = By.xpath("//a[@href='/admin/blogs']");
    By titleBlog = By.xpath("//h2[contains(text(),'Quản lý Bài viết')]");

    public void navBlogManager() throws InterruptedException {
        click(linkBlog);
        Thread.sleep(1000);
    }
    public boolean atBlogManager() {
        return isDisplayed(titleBlog);
    }




}
