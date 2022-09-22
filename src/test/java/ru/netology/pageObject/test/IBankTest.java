package ru.netology.pageObject.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.pageObject.data.DataHelper;
import ru.netology.pageObject.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class IBankTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void fromCard1ToCard2() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card1 = DataHelper.getCardInfo(authInfo);
        var card2 = DataHelper.getOtherCardInfo(authInfo);
        var balance1 = dashboardPage.getCardBalance(card1);
        var balance2 = dashboardPage.getCardBalance(card2);
        var dashboardPageV2 = dashboardPage.deposit(card2);
        String amount = Integer.toString(Math.abs(balance1) / 2);
        var dashboardPageAgain = dashboardPageV2.cardReplenishment(amount, card1);
        var actual = dashboardPageAgain.getCardBalance(card1);
        var actual2 = dashboardPageAgain.getCardBalance(card2);
        var expected = balance1 - Integer.parseInt(amount);
        var expected2 = balance2 + Integer.parseInt(amount);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    void fromCard2ToCard1() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card1 = DataHelper.getCardInfo(authInfo);
        var card2 = DataHelper.getOtherCardInfo(authInfo);
        var balance1 = dashboardPage.getCardBalance(card1);
        var balance2 = dashboardPage.getCardBalance(card2);
        var dashboardPageV2 = dashboardPage.deposit(card1);
        String amount = Integer.toString(Math.abs(balance2) / 2);
        var dashboardPageAgain = dashboardPageV2.cardReplenishment(amount, card2);
        var actual = dashboardPageAgain.getCardBalance(card1);
        var actual2 = dashboardPageAgain.getCardBalance(card2);
        var expected = balance1 + Integer.parseInt(amount);
        var expected2 = balance2 - Integer.parseInt(amount);
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected2, actual2);
    }

    @Test
    void errorSituation() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var card1 = DataHelper.getCardInfo(authInfo);
        var card2 = DataHelper.getOtherCardInfo(authInfo);
        var balance2 = dashboardPage.getCardBalance(card2);
        var dashboardPageV2 = dashboardPage.deposit(card1);
        String amount = Integer.toString(balance2 + 2);
        dashboardPageV2.cardReplenishmentError(amount, card2);
    }
}
