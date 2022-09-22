package ru.netology.pageObject.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.openqa.selenium.By;
import ru.netology.pageObject.data.DataHelper;

import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private SelenideElement heading = $("h1");
    private ElementsCollection cards = $$(".list__item div");
    //private SelenideElement reloadButton = $("button[data-test-id='action-reload']");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldHave(Condition.text("Ваши карты"), Condition.visible);
    }

    public int getCardBalance(DataHelper.Card card) {
        String text = cards.findBy(Condition.attribute("data-test-id", card.getId())).getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public DashboardPageV2 deposit(DataHelper.Card card) {
        cards.findBy(Condition.attribute("data-test-id", card.getId()))
                .findElement(By.cssSelector("button")).click();
        ;
        return new DashboardPageV2();
    }

}
