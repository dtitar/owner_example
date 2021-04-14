package scenarios;

import com.codeborne.selenide.Configuration;
import config.WebDriverConfig;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static utils.Attachments.*;

public class TestBase {
    private static final WebDriverConfig config = ConfigFactory.create(WebDriverConfig.class);

    @BeforeAll
    static void setup() {

        addListener("AllureSelenide", new AllureSelenide());

        Configuration.startMaximized = true;
        Configuration.browser = config.browser();
        if (config.isRemote()) {
            setupRemoteTestExecution();
        }
    }

    @AfterEach
    void afterEach() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        attachVideo();
        closeWebDriver();
    }

    private static void setupRemoteTestExecution() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
        Configuration.remote = String.format("https://%s:%s@%s/wd/hub/", config.login(), config.password(), config.selenoidDomain());
    }
}
