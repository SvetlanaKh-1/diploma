package ru.netology.diploma.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ApplicationPage {
    private final SelenideElement heading = $(withText("Путешествие дня"));
    private final SelenideElement purchaseButton = $(withText("Купить"));
    private final SelenideElement purchaseCreditButton = $(withText("Купить в кредит"));
    private final SelenideElement headingPurchase = $(withText("Оплата по карте"));
    private final SelenideElement headingPurchaseInCredit = $(withText("Кредит по данным карты"));

    private final SelenideElement cardNumberField = $$(".input__inner").findBy(text("Номер карты")).$(".input__control");
    private final SelenideElement monthField = $$(".input__inner").findBy(text("Месяц")).$(".input__control");
    private final SelenideElement yearField = $$(".input__inner").findBy(text("Год")).$(".input__control");
    private final SelenideElement cardholderField = $$(".input__inner").findBy(text("Владелец")).$(".input__control");
    private final SelenideElement codeField = $$(".input__inner").findBy(text("CVC/CVV")).$(".input__control");
    private final SelenideElement continueButton = $$(".button__content").findBy(text("Продолжить"));

    private final SelenideElement approvedOperation = $$(".notification__content").findBy(text("Операция одобрена Банком."));
    private final SelenideElement declinedOperation = $$(".notification__content").findBy(text("Ошибка! Банк отказал в проведении операции."));
    private final SelenideElement errorFormat = $$(".input__inner").findBy(text("Неверный формат"));
    private final SelenideElement emptyField = $$(".input__inner").findBy(text("Поле обязательно для заполнения"));
    private final SelenideElement errorIncorrectPeriod = $$(".input__inner").findBy(text("Неверно указан срок действия карты"));
    private final SelenideElement errorExpiredCard = $$(".input__inner").findBy(text("Истёк срок действия карты"));

    public void applicationPage(String expectedText) {
        heading.shouldBe(Condition.visible);
    }

    public void selectPurchase(String expectedText) {
        purchaseButton.click();
        headingPurchase.shouldHave(exactText(expectedText)).shouldBe(visible);
    }

    public void selectPurchaseInCredit(String expectedText) {
        purchaseCreditButton.click();
        headingPurchaseInCredit.shouldHave(exactText(expectedText)).shouldBe(visible);
    }

    public void fillCardForm(String cardNumber, String month, String year, String cardholder, String code) {
        cardNumberField.setValue(cardNumber);
        monthField.setValue(month);
        yearField.setValue(year);
        cardholderField.setValue(cardholder);
        codeField.setValue(code);
        continueButton.click();
    }

    public void verifyApprovedStatus() {
        approvedOperation.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void verifyDeclinedStatus() {
        declinedOperation.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void verifyErrorFormatNotification() {
        errorFormat.shouldBe(visible);
    }

    public void verifyEmptyFieldsNotification() {
        emptyField.shouldBe(visible);
    }

    public void verifyErrorIncorrectPeriodNotification() {
        errorIncorrectPeriod.shouldBe(visible);
    }

    public void verifyErrorExpiredCardNotification() {
        errorExpiredCard.shouldBe(visible);
    }
}