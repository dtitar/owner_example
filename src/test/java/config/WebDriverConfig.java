package config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:credentials.properties"})
public interface WebDriverConfig extends Config {

    @Key("browser")
    @DefaultValue("chrome")
    String browser();

    @Key("remoteUrl")
    String remoteUrl();

    @Key("login")
    String login();

    @Key("password")
    String password();
}
