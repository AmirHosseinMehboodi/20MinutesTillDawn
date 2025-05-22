package io.github.some_example_name.Controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.Model.App;
import io.github.some_example_name.Model.Menus;
import io.github.some_example_name.Model.User;
import io.github.some_example_name.View.ForgetPasswordMenu;
import io.github.some_example_name.View.LoginMenu;
import io.github.some_example_name.View.MainMenu;
import io.github.some_example_name.View.RegisterMenu;

import java.awt.*;

public class LoginMenuController {
    public static String handleButton(LoginMenu loginMenu) {

        if(loginMenu.getPlayButton().isPressed()){
            for(User user : App.getUsers()){
                if(user.getName().equals(loginMenu.getUsername().getText())){
                    if(user.getPassword().equals(loginMenu.getPassword().getText())){
                        App.setCurrentUser(user);
                        Main.getMain().setScreen(new MainMenu());
                        return null;
                    } else {
                        return "Wrong Password!";
                    }
                }
            }

            return "Wrong username!";
        }

        if(loginMenu.getPlayAsGuestButton().isPressed()){
            App.setCurrentUser(App.getUsers().get(0));
            Main.getMain().setScreen(new MainMenu());
            return null;
        }


        if(loginMenu.getForgetPasswordButton().isPressed()){
            Main.getMain().setScreen(new ForgetPasswordMenu());
            return null;
        }

        if(loginMenu.getRegisterButton().isPressed()){
            Main.getMain().setScreen(new RegisterMenu());
            return null;
        }

        return null;
    }
}
