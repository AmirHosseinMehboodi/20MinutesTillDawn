package io.github.some_example_name.Controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.Model.App;
import io.github.some_example_name.View.*;

public class MainMenuController {

    public static void handleButton(MainMenu mainMenu) {
        if(mainMenu.getNewGame().isPressed()){
            Main.getMain().setScreen(new PreGameMenu());
        }


        if(mainMenu.getScoreboard().isPressed()){
            Main.getMain().setScreen(new ScoreBoardMenu());
        }

        if(mainMenu.getSetting().isPressed()){
            Main.getMain().setScreen(new SettingMenu());
        }

        if(mainMenu.getProfile().isPressed()){
            Main.getMain().setScreen(new ProfileMenu());
        }

        if(mainMenu.getLogout().isPressed()){
            App.setCurrentUser(null);
            Main.getMain().setScreen(new LoginMenu(false));
        }

        if(mainMenu.getHint().isPressed()){
            Main.getMain().setScreen(new HintMenu());
        }
    }

}
