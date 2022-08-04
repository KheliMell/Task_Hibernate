package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static String getDBName() {
        return "taskpp.users";
    }

    public static String getURL() {
        return "jdbc:mysql://localhost:3306/taskpp";
    }

    private static final String URL = Util.getURL();
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    //JDBC
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            connection.commit();
        }
        return connection;

    }

    //Hibernate
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(Environment.URL, URL);
            settings.put(Environment.USER, USERNAME);
            settings.put(Environment.PASS, PASSWORD);
            settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

            configuration.setProperties(settings);

            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
