package connect;

import data.UserDb;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.DriverManager;
import java.util.List;

public class ConnectionMySql {
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
            List<UserDb> all = runner.query(conn, users, new BeanListHandler<>(UserDb.class));
            for (UserDb user : all) {
                if ((user.getLogin().equals("petya")) || (user.getLogin().equals("vasya"))) {
                    var demoId = user.getId();
                    runner.execute(conn, removeFromCards, demoId);
                    runner.execute(conn, removeFromAuthCodes, demoId);
                    runner.execute(conn, removeFromUsers, demoId);

                }
            }
        }
    }
}

