package pages;


import com.codeborne.selenide.Condition;

import com.codeborne.selenide.SelenideElement;
import helpers.JavaScriptUtils;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class HomePage {

    public HomePage(){
        open("https://market.yandex.ru");
    }

    @Step("Перейти к выбору региона")
    public HomePage toRegionSelectionForm() {
        $(byAttribute("title", "Регион"))
                .shouldBe(visible).click();
        return this;
    }

    @Step("Выбрать новый город по text")
    public HomePage setRegion(String text){
        $(byAttribute("placeholder","Укажите другой регион"))
                .shouldBe(Condition.visible).setValue(text);
        $(byAttribute("class", "hKCM_OMVVX"))
                .shouldBe(visible).click();
        $(byXpath("//span[text()='Продолжить с новым регионом']"))
                .shouldBe(visible).click();
        return this;
    }

    @Step("Перейти в меню 'Каталог товаров'")
    public HomePage toCatalogMenu(){
        $(byXpath("//span[text()='Каталог']"))
                .shouldBe(visible).click();
        return this;
    }

    @Step("Навести на категорию 'Компьютерная техника'")
    public HomePage moveToCategory(){

        $(byXpath("//span[text()='Компьютерная техника']"))
                .shouldBe(visible).hover();
        return this;

    }

    @Step("Выбрать раздел 'Ноутбуки'")
    public HomePage toNotebooks(){

        $(byXpath("//a[text()='Ноутбуки']"))
                .shouldBe(visible).click();
        return this;

    }

    @Step("Показать весь список производителей")
    public HomePage showFullList(){
        $(byXpath("//button[text()='Показать всё']"))
                .shouldBe(visible).click();
        return this;

    }

    @Step("Выбрать производителя")
    public HomePage selectNotebookFromList(String text) {
        $(byAttribute("name", "Поле поиска"))
                .shouldBe(visible).setValue(text);
        $(byXpath("//span[text()='" + text + "']"))
                .shouldBe(visible).click();
        return this;
    }

    @Step("Выбрать диапазон значений")
    public HomePage selectBorders(int min, int max){
        $(byAttribute("name", "Цена от"))
                .shouldBe(visible).setValue(String.valueOf(min));
        $(byAttribute("name", "Цена до"))
                .shouldBe(visible).setValue(String.valueOf(max));
        return this;
    }

    @Step("Отсортировать список по рейтингу")
    public HomePage sortList(){
        $(byAttribute("data-autotest-id","quality"))
                .shouldBe(visible).click();
        return this;
    }

    @Step("Выбрать ноутбук")
    public NotebookPage selectNotebook(){
        SelenideElement element = $$(byXpath("//span[@data-tid='ce80a508']")).get(3);
        JavaScriptUtils.scrollToElement(element);
        element.click();
        return new NotebookPage();
    }
}
