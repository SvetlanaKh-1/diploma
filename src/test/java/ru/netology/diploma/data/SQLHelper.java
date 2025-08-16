package ru.netology.diploma.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    @SneakyThrows
    public static String getCardPayment() {
        var connection = getConnection();
        var codeSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        return QUERY_RUNNER.query(connection, codeSQL, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getCreditPayment() {
        var connection = getConnection();
        var codeSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        return QUERY_RUNNER.query(connection, codeSQL, new ScalarHandler<>());
    }

    @SneakyThrows
    public static void cleanBase() {
        var connection = getConnection();
        QUERY_RUNNER.execute(connection, "DELETE FROM credit_request_entity");
        QUERY_RUNNER.execute(connection, "DELETE FROM order_entity");
        QUERY_RUNNER.execute(connection, "DELETE FROM payment_entity");
    }
}