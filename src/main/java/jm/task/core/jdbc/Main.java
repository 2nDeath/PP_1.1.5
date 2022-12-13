package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.model.User;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        UserServiceImpl userService= new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Pavel", "Povalikhin", (byte)55);
        userService.saveUser("Maxim", "Turkov", (byte)17);
        userService.saveUser("Artem", "Val", (byte)11);
        userService.saveUser("Roman", "Strizh", (byte)35);

        users = userService.getAllUsers();
        for(User user : users) {
            System.out.println(user.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
