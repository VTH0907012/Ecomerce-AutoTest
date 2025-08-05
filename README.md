# 🛒 E-commerce Automation Test Suite (Java + Selenium + TestNG)

This repository contains an automated end-to-end testing suite for an e-commerce website using **Java**, **Selenium WebDriver**, **TestNG**, and **ExtentReports**.

👉 **Live Website Under Test:**  
🔗 [https://vercel.com/thanh-hieus-projects/ecommerce-electronics](https://vercel.com/thanh-hieus-projects/ecommerce-electronics)

---

## 🧪 Tech Stack

- **Java 17**
- **Selenium WebDriver 4.34.0**
- **TestNG 7.11.0**
- **ExtentReports 5.1.1**
- **WebDriverManager 6.2.0**
- **Apache POI** (for reading/writing Excel files)
- **Commons IO** (file handling)
- **Maven** for dependency management

## 🧰 Features

- ✅ Modular TestNG test classes (login, cart, checkout, etc.)
- ✅ Page Object Model (POM) design pattern
- ✅ End-to-end checkout flow test
- ✅ ExtentReports for beautiful HTML reports
- ✅ Data-driven testing support via Excel (Apache POI)
- 🛠️ Screenshots on test failure *(in progress)*
- 🛠️ Logs with SLF4J *(in progress)*

## 🚀 How to Run

### Prerequisites

- Java 17+
- Maven 3+
- Chrome browser (latest)
- Internet connection (for WebDriverManager to download drivers)


### 🔁 Run all tests
Chạy toàn bộ các test bằng Maven:
```bash
mvn clean test
```

---

### 📂 Run by module using `testng.xml`
Bạn có thể cấu hình file `testng.xml` để chạy theo **module** hoặc **test suite** tùy ý:
```xml
<suite name="E2E Suite">
  <test name="Cart Test">
    <classes>
      <class name="tests.CartTest"/>
    </classes>
  </test>
</suite>
```
Chạy bằng lệnh:
```bash
mvn test -DsuiteXmlFile=testng.xml
```

---

### 🧪 Run single test method
Bạn cũng có thể chạy trực tiếp một method cụ thể trong một class (IDE như IntelliJ hoặc Eclipse):

Ví dụ: Trong class `LoginTest.java` có method `testInvalidLogin()`, bạn có thể:
- Right-click vào method → Run
- Hoặc dùng annotation group/tag (nếu có)

---

### 📊 Report Location
Sau khi chạy test, bạn có thể xem báo cáo tại:
```
/test-output/ExtentReport.html
```

---

## 🧑‍💻 Author

**Võ Thanh Hiếu**  
📧 Contact: thanhhieu@gmail.com
---

## 📄 License

This project is licensed under the MIT License.
