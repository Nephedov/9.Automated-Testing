package page;

import com.codeborne.selenide.SelenideElement;
import data.DataGenerator;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.page;

public class LoginPage {
    @FindBy(css = "[data-test-id='login'] input")
    private SelenideElement loginField;
    @FindBy(css = "[data-test-id='password'] input")
    private SelenideElement passwordField;
    @FindBy(css = "[data-test-id='action-login']")
    private SelenideElement loginButton;


    public VerificationPage validLogIn(DataGenerator.UserInfo user) {
        logIn(user.getLogin(), user.getPassword());
        return page(VerificationPage.class);
    }

    public void logIn(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
    }

}
