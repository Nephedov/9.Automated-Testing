package data;

import data.DataGenerator.Users;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.util.List;

public class MySqlHelper {
    final String url = "jdbc:mysql://localhost:3306/app";
    final String userDb = "app";
    final String userDbPassword = "pass";

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
    public void RemoveDemoData() {
        var runner = new QueryRunner();

        var users = "SELECT * FROM users;";
        var removeFromCards = "DELETE FROM cards WHERE user_id=?;";
        var removeFromAuthCodes = "DELETE FROM auth_codes WHERE user_id=?;";
        var removeFromUsers = "DELETE FROM users WHERE id=?;";

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
    public static String getDbId(DataGenerator.UserInfo user) {
        var conn = new MySqlHelper().getConnection();
        var runner = new QueryRunner();
        var requestSQL = "SELECT id FROM users WHERE login=?;";
        try (conn) {
            String id = runner.query(conn, requestSQL, new ScalarHandler<>(), user.getLogin());
            return id;
        }
    }

    @SneakyThrows
    public static String getVerificationCode(DataGenerator.UserInfo user) {
        var conn = new MySqlHelper().getConnection();
        var runner = new QueryRunner();
        var requestSQL = "SELECT code FROM auth_codes WHERE user_id=? ORDER BY created DESC LIMIT 1;";
        try (conn) {
            String validCode = runner.query(conn, requestSQL, new ScalarHandler<>(), MySqlHelper.getDbId(user));
            return validCode;
        }
    }
}

