package com.demo.mobile;

import com.demo.mobile.pages.ConfirmationDialog;
import com.demo.mobile.pages.MainPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConversionTests extends BaseTest{

    /*
    TODO:
    Add the following Scenarios
        Acceptance Criteria:
            1.    User can convert to any currency.
            2.    The first five currency exchanges are free of charge but afterwards they're charged 0.7% of the currency being traded.
            3.    The commission fee should be displayed in the message that appears after the conversion (if applicable).
            4.    Balance can’t fall below zero.
            5.    Error Message should displayed if fund is insufficient.

    Add Reporting
     */

    // Test is failing due to bug in the fee calculation
    // This is High severity bug and must be fixed with High Priority
    @Test
    public void userSeesFeesMessageAfterTheFifthTransaction() {
        Logger log = LoggerFactory.getLogger(this.getClass());
        MainPage mainPage = new MainPage(driver);
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(driver);
        String dialogMessage = null;
        String expectedMessageOK =  "You have converted 100.00 EUR to 112.90 USD. Commission Fee - 0.00 EUR.";
        String expectedMessageNOK =  "You have converted 100.00 EUR to 112.90 USD. Commission Fee - 0.70 EUR.";

        for (int i = 0; i < 6; i++) {
            // Enter input amount of 100 EUR
            mainPage.setInputAmountValue("100");
            log.info("Amount to confirm: " + mainPage.getInputAmountValue());

            // Submit the transaction
            mainPage.tapSubmitButton();
            log.info("Submit button is pressed");

            // Read the dialog message
            dialogMessage = confirmationDialog.getMessage();

            // Check the dialog message text
            if (i < 5) {
                // Check for no fees in the first five transactions
                Assertions.assertEquals( expectedMessageOK, dialogMessage,
                        "Expected the following Error message to be displayed: " + expectedMessageOK + "\n" +
                                "However the following message was displayed: " + dialogMessage);
            } else {
                // Check for fee in the sixth transaction
                // BUG: The transaction fee charged should be 0.7% of the currency being traded -> 0.70 EUR, not 100 EUR
                Assertions.assertEquals( expectedMessageNOK, dialogMessage,
                        "Expected the following Error message to be displayed: " + expectedMessageNOK + "\n" +
                                "However the following message was displayed: " + dialogMessage);
            }

            // Close dialog message
            confirmationDialog.confirm();
            log.info("Confirmation dialog is pressed");
        }
    }

    @Test
    public void userSeesThatBalanceCanNotBeBellowZero() {
        Logger log = LoggerFactory.getLogger(this.getClass());
        MainPage mainPage = new MainPage(driver);
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(driver);

        // Enter input amount of 1000 EUR - total balance
        mainPage.setInputAmountValue("1000");
        log.info("Amount to confirm: " + mainPage.getInputAmountValue());

        // Submit the transaction
        mainPage.tapSubmitButton();
        log.info("Submit button is pressed");

        // Check that Operation is allowed
        String expectedMessageOK =  "You have converted 1000.00 EUR to 1129.03 USD. Commission Fee - 0.00 EUR.";
        String actualMessageOK = confirmationDialog.getMessage();
        Assertions.assertEquals( expectedMessageOK, actualMessageOK,
                "Expected the following Error message to be displayed: " + expectedMessageOK + "\n" +
                        "However the following message was displayed: " + actualMessageOK);

        // Close dialog message
        confirmationDialog.confirm();
        log.info("Confirmation dialog is pressed");

        // Check balance is Zero
        assertEquals(0.0, mainPage.getBalanceAmount());

        // Enter input amount of 1000 EUR - total balance
        mainPage.setInputAmountValue("500");
        log.info("Amount to confirm: " + mainPage.getInputAmountValue());

        // Submit the transaction
        mainPage.tapSubmitButton();
        log.info("Submit button is pressed");

        // Check that Operation is not allowed
        String expectedMessageNOK =  "You don't have enough money after pay commission 0.00 EUR.";
        String actualMessageNOK = confirmationDialog.getMessage();
        Assertions.assertEquals( expectedMessageNOK, actualMessageNOK,
                "Expected the following Error message to be displayed: " + expectedMessageNOK + "\n" +
                        "However the following message to was displayed: " + actualMessageNOK);

        // Close dialog message
        confirmationDialog.confirm();
        log.info("Confirmation dialog is pressed");

        // Check balance is not below Zero
        assertEquals(0.0, mainPage.getBalanceAmount());
    }
}