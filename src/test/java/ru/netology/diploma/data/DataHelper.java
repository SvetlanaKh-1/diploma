package ru.netology.diploma.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private static final Faker faker = new Faker(new Locale("en"));
    private static final Faker fakerRus = new Faker(new Locale("ru"));

    private DataHelper() {
    }

    @Value
    public static class CardInfo {
        public String cardNumber;
        public String Status;

    }

    public static CardInfo getCardStatusApproved() {
        return new CardInfo("4444 4444 4444 4441", "APPROVED");

    }

    public static CardInfo getCardStatusDeclined() {

        return new CardInfo("4444 4444 4444 4442", "DECLINED");
    }

    public static String generateUnregisteredCardNumber() {
        return faker.business().creditCardNumber();
    }

    public static String generateRandomMonth(int month) {
        return LocalDate.now().plusMonths(month).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String generateRandomYear(int year) {
        return LocalDate.now().plusYears(year).format(DateTimeFormatter.ofPattern("yy"));

    }

    public static String generateCardholder(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generateCVC() {
        return faker.numerify("###");
    }

    public static String generateInvalidCardNumber() {
        return faker.numerify("###############");
    }

    public static String generateInvalidPeriod() {
        return faker.numerify("#");
    }

    public static String getZeroValue() {
        return "00";
    }

    public static String getSpecialCharactersName() {
        return "!@№#%$,^.&:~;*()_+=?/<>";
    }

    public static String generateNumberName() {
        return faker.numerify("###############");
    }

    public static String generateInvalidCVC() {
        return faker.numerify("##");
    }

    public static String getZeroCVC() {
        return "000";
    }

    public static String getEmptyField() {
        return "";
    }
}