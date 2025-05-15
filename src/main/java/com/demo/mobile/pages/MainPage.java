package com.demo.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    public MainPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Main Page - Elements

    // <editor-fold desc="Balance Label Element Interactions">
    private WebElement balanceElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.serheniuk.currencyconversion:id/balance")));
    }

    public String getBalanceText() {
        return balanceElement().getText();
    }

    public Double getBalanceAmount() {
        String[] parts = getBalanceText().split(" ");
        return Double.parseDouble(parts[0]);
    }

    public String getBalanceCurrency() {
        String[] parts = getBalanceText().split(" ");
        return parts[1];
    }
    // </editor-fold>

    // <editor-fold desc="Input Amount Field Element Interactions">
    private WebElement inputAmountElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.serheniuk.currencyconversion:id/amountInput")));
    }

    public Double getInputAmountValue() {
        return Double.parseDouble(inputAmountElement().getText());
    }

    public void setInputAmountValue(String value) {
        WebElement input = inputAmountElement();
        input.clear();
        input.sendKeys(value);
    }
    // </editor-fold>

    // <editor-fold desc="Recieved Amount Field Element Interactions">
    private WebElement receivedAmountElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.serheniuk.currencyconversion:id/amountReceived")));
    }

    public String getReceivedAmountText() {
        return receivedAmountElement().getText();
    }

    public String getReceivedAmountSign() {
        return receivedAmountElement().getText().split(" ")[0];
    }

    public Double getReceivedAmountValue() {
        return Double.parseDouble(receivedAmountElement().getText().split(" ")[1]);
    }
    // </editor-fold>

    // <editor-fold desc="Submit Button Element Interactions">
    private WebElement submitButtonElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.serheniuk.currencyconversion:id/submitButton")));
    }

    public String getSubmitButtonText() {
        return submitButtonElement().getText();
    }

    public void tapSubmitButton() {
        submitButtonElement().click();
    }
    // </editor-fold>

    // <editor-fold desc="Element Titles">
    public String mainPageTitleText() {
        WebElement mainPageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath("//android.widget.TextView[@text='Currency conerter']")));
        return mainPageTitle.getText();
    }

    public String sellLabelTitleText() {
        WebElement mainPageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.serheniuk.currencyconversion:id/sellLabel")));
        return mainPageTitle.getText();
    }

    public String receivedLabelTitleText() {
        WebElement mainPageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.serheniuk.currencyconversion:id/receiveLabel")));
        return mainPageTitle.getText();
    }

    public String fromCurrencyDropDownTitleText() {
        WebElement mainPageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.serheniuk.currencyconversion:id/fromCurrency")));
        return mainPageTitle.getText();
    }

    public String toCurrencyDropDownTitleText() {
        WebElement mainPageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("com.serheniuk.currencyconversion:id/toCurrency")));
        return mainPageTitle.getText();
    }
    // </editor-fold>

}
