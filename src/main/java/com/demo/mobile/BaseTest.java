package com.demo.mobile;

import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    protected static AndroidDriver driver;  //

    @BeforeEach
    public void setUp() throws MalformedURLException {
        // Set APK file path in Target
        Logger log = LoggerFactory.getLogger(this.getClass());
        String apkPath = System.getProperty("user.dir") + "/target/apk/demo-app.apk";
        File app = new File(apkPath);
        if (!app.exists()) {
            log.info("App does not exist");
            throw new RuntimeException("APK file not found at: " + apkPath);
        }

        // Appium Android Device Settings
        DesiredCapabilities caps = new DesiredCapabilities();
        log.info("Setting up capabilities");

        // Platform Name
        caps.setCapability("platformName", "Android");
        log.info("Setting up Android device capabilities");

        // Android Version
        caps.setCapability("platformVersion", "16");
        log.info("Setting up Android to version: " + caps.getCapability("platformVersion"));

        // Android Device / Emulator Name
        caps.setCapability("deviceName", "emulator-5554");
        log.info("Setting up Android emulator");

        // Driver Name - automation framework UA2
        caps.setCapability("automationName", "UiAutomator2");
        log.info("Setting up Appium framework");

        // App path - Target folder
        caps.setCapability("app", app.getAbsolutePath());
        log.info("Setting up App path");

        // Set main activity
        caps.setCapability("appWaitActivity", "*");
        log.info("Setting up App main activity");

        // Set Launch Time - 60s (due to device full reset)
        caps.setCapability("uiautomator2ServerLaunchTimeout", 60000);
        log.info("Setting up App main activity server launch timeout");

        // Reset Device Settings
        caps.setCapability("fullReset", true);
        log.info("Setting up App to fully reset every time");

        // local host - Android Studio Emulator
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), caps);
        log.info("Setting up Android device location");
    }

    @AfterEach
    public void tearDown() {
        Logger log = LoggerFactory.getLogger(this.getClass());
        if (driver != null) {
            log.info("Tearing down");
            driver.quit();
        }
    }
}
