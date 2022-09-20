package ru.netology.pageObject.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.pageObject.data.DataHelper;

import static com.codeborne.selenide.Selenide.*;

public class DashboardPageV2 {
    private SelenideElement title = $("h1");
    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");

    public DashboardPageV2() {
        //title.shouldHave(text("Пополнение карты"), Condition.visible);
    }

    public DashboardPage cardReplenishment(String amount, DataHelper.Card card) {
        amountField.setValue(amount);
        fromField.setValue(card.getCard());
        transferButton.click();
        return new DashboardPage();
    }
}
