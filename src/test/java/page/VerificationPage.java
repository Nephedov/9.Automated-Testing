package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id='code'] input");
    private SelenideElement verifyButton = $("[data-test-id='action-verify']");

    private SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public VerificationPage() {
        codeField.shouldBe(Condition.visible);
    }

    private void verify(String code) {
        codeField.setValue(code);
        verifyButton.click();
    }

    public DashboardPage validVerify(String code) {
        verify(code);
        return new DashboardPage();
    }

    public VerificationPage invalidVerify(String code) {
        verify(code);
        errorNotification.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверно указан код! Попробуйте ещё раз."));
        return this;
    }

    public LoginPage blockedSystemInvalidVerify(String code) {
        verify(code);
        errorNotification.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Превышено количество попыток ввода"));
        return page(LoginPage.class);
    }

}
