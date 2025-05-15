package com.demo.mobile;

import com.demo.mobile.pages.ConfirmationDialog;
import com.demo.mobile.pages.MainPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Currency Conversion")
@Feature("Validation")
@Story("Insufficient Funds Error")
@Severity(SeverityLevel.CRITICAL)
public class ValidationTests extends BaseTest {

    @Test
    public void userSeesErrorMessageInCaseOfInsufficientFunds() {
        Logger log = LoggerFactory.getLogger(this.getClass());

        MainPage mainPage = new MainPage(driver);
        log.info("Created 'Main page'");

        mainPage.setInputAmountValue("1100");
        log.info("Entered amount for input 1100 EUR");

        mainPage.tapSubmitButton();
        log.info("Submitted amount for input 1100 EUR");

        ConfirmationDialog confirmationDialog = new ConfirmationDialog(driver);
        log.info("Created 'Confirmation dialog'");

        // Check that Confirmation Dialog - Error message is displayed, because of the insufficient funds
        String expectedMessage = "You don't have enough money after pay commission 0.00 EUR.";
        String dialogMessage = confirmationDialog.getMessage();
        Assertions.assertEquals( expectedMessage, dialogMessage,
                "Expected the following Error message to be displayed: " + expectedMessage + "\n" +
                        "However the following message was displayed: " + dialogMessage);

        confirmationDialog.confirm();
        log.info("Closed 'Confirmation dialog'");
    }

    // Test is failing because of function bugs
    // Values of Input and Received amounts should be zero after closing the error message
    // However they are keeping their values
    @Test
    @DisplayName("User sees error message when funds are insufficient")
    public void userSeesNoBalanceChangesInCaseOfInsufficientFunds() {
        Logger log = LoggerFactory.getLogger(this.getClass());
        MainPage mainPage = new MainPage(driver);

        mainPage.setInputAmountValue("1100");
        log.info("Created 'Main page'");

        mainPage.tapSubmitButton();
        log.info("Submitted amount for input 1100 EUR");

        ConfirmationDialog confirmationDialog = new ConfirmationDialog(driver);
        log.info("Created 'Confirmation dialog'");

        // Add Allure attachments
        Allure.addAttachment("Error Dialog Message", confirmationDialog.getMessage());
        attachScreenshot("Insufficient Funds Dialog");

        confirmationDialog.confirm();
        log.info("Closed 'Confirmation dialog'");

        // Verification for no change in 'Balance' value
        String expectedBalance = "1000 EUR";
        String actualBalance = mainPage.getBalanceText();
        assertEquals(expectedBalance, actualBalance,
                "Expected 'Balance' value to be: " + expectedBalance +
                        ", but it was: " + actualBalance);


        // Verification for initial 'Input Amount' value
        // BUG: Input amount value should return to initial state after closing the Error message
        Double expectedInputAmount = (double) 0; // 1100
        Double actualInputAmount = mainPage.getInputAmountValue();
        assertEquals(expectedInputAmount, actualInputAmount,
                "Expected 'Input Amount' initial value to be: " + expectedInputAmount +
                        ", but it was: " + actualInputAmount);


        // Verification for initial 'Received Amount' value
        // BUG: Received amount value should return to initial state after closing the Error message
        Double expectedReceivedAmount = (double) 0; // 1241.93
        Double actualReceivedAmount = mainPage.getReceivedAmountValue();
        assertEquals(expectedReceivedAmount, actualReceivedAmount,
                "Expected 'Received Amount' initial value to be: " +  expectedReceivedAmount +
                        ", but it was: " + actualReceivedAmount);
    }

    @Test
    public void userSeesErrorMessageInCaseSubmitZeroAmount() {
        Logger log = LoggerFactory.getLogger(this.getClass());
        MainPage mainPage = new MainPage(driver);

        mainPage.setInputAmountValue("0");
        log.info("Created 'Main page'");

        mainPage.tapSubmitButton();
        log.info("Submitted amount for input '0' EUR");

        ConfirmationDialog confirmationDialog = new ConfirmationDialog(driver);
        log.info("Created 'Confirmation dialog'");

        // Check that Confirmation Dialog - Error message is displayed, because of the insufficient funds
        String expectedMessage = "You have converted 0.00 EUR to 0.00 USD. Commission Fee - 0.00 EUR.";
        String dialogMessage = confirmationDialog.getMessage();
        Assertions.assertEquals( expectedMessage, dialogMessage,
                "Expected the following Error message to be displayed: " + expectedMessage + "\n" +
                        "However the following message was displayed: " + dialogMessage);

        confirmationDialog.confirm();
        log.info("Closed 'Confirmation dialog'");

        // Verification for no change in 'Balance' value
        String expectedBalance = "1000 EUR";
        String actualBalance = mainPage.getBalanceText();
        assertEquals(expectedBalance, actualBalance,
                "Expected 'Balance' value to be: " + expectedBalance +
                        ", but it was: " + actualBalance);
    }

    @Attachment(value = "{screenshotName}", type = "image/png")
    private byte[] attachScreenshot(String screenshotName) {
        File src = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
        try (InputStream is = new FileInputStream(src)) {
            return is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
