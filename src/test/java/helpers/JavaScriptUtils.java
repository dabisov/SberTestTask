package helpers;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class JavaScriptUtils {
    public static void scrollToElement(SelenideElement element) {
        executeJavaScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void closeWindow(){
        executeJavaScript("window.close()");
    }
}
