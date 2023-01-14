package data;

import com.github.javafaker.Faker;
import connect.ConnectionMySql;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;


public class DataGenerator {
    public static UserInfo getMySqlUser(String login) {
        return new UserInfo(getDbId(login), login, getHardPassword(login));
    }

    @SneakyThrows
    private static String getDbId(String login) {
        var conn = new ConnectionMySql().getConnection();
        var runner = new QueryRunner();
        var requestSQL = "SELECT id FROM users WHERE login=?;";
        try (conn) {
            String id = runner.query(conn, requestSQL, new ScalarHandler<>(), login);
            return id;
        }
    }

    private static String getHardPassword(String login) {
        if (login.equals("vasya")) {
            return "qwerty123";
        } else return null;
    }

    @Value
    public static class UserInfo {
        String id;
        String login;
        String password;
    }

    public static class VerificationCodeDb {

        @SneakyThrows
        public static String getCodeFromMySql(DataGenerator.UserInfo user) {
            var conn = new ConnectionMySql().getConnection();
            var runner = new QueryRunner();
            var requestSQL = "SELECT code FROM auth_codes WHERE user_id=? AND created > NOW() - INTERVAL 10 SECOND;";
            try (conn) {
                String validCode = runner.query(conn, requestSQL, new ScalarHandler<>(), user.getId());
                return validCode;
            }
        }

        public static String getRandomCode() {
            var faker = new Faker();
            var code = String.valueOf(faker.random().nextInt(100000, 999999));
            return code;
        }
    }
}
