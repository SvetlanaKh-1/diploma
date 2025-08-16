package ru.netology.diploma.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.diploma.data.DataHelper;
import ru.netology.diploma.data.SQLHelper;
import ru.netology.diploma.page.ApplicationPage;

import static com.codeborne.selenide.Selenide.open;

public class PurchaseOnCreditTest {
    ApplicationPage applicationPage;
    String approvedCardNumber = DataHelper.getCardStatusApproved().getCardNumber();
    String declinedCardNumber = DataHelper.getCardStatusDeclined().getCardNumber();
    String validMonth = DataHelper.generateRandomMonth(1);
    String validYear = DataHelper.generateRandomYear(1);
    String validCardholder = DataHelper.generateCardholder("en");
    String validCVC = DataHelper.generateCVC();

    @BeforeEach
    public void setUp() {
        applicationPage = open("http://localhost:8080/", ApplicationPage.class);
        applicationPage.applicationPage("Путешествие дня");
        applicationPage.selectPurchaseInCredit("Кредит по данным карты");
    }

    @AfterEach
    public void shouldCleanBase() {
        SQLHelper.cleanBase();
    }

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Should successful card payment with status approved")
    void theCardPaymentShouldBeApproved() {
        applicationPage.fillCardForm(approvedCardNumber, validMonth, validYear, validCardholder, validCVC);
        applicationPage.verifyApprovedStatus();
        Assertions.assertEquals("APPROVED", SQLHelper.getCreditPayment());
    }

    @Test
    @DisplayName("Should card payment with status declined")
    void theCardPaymentShouldBeDeclined() {
        applicationPage.fillCardForm(declinedCardNumber, validMonth, validYear, validCardholder, validCVC);
        applicationPage.verifyDeclinedStatus();
        Assertions.assertEquals("DECLINED", SQLHelper.getCreditPayment());
    }

    @Test
    @DisplayName("Should unsuccessful card payment with unregistered card")
    void unregisteredCardPayment() {
        var unregisteredCardNumber = DataHelper.generateUnregisteredCardNumber();
        applicationPage.fillCardForm(unregisteredCardNumber, validMonth, validYear, validCardholder, validCVC);
        applicationPage.verifyDeclinedStatus();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with invalid card number")
    void invalidCardNumberPayment() {
        var invalidCardNumber = DataHelper.generateInvalidCardNumber();
        applicationPage.fillCardForm(invalidCardNumber, validMonth, validYear, validCardholder, validCVC);
        applicationPage.verifyErrorFormatNotification();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with empty form")
    void emptyFormPayment() {
        applicationPage.verifyEmptyFieldsNotification();
    }

    @Test
    @DisplayName("Should successful card payment with current period")
    void currentPeriodCardPayment() {
        var currentMonth = DataHelper.generateRandomMonth(0);
        var currentYear = DataHelper.generateRandomYear(0);
        applicationPage.fillCardForm(approvedCardNumber, currentMonth, currentYear, validCardholder, validCVC);
        applicationPage.verifyApprovedStatus();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with expired card month")
    void expiredCardMonthPayment() {
        var expiredMonth = DataHelper.generateRandomMonth(-1);
        var currentYear = DataHelper.generateRandomYear(0);
        applicationPage.fillCardForm(approvedCardNumber, expiredMonth, currentYear, validCardholder, validCVC);
        applicationPage.verifyErrorIncorrectPeriodNotification();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with expired card year")
    void expiredCardYearPayment() {
        var currentMonth = DataHelper.generateRandomMonth(0);
        var expiredYear = DataHelper.generateRandomYear(-1);
        applicationPage.fillCardForm(approvedCardNumber, currentMonth, expiredYear, validCardholder, validCVC);
        applicationPage.verifyErrorExpiredCardNotification();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with invalid card month")
    void invalidMonthPayment() {
        var invalidMonth = DataHelper.generateInvalidPeriod();
        applicationPage.fillCardForm(approvedCardNumber, invalidMonth, validYear, validCardholder, validCVC);
        applicationPage.verifyErrorFormatNotification();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with invalid card year")
    void invalidYearPayment() {
        var invalidYear = DataHelper.generateInvalidPeriod();
        applicationPage.fillCardForm(approvedCardNumber, validMonth, invalidYear, validCardholder, validCVC);
        applicationPage.verifyErrorFormatNotification();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with invalid card month - 00")
    void invalidZeroMonthPayment() {
        var invalidZeroMonth = DataHelper.getZeroValue();
        applicationPage.fillCardForm(approvedCardNumber, invalidZeroMonth, validYear, validCardholder, validCVC);
        applicationPage.verifyErrorFormatNotification();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with invalid card year - 00")
    void invalidZeroYearPayment() {
        var invalidZeroYear = DataHelper.getZeroValue();
        applicationPage.fillCardForm(approvedCardNumber, validMonth, invalidZeroYear, validCardholder, validCVC);
        applicationPage.verifyErrorExpiredCardNotification();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with cyrillic cardholder")
    void cyrillicCardholderPayment() {
        var cyrillicCardholder = DataHelper.generateCardholder("ru");
        applicationPage.fillCardForm(approvedCardNumber, validMonth, validYear, cyrillicCardholder, validCVC);
        applicationPage.verifyErrorFormatNotification();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with special characters in cardholder")
    void specialCharactersCardholderPayment() {
        var specialCharactersCardholder = DataHelper.getSpecialCharactersName();
        applicationPage.fillCardForm(approvedCardNumber, validMonth, validYear, specialCharactersCardholder, validCVC);
        applicationPage.verifyErrorFormatNotification();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with numbers in cardholder")
    void numbersCardholderPayment() {
        var numbersCardholder = DataHelper.generateNumberName();
        applicationPage.fillCardForm(approvedCardNumber, validMonth, validYear, numbersCardholder, validCVC);
        applicationPage.verifyErrorFormatNotification();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with invalid CVC")
    void invalidCVCPayment() {
        var invalidCVC = DataHelper.generateInvalidCVC();
        applicationPage.fillCardForm(approvedCardNumber, validMonth, validYear, validCardholder, invalidCVC);
        applicationPage.verifyErrorFormatNotification();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with zero value CVC")
    void zeroValueCVCPayment() {
        var zeroValuedCVC = DataHelper.getZeroCVC();
        applicationPage.fillCardForm(approvedCardNumber, validMonth, validYear, validCardholder, zeroValuedCVC);
        applicationPage.verifyErrorFormatNotification();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with empty card number")
    void emptyCardNumberPayment() {
        var emptyCardNumber = DataHelper.getEmptyField();
        applicationPage.fillCardForm(emptyCardNumber, validMonth, validYear, validCardholder, validCVC);
        applicationPage.verifyEmptyFieldsNotification();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with empty month")
    void emptyMonthPayment() {
        var emptyMonth = DataHelper.getEmptyField();
        applicationPage.fillCardForm(approvedCardNumber, emptyMonth, validYear, validCardholder, validCVC);
        applicationPage.verifyEmptyFieldsNotification();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with empty year")
    void emptyYearPayment() {
        var emptyYear = DataHelper.getEmptyField();
        applicationPage.fillCardForm(approvedCardNumber, validMonth, emptyYear, validCardholder, validCVC);
        applicationPage.verifyEmptyFieldsNotification();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with empty cardholder")
    void emptyCardholderPayment() {
        var emptyCardholder = DataHelper.getEmptyField();
        applicationPage.fillCardForm(approvedCardNumber, validMonth, validYear, emptyCardholder, validCVC);
        applicationPage.verifyEmptyFieldsNotification();
    }

    @Test
    @DisplayName("Should unsuccessful card payment with empty CVC")
    void emptyCVCPayment() {
        var emptyCVC = DataHelper.getEmptyField();
        applicationPage.fillCardForm(approvedCardNumber, validMonth, validYear, validCardholder, emptyCVC);
        applicationPage.verifyEmptyFieldsNotification();
    }
}
