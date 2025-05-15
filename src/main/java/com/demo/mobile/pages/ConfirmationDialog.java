package com.demo.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ConfirmationDialog {

    private AndroidDriver driver;
    private WebDriverWait wait;

    public ConfirmationDialog(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getMessage() {
        WebElement messageText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("android:id/message")));
        return messageText.getText();
    }

    public void confirm() {
        WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(
                AppiumBy.id("android:id/button1")));
        okButton.click();
    }
}
