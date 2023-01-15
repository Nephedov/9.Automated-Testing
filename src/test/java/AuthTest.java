import com.codeborne.selenide.Selenide;
import data.MySqlHelper;
import data.DataGenerator;
import org.junit.jupiter.api.*;
import page.LoginPage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthTest {
    @AfterAll
    static void setUp() {
        new MySqlHelper().RemoveDemoData();
    }

    @Order(1)
    @Test
    void AuthUserWithDbCodeTest() {
        var dbUserInfo = DataGenerator.getMySqlUser("vasya");

        var loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        var verificationPage = loginPage.validLogIn(dbUserInfo);

        var validCodeFromDb = MySqlHelper.getVerificationCode(dbUserInfo);
        verificationPage.validVerify(validCodeFromDb);
    }

    @Order(2)
    @Test
    void BlockedSystemTest() {
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
        thirdVerificationPage.blockedSystemInvalidVerify(invalidCode);

    }
}

