package com.automation.pages;

import org.openqa.selenium.By;

public class HomePage extends BasePage {

    private final By pageTitle  = By.className("title");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    public boolean isLoaded() {
        return isDisplayed(pageTitle);
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public void logout() {
        click(menuButton);
        click(logoutLink);
    }
}
