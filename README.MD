
# 📱 Mobile Automation Demo – Currency Converter App

This project showcases automated UI testing for a sample Android mobile application (Currency Converter) using **Appium**, **Selenium**, and **JUnit 5**.  
It demonstrates best practices in mobile test automation architecture using Java and Maven.

---

## 🛠 Tech Stack

- Java 11
- Appium Java Client `8.6.0`
- Selenium `4.25.0`
- JUnit 5
- Maven
- Allure (for reporting)

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java SDK: OpenJDK 11+
- Android SDK & Android Emulator
- Appium Server (v2+)
- Maven (3.6+)
- IntelliJ IDEA or similar IDE

### 🔄 Clone the Project

```bash
git clone https://github.com/martiness/mobile-automation-demo.git
cd mobile-automation-demo
```

---

## 📱 APK Handling

Place your `.apk` file in:

```
src/test/resources/apk/demo-app.apk
```

During the Maven `process-test-resources` phase, the file will be **automatically copied** to `target/` and used in test execution.

> ⚠️ The `.apk` file is excluded from version control via `.gitignore`.

---

## 🧪 Running Tests

Make sure Appium Server is running and the Android emulator is started.

```bash
# Run all tests
mvn clean test
```

### 🧾 Generate Allure Report (optional)

```bash
# View test report
allure serve target/allure-results
```

---
## 🗂 Project Structure

```
mobile-automation-demo/
├── src/
│   └── test/
│       ├── java/
│       │   └── com/demo/tests/       # Test classes
│       └── resources/
│           └── apk/                  # Local APK file (ignored by Git)
├── target/                           # Maven build output
├── pom.xml                           # Maven configuration
└── README.md                         # Project overview
```

---

## 📂 Test Structure

- **Smoke Tests** – `SmokeTests.java`
- **Acceptance Tests** – `AcceptanceTests.java`
- **Validation Tests** – `ValidationTests.java`
- **Conversion Tests** – `ConversionTests.java`

---

## 🧩 Features

- Test execution via Appium with custom capabilities
- Local `.apk` usage, excluded from GitHub
- Clear Maven structure with plugin automation
- Example of best practices in mobile test automation

---

## 🧪 Notes

- Appium server must be running at `http://localhost:4723/`
- Ensure emulator or real device is active before running tests
- Keep library versions in sync to avoid runtime issues

---

## 🐞 Known Bugs (Intentionally Left for Demo Purposes)

| Test Class & Method | Severity | Description |
|---------------------|----------|-------------|
| `ConversionTests.userSeesFeesMessageAfterTheFifthTransaction` | High | Commission fee is displayed as 100.00 EUR instead of 0.70 EUR |
| `ValidationTests.userSeesNoBalanceChangesInCaseOfInsufficientFunds` | Medium | Input field retains invalid value (1100) instead of resetting to 0.0 |
| `SmokeTests.mainScreenAndMainElementsTitlesShouldBeShown` | Low | Title contains typo: "Currency conerter" instead of "Currency converter" |

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).

---

## 🤝 Contact

Created by **[Martin Kenov]**  
For more, visit [GitHub/martiness](https://github.com/martiness)


