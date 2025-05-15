package com.demo.mobile;

import com.demo.mobile.pages.MainPage;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SmokeTests extends BaseTest {

    @Test
    public void appShouldLaunch() {
        Logger log = LoggerFactory.getLogger(this.getClass());
        assertNotNull(driver, "Driver should not be null, App should be launched!");
        log.info("App launched");
    }

    @Test
    // BUG: Test is failing, because of a bug in Main page title - "Currency conerter"
    public void mainScreenAndMainElementsTitlesShouldBeShown() {
        Logger log = LoggerFactory.getLogger(this.getClass());
        MainPage mainPage = new MainPage(driver);

        assertAll("Main screen elements",
                // There is a typo in the title of the Main page. Should be "Currency converter"
                () -> assertEquals("Currency converter", mainPage.mainPageTitleText(),
                        "Expected 'Main Page' title should be 'Currency converter'"),
                () -> assertEquals("Sell", mainPage.sellLabelTitleText(),
                        "Expected 'Sell' label text should be 'Sell'"),
                () -> assertEquals("Receive", mainPage.receivedLabelTitleText(),
                        "Expected 'Receive' label text should be 'Receive'"),
                () -> assertEquals("SUBMIT", mainPage.getSubmitButtonText(),
                        "Expected 'Submit' button text should be 'SUBMIT'"),
                () -> assertEquals("EUR", mainPage.fromCurrencyDropDownTitleText(),
                        "Expected 'From Currency' dropdown text should be 'EUR'"),
                () -> assertEquals("USD", mainPage.toCurrencyDropDownTitleText(),
                        "Expected 'To Currency' dropdown text should be 'USD'")
        );
    }

    @Test
    public void mainScreenInitialValueShouldBeShown() {
        Logger log = LoggerFactory.getLogger(this.getClass());
        MainPage mainPage = new MainPage(driver);

        // Verification for initial 'Balance' value
        String expectedBalance = "1000 EUR";
        String actualBalance = mainPage.getBalanceText();
        assertEquals(expectedBalance, actualBalance,
                "Expected 'Balance' initial value to be: " + expectedBalance +
                        ", but it was: " + actualBalance);

        // Verification for initial 'Input Amount' value
        Double expectedInputAmount = (double) 0;
        Double actualInputAmount = mainPage.getInputAmountValue();
        assertEquals(expectedInputAmount, actualInputAmount,
                "Expected 'Input Amount' initial value to be: " + expectedInputAmount +
                        ", but it was: " + actualInputAmount);

        // Verification for initial 'Received Amount' value
        Double expectedReceivedAmount = (double) 0;
        Double actualReceivedAmount = mainPage.getReceivedAmountValue();
        assertEquals(expectedReceivedAmount, actualReceivedAmount,
                "Expected 'Received Amount' initial value to be: " +  expectedReceivedAmount +
                        ", but it was: " + actualReceivedAmount);
    }
}
