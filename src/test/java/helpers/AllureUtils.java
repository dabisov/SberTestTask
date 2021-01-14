package helpers;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;

import java.util.UUID;

public class AllureUtils {

    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] attachScreenshot(byte[] screenshot){
        return screenshot;
    }

    public static void createStep(String message, Status status) {
        String uuid = UUID.randomUUID().toString();
        StepResult result = new StepResult().setName(message).setStatus(status);
        Allure.getLifecycle().startStep(uuid, result);
        Allure.getLifecycle().stopStep(uuid);
    }
}
