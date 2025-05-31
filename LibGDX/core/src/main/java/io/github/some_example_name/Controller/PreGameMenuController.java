package io.github.some_example_name.Controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.Model.Game;
import io.github.some_example_name.Model.Map;
import io.github.some_example_name.View.GameScreen;
import io.github.some_example_name.View.MainMenu;
import io.github.some_example_name.View.PreGameMenu;

public class PreGameMenuController {
    public static void handleButton(PreGameMenu menu) {
        if(menu.getStartGame().isPressed()) {
            Main.getMain().setScreen(new GameScreen(new Game(menu.getHero().getSelected(),menu.getGun().getSelected(),menu.getTime().getSelected())));
        }

        if(menu.getBack().isPressed()) {
            Main.getMain().setScreen(new MainMenu());
        }
    }


}
