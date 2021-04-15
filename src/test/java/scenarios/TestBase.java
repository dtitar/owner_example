package scenarios;

import com.codeborne.selenide.Configuration;
import config.WebDriverConfig;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static utils.Attachments.*;

@Slf4j
public class TestBase {

    @BeforeAll
    static void setup() {
        final WebDriverConfig config = ConfigFactory.create(WebDriverConfig.class);

        addListener("AllureSelenide", new AllureSelenide());

        Configuration.startMaximized = true;
//        Configuration.browser = config.browser();
        Configuration.browser = System.getProperty("browser");
//        if (config.remoteUrl() != null) {
        if (System.getProperty("remoteUrl()") != null) {
            setupRemoteTestExecution(config.remoteUrl(), config.login(), config.password());
        }
    }

    @AfterEach
    void afterEach() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        if (System.getProperty("video.storage") != null)
            attachVideo();
        closeWebDriver();
    }

    private static void setupRemoteTestExecution(String remoteUrl, String login, String password) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
        Configuration.remote = String.format(remoteUrl, login, password);
    }
}
