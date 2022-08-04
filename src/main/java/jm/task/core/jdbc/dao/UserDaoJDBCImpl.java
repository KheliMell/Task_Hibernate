package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {

            String query = "CREATE TABLE " + Util.getDBName() +
                    "(`id` INT NOT NULL AUTO_INCREMENT," +
                    " `name` VARCHAR(45) ," +
                    " `lastName` VARCHAR(45) ," +
                    " `age` TINYINT(0) UNSIGNED ," +
                    " PRIMARY KEY (`id`))";

            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("Что-то пошло не так! [СОЗДАНИЕ ТАБЛИЦЫ]");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {

            String query = "DROP TABLE " + Util.getDBName();

            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("Что-то пошло не так! [УДАЛЕНИЕ ТАБЛИЦЫ]");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = Util.getConnection().createStatement()) {

            String query = "INSERT INTO " + Util.getDBName() + " (name, lastName, age) values ('" + name + "', '" + lastName + "', '" + age + "')";

            statement.execute(query);

            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Что-то пошло не так! [СОХРАНЕНИЕ ПОЛЬЗОВАТЕЛЕЙ]");
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = Util.getConnection().createStatement()) {

            String query = "DELETE FROM " + Util.getDBName() + " WHERE id = " + id;

            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("Что-то пошло не так! [УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ]");
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Statement statement = Util.getConnection().createStatement()) {

            String query = "SELECT * FROM " + Util.getDBName();

            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setLastName(result.getString("lastName"));
                user.setAge(result.getByte("age"));
                userList.add(user);
            }


        } catch (SQLException e) {
            System.out.println("Что-то пошло не так! [ПОЛУЧЕНИЕ СПИСКА ПОЛЬЗОВАТЕЛЕЙ]");
        }

        return userList;
    }

    public void cleanUsersTable() {
        try {
            Util.getConnection().rollback();
//            String query = "DELETE FROM " + Util.getDBName();
//            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("Что-то пошло не так! [ОТЧИСТКА ТАБЛИЦЫ]");
        }
    }
}
