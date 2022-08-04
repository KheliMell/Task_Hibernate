package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;


public class Main {
    public static String getDBName() {
        return "taskpp.users";
    }

    public static String getURL() {
        return "jdbc:mysql://localhost:3306/taskpp";
    }
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Atrem", "Novikov", (byte) 12);
        userService.saveUser("Petr", "Petrov", (byte) 32);
        userService.saveUser("Ольга", "Nekrasova", (byte) 45);
        userService.saveUser("Константин", "Горбулин", (byte) 21);

        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            System.out.println(user);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
