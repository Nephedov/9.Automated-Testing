package data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;


public class DataGenerator {

    public static UserInfo getMySqlUser(String login) {
        return new UserInfo(login, getPassword(login));
    }


    private static String getPassword(String login) {
        if (login.equals("vasya")) {
            return "qwerty123";
        } else return null;
    }

    public static String getRandomVerificationCode() {
        var faker = new Faker();
        var code = String.valueOf(faker.numerify("######"));
        return code;
    }

    @Value
    public static class UserInfo {
        String login;
        String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Users {
        String id;
        String login;
        String password;
        String status;
    }

}
