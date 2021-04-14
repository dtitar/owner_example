package config;

import org.aeonbits.owner.Config;

public interface WebDriverConfig extends Config {

    @Key("browser")
    @DefaultValue("chrome")
    String browser();

    @Key("remote")
    @DefaultValue("false")
    boolean isRemote();

    @Key("selenoid.domain")
    String selenoidDomain();

    @Key("login")
    String login();

    @Key("password")
    String password();
}
