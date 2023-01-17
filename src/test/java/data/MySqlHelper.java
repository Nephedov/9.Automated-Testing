package data;

import data.DataGenerator.Users;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.util.List;

public class MySqlHelper {
    private final String url = "jdbc:mysql://localhost:3306/app";
    private final String userDb = "app";
    private final String userDbPassword = "pass";
    private final String users = "SELECT * FROM users;";
    private final String removeFromCards = "DELETE FROM cards WHERE user_id=?;";
    private final String removeFromAuthCodes = "DELETE FROM auth_codes WHERE user_id=?;";
    private final String removeFromUsers = "DELETE FROM users WHERE id=?;";

    private static String findIdInDb = "SELECT id FROM users WHERE login=?;";
    private static String findAuthCodeInDb = "SELECT code FROM auth_codes WHERE user_id=? ORDER BY created DESC LIMIT 1;";


    @SneakyThrows
    public java.sql.Connection getConnection() {
        var conn = DriverManager.getConnection(
                url, userDb, userDbPassword
        );
        {
        }
        return conn;
    }

    @SneakyThrows
    public void removeDemoData() {
        var runner = new QueryRunner();

        try (var conn = getConnection()) {
            List<Users> all = runner.query(conn, users, new BeanListHandler<>(Users.class));
            for (Users user : all) {
                if ((user.getLogin().equals("petya")) || (user.getLogin().equals("vasya"))) {
                    var demoId = user.getId();
                    runner.execute(conn, removeFromCards, demoId);
                    runner.execute(conn, removeFromAuthCodes, demoId);
                    runner.execute(conn, removeFromUsers, demoId);

                }
            }
        }
    }

    @SneakyThrows
    public void clearAuthCodesDemo() {
        var runner = new QueryRunner();

        try (var conn = getConnection()) {
            List<Users> all = runner.query(conn, users, new BeanListHandler<>(Users.class));
            for (Users user : all) {
                if ((user.getLogin().equals("petya")) || (user.getLogin().equals("vasya"))) {
                    var demoId = user.getId();
                    runner.execute(conn, removeFromAuthCodes, demoId);

                }
            }
        }
    }

    @SneakyThrows
    public static String getDbId(DataGenerator.UserInfo user) {
        var conn = new MySqlHelper().getConnection();
        var runner = new QueryRunner();

        try (conn) {
            String id = runner.query(conn, findIdInDb, new ScalarHandler<>(), user.getLogin());
            return id;
        }
    }

    @SneakyThrows
    public static String getVerificationCode(DataGenerator.UserInfo user) {
        var conn = new MySqlHelper().getConnection();
        var runner = new QueryRunner();

        try (conn) {
            String validCode = runner.query(conn, findAuthCodeInDb, new ScalarHandler<>(), MySqlHelper.getDbId(user));
            return validCode;
        }
    }
}

