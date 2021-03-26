import { AppPage } from "./app.po";
import { browser, by, element } from "protractor";

describe("workspace-project App", () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it("should display title of application", () => {
    page.navigateTo();
    expect(browser.getTitle()).toEqual("MuzixApplication");
  });

  it("should be redirected to /login route", () => {
    browser.element(by.css(".register-button")).click();
    expect(browser.getCurrentUrl()).toContain("/register");
    browser.driver.sleep(2000);
  });
  it("Should be able to register user", () => {
    browser.element(by.id("username")).sendKeys("test123");
    browser.element(by.id("email")).sendKeys("test123");
    browser.element(by.id("password")).sendKeys("test123");
    browser.element(by.css(".register-user")).click();
    // expect(browser.getCurrentUrl()).toContain('/login');
    browser.driver.sleep(1000);
  });

  it("Should be able to login user", () => {
    browser.element(by.id("username")).sendKeys("test123");

    browser.element(by.id("password")).sendKeys("test123");
    browser.element(by.css(".login-click")).click();
    // expect(browser.getCurrentUrl()).toContain('/login');
    browser.driver.sleep(1000);
  });

  it("should be able to click on Menu item for India", () => {
    browser.element(by.css(".mat-button")).click();
    browser.driver.sleep(1000);
    browser.element(by.css(".mat-menu-item-india")).click();
    expect(browser.getCurrentUrl()).toContain("/India");
    browser.driver.sleep(1000);
  });
  it("should be able to save Indian track to wishlist", () => {
    browser.driver
      .manage()
      .window()
      .maximize();
    browser.driver.sleep(1000);
    const tracks = element.all(by.css(".example-card"));
    browser.driver.sleep(1000);
    browser.element(by.css(".addbutton")).click();
    browser.driver.sleep(1000);
  });
  it("should be able to click on Menu item for Spain", () => {
    browser.element(by.css(".mat-button")).click();
    browser.element(by.css(".mat-menu-item-spain")).click();
    expect(browser.getCurrentUrl()).toContain("/Spain");
    browser.driver.sleep(1000);
  });
  it("should be able to save Spain track to wishlist", () => {
    browser.driver
      .manage()
      .window()
      .maximize();
    browser.driver.sleep(1000);
    const tracks = element.all(by.css(".example-card"));
    browser.driver.sleep(500);
    browser.element(by.css(".addbutton")).click();
    browser.driver.sleep(500);
  });
  it("should be able to get all data from WishList", () => {
    browser.driver.sleep(1000);
    browser.element(by.css(".mat-button-wishlist")).click();
    expect(browser.getCurrentUrl()).toContain("/WishList");
    browser.driver.sleep(1000);
  });

  it("should be able to delete data from WishList", () => {
    browser.driver.sleep(1000);
    const tracks = element.all(by.css(".example-card"));
    browser.sleep(1000);
    browser.element(by.css(".deletebutton")).click();
    browser.driver.sleep(1000);
  });
  it("should be able to open dialogbox to update comments from WishList", () => {
    browser.driver.sleep(500);
    const tracks = element.all(by.css(".example-card"));
    browser.sleep(500);
    browser.element(by.css(".updatebutton")).click();
    browser.driver.sleep(1000);
  });

  it("should be able to save updated comments to WishList", () => {
    browser.driver.sleep(500);
    browser.element(by.css(".matInput")).sendKeys("New comments");
    browser.driver.sleep(1000);
    browser.element(by.css(".updateCommentdemo")).click();
    browser.driver.sleep(1000);
  });

  it("should be able to logout from application", () => {
    browser.driver.sleep(500);
    browser.element(by.css(".mat-button-logout")).click();
    browser.driver.sleep(500);
  });
});
