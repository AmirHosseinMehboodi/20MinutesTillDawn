package io.github.some_example_name.Controller;

import com.badlogic.gdx.Screen;
import io.github.some_example_name.Main;
import io.github.some_example_name.Model.App;
import io.github.some_example_name.Model.Menus;
import io.github.some_example_name.Model.User;
import io.github.some_example_name.View.MainMenu;
import io.github.some_example_name.View.RegisterMenu;

import java.util.Random;
import java.util.regex.Pattern;

public class RegisterMenuController {
    public static String handleButton(RegisterMenu registerMenu) {

        if(registerMenu.getPlayButton().isPressed()) {
            if(!Pattern.compile("(?=.*[A-Z])(?=.*[0-9])(?=.*[_()*&%$#@])[A-Za-z0-9_()*&%$#@]{8,}").matcher(registerMenu.getPassword().getText()).matches()) {
                return "Weak Password!";
            }

            for(User user : App.getUsers()) {
                if(user.getName().equals(registerMenu.getUsername().getText())) {
                    return "This username is already in use!";
                }
            }

            Random rand = new Random();

            User user = new User(registerMenu.getUsername().getText()
                ,registerMenu.getPassword().getText()
                ,registerMenu.getQuestions().getSelectedIndex()
                ,registerMenu.getSecurityQuestion().getText()
                , rand.nextInt(5));
            user.addDB();

            App.getUsers().add(user);
            App.setCurrentUser(user);
            Main.getMain().setScreen(new MainMenu());
        }

        if(registerMenu.getPlayAsGuestButton().isPressed()) {
            App.setCurrentUser(App.getUsers().get(0));
            Main.getMain().setScreen(new MainMenu());
        }

        return null;
    }
}
