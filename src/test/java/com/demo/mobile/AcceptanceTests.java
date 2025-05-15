package com.demo.mobile;

import com.demo.mobile.pages.ConfirmationDialog;
import com.demo.mobile.pages.MainPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class AcceptanceTests extends BaseTest {

    /*
     TODO:
     Scenario 1:
     User enters 100 Euros and picks US Dollars.
     Clicks Submit button.
     Euro balance is reduced to 900 and
     US Dollars balance is increased by an amount specified in Receive input field
     */

    @Test
    public void userSeesCorrectAmountsBeforeSubmit() {
        Logger log = LoggerFactory.getLogger(this.getClass());
        MainPage mainPage = new MainPage(driver);
        mainPage.setInputAmountValue("100");
        log.info("Main page is opened and amount 100 EUR is entered");

        // Check that Input amount correspond to entered amount
        assertEquals(100.0, mainPage.getInputAmountValue(), "Amount should be 100");

        // Check that Received amount correspond to the exchanged amount
        assertEquals(112.90, mainPage.getReceivedAmountValue(), "Amount should be 112.90");
    }

    @Test
    public void userSeesCorrectBalanceAfterSubmit() {
        Logger log = LoggerFactory.getLogger(this.getClass());
        MainPage mainPage = new MainPage(driver);

        mainPage.setInputAmountValue("100");
        log.info("Enter input amount value fo 100 EUR");

        mainPage.tapSubmitButton();
        log.info("Submit button is pressed");

        ConfirmationDialog confirmationDialog = new ConfirmationDialog(driver);
        confirmationDialog.confirm();
        log.info("Confirmation dialog is pressed");

        // Check that the balance is reduced by 100 EUR (1000 EUR - 100 EUR = 900 EUR)
        String expectedReducedBalanceBalance = "900 EUR";
        String actualReducedBalance = mainPage.getBalanceText();
        Assertions.assertEquals(expectedReducedBalanceBalance, actualReducedBalance,
                "Balance should be reduced to: " + expectedReducedBalanceBalance +
                        " but was: " + actualReducedBalance);

        // TODO: Find a way how to locate the reduced amount element and implement the following step
        // US Dollars balance is increased by an amount specified in Receive input field

        // Check that Input amount get back to initial state -> 0
        Double actualInitialInputAmount = mainPage.getReceivedAmountValue();
        Assertions.assertEquals(0, actualInitialInputAmount,
                "Expected amount to be zero but was: " + actualInitialInputAmount);

        // Check that Received amount get back to initial state -> 0
        Double actualInitialRecievedAmound = mainPage.getReceivedAmountValue();
        assertEquals(0, actualInitialRecievedAmound,
                "Expected amount received: " + actualInitialRecievedAmound);

        // Check that Received amount sign has '+' prefix
        assertTrue(mainPage.getReceivedAmountSign().contains("+"),
                "Amount received should be updated with '+' prefix");

        // Check that Received Currency Value is 'USD'
        assertEquals("USD", mainPage.toCurrencyDropDownTitleText(),
                "Currency to receive should remain USD");
    }

    @Test
    public void userSeeConfirmationDialogAfterSubmit() {
        Logger log = LoggerFactory.getLogger(this.getClass());

        MainPage mainPage = new MainPage(driver);
        log.info("Create Main Page instance");

        mainPage.setInputAmountValue("100");
        log.info("Enter input amount value fo 100 EUR");

        mainPage.tapSubmitButton();
        log.info("Submit button is pressed");

        ConfirmationDialog confirmationDialog = new ConfirmationDialog(driver);
        log.info("Create Confirmation dialog create");

        // Check that Confirmation Dialog message correspond to the submited amount and fees
        String expectedMessage = "You have converted 100.00 EUR to 112.90 USD. Commission Fee - 0.00 EUR.";
        String dialogMessage = confirmationDialog.getMessage();
        Assertions.assertEquals( expectedMessage, dialogMessage,
                "Expected the following text to be displayed: " + expectedMessage + "\n" +
                        "Got the following text to be displayed: " + dialogMessage);

        confirmationDialog.confirm();
        log.info("Confirmation dialog is pressed");
    }
}