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
        String text = findHelper(card).getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    private SelenideElement findHelper(DataHelper.Card card) {
        String text = "Error";
        SelenideElement findCard = $("");
        while (true) {
            for (SelenideElement element : cards) {
                if (element.getAttribute("data-test-id").equals(card.getId())) {
                    findCard = element;
                    text = "OK";
                    break;
                }
            }
            break;
        }
        if (text == "Error") {
            throw new RuntimeException("Данная карта не найдена");
        } else {
            return findCard;
        }
    }

    public DashboardPageV2 deposit(DataHelper.Card card) {
        findHelper(card).findElement(By.cssSelector("button")).click();
        ;
        return new DashboardPageV2();
    }

}
