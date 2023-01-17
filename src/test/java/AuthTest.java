import com.codeborne.selenide.Selenide;
import data.MySqlHelper;
import data.DataGenerator;
import org.junit.jupiter.api.*;
import page.LoginPage;

public class AuthTest {
    @AfterEach
    void clearAuthCodeTable() {
        new MySqlHelper().clearAuthCodesDemo();
    }
    @AfterAll
    static void setUp() {
        new MySqlHelper().removeDemoData();
    }

    @Test
    void authUserWithDbCodeTest() {
        var dbUserInfo = DataGenerator.getMySqlUser("vasya");

        var loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        var verificationPage = loginPage.validLogIn(dbUserInfo);

        var validCodeFromDb = MySqlHelper.getVerificationCode(dbUserInfo);
        verificationPage.validVerify(validCodeFromDb);
    }

    @Test
    void blockedSystemTest() {
        var dbUserInfo = DataGenerator.getMySqlUser("vasya");
        var invalidCode = DataGenerator.getRandomVerificationCode();

        var firstAuth = Selenide.open("http://localhost:9999", LoginPage.class);
        var firstVerificationPage = firstAuth.validLogIn(dbUserInfo);
        firstVerificationPage.invalidVerify(invalidCode);

        var secondAuth = Selenide.open("http://localhost:9999", LoginPage.class);
        var secondVerificationPage = secondAuth.validLogIn(dbUserInfo);
        secondVerificationPage.invalidVerify(invalidCode);

        var thirdAuth = Selenide.open("http://localhost:9999", LoginPage.class);
        var thirdVerificationPage = thirdAuth.validLogIn(dbUserInfo);
        thirdVerificationPage.invalidVerify(invalidCode);

        var fourthAuth = Selenide.open("http://localhost:9999", LoginPage.class);
        var fourthAuthVerificationPage = fourthAuth.validLogIn(dbUserInfo);
        fourthAuthVerificationPage.blockedSystemInvalidVerify(invalidCode);

    }
}

