package tests;

import com.codeborne.selenide.Configuration;
import helpers.*;
import helpers.DataProvider;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.NotebookPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class MarketTests {

    private HomePage homePage;
    private ArrayList<Notebook> notebooks;

    @BeforeClass
    public void homePage() {
        Configuration.browser = "chrome";
        notebooks = new ArrayList<>();
        homePage = new HomePage();
        homePage.toRegionSelectionForm()
                .setRegion("Сан")
                .toCatalogMenu()
                .moveToCategory()
                .toNotebooks();
    }

    @Test(description = "Тест страниц", dataProvider = "data-provider", dataProviderClass = DataProvider.class)
    public void marketTest(Manufacturer manufacturer, int upperPrice) throws IOException {
        homePage
                .showFullList()
                .selectNotebookFromList(manufacturer.getName());

        Assert.assertTrue(manufacturer.getMax() <= upperPrice);

        NotebookPage notebookPage = homePage
                .sortList()
                .selectBorders(manufacturer.getMin(), manufacturer.getMax())
                .selectNotebook()
                .toCharacteristics()
                .takeScreenshot(manufacturer.getName());

        notebooks.add(new Notebook(
                notebookPage.getName(),
                notebookPage.getDiagonal(),
                notebookPage.getWeight(notebookPage.getName())
        ));

        Assert.assertEquals(notebookPage.getManufacturer(), manufacturer.getName());
    }

    @AfterMethod
    public void shutdownMethod(){
        JavaScriptUtils.closeWindow();
        switchTo().window(0);
        $(byXpath("//span[contains(@class,'17C4Le-0TB')]")).click();
    }

    @AfterClass
    public void shutdown() {
        Utils.saveNotebookToXml(new Notebook(Collections.max(notebooks)), "notebook.xml");
        closeWebDriver();
    }

}
