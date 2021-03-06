package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        List<User> usersToAdd = List.of(
            new User("Portia", "Reader", (byte) 33),
            new User("Aalia", "Brandt", (byte) 43),
            new User("Sukhmani", "Simpson", (byte) 54),
            new User("Zeynep", "Schofield", (byte) 22)
        );

        userService.createUsersTable();

        for (User u : usersToAdd) {
            userService.saveUser(u.getName(), u.getLastName(), u.getAge());
            System.out.printf("User с именем - %s добавлен в базу данных%n", u.getName());
        }

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
