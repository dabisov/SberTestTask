package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.google.common.io.Files;
import helpers.AllureUtils;
import helpers.Utils;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;


import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class NotebookPage {

    public NotebookPage() {
        switchTo().window(1);
    }

    @Step("Перейти в характеристики")
    public NotebookPage toCharacteristics() {
        $(byXpath("//span[text()='Характеристики']"))
                .shouldBe(visible).click();
        return this;
    }

    @Step("Сделать скриншот страницы")
    public NotebookPage takeScreenshot(String product) throws IOException {
        Selenide.screenshot(product);
        AllureUtils.attachScreenshot(Files.toByteArray(
                new File("build/reports/tests/" + product + ".png")));
        return this;
    }

    @Step("Получить имя")
    public String getName() {
        return $(byXpath("//h1[@data-tid='c0924aa2']"))
                .shouldBe(visible).text();
    }


    @Step("Получить диагональ экрана ноутбука")
    public double getDiagonal() {
        SelenideElement diagonalElement = $(byXpath("//span[text()='Диагональ экрана']/../../../dd"));
        SelenideElement screenElement = $(byXpath("//span[text()='Экран']/../../../dd"));
        if (diagonalElement.isDisplayed()) {
            return Utils.parseToDouble(diagonalElement.text());
        }
        return Utils.parseToDouble(screenElement.text());
    }


    @Step("Получить вес ноутбука")
    public double getWeight(String name) throws IOException {
        if ($(byXpath("//span[text()='Вес']/../../../dd")).isDisplayed()) {
            AllureUtils.createStep("Проверить вес ноутбука", Status.PASSED);
            return Utils.parseToDouble($(byXpath("//span[text()='Вес']/../../../dd"))
                    .shouldBe(visible).text());
        } else {
            AllureUtils.createStep("Проверить вес ноутбука", Status.BROKEN);
            Selenide.screenshot(name);
            AllureUtils.attachScreenshot(Files.toByteArray(
                    new File("build/reports/tests/" + name + ".png")));
            return 0d;
        }
    }

    @Step("Сравнить производителя с 'product'")
    public NotebookPage checkProduct(String product) {
        $(byXpath("//ul[@itemtype='https://schema.org/BreadcrumbList']/li[2]/div/a/span"))
                .shouldBe(visible).shouldHave(text(product));
        return this;
    }
}
