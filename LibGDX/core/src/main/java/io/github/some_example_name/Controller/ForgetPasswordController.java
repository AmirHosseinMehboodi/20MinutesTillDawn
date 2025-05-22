package io.github.some_example_name.Controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.Model.App;
import io.github.some_example_name.Model.DatabaseManager;
import io.github.some_example_name.Model.Menus;
import io.github.some_example_name.Model.User;
import io.github.some_example_name.View.ForgetPasswordMenu;
import io.github.some_example_name.View.LoginMenu;
import io.github.some_example_name.View.RegisterMenu;

import java.awt.*;
import java.util.regex.Pattern;

public class ForgetPasswordController {
    public static String handleButton(ForgetPasswordMenu menu) {
//        ForgetPasswordMenu menu = (ForgetPasswordMenu) Menus.ForgetPasswordMenu.screen;

        if(menu.getCheckUsernameButton().isPressed()){
            for(User user : App.getUsers()){
                if(user.getName().equals(menu.getUsername().getText())){
                    return (new RegisterMenu()).getQuestions().getItems().get(user.getQuestionNumber());
                }
            }

            return "Wrong username!";
        }

        if(menu.getSetPasswordButton().isPressed()){
            for(User user : App.getUsers()){
                if(user.getName().equals(menu.getUsername().getText())){
                    if(user.getAnswer().equals(menu.getAnswer().getText())){

                        if(!Pattern.compile("(?=.*[A-Z])(?=.*[0-9])(?=.*[_()*&%$#@])[A-Za-z0-9_()*&%$#@]{8,}").matcher(menu.getPassword().getText()).matches()) {
                            return "Weak Password!";
                        }

                        user.setPassword(menu.getPassword().getText());
                        DatabaseManager.getInstance().updateUser(user);
                        Main.getMain().setScreen(new LoginMenu(false));
                        return null;
                    } else {
                        return "Wrong answer!";
                    }
                }
            }
            return "Wrong password!";
        }


        return null;
    }
}
