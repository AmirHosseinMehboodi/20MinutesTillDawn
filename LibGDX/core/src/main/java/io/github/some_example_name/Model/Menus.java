package io.github.some_example_name.Model;

import com.badlogic.gdx.Screen;
import io.github.some_example_name.View.*;

public enum Menus {
    LoginMenu(new LoginMenu(false)),
    MainMenu(new MainMenu()),
    PauseMenu(new PauseMenu()),
    PreGameMenu(new PreGameMenu()),
    ProfileMenu(new ProfileMenu()),
    RegisterMenu(new RegisterMenu()),
    ScoreBoardMenu(new ScoreBoardMenu()),
    SettingsMenu(new SettingMenu()),
    ForgetPasswordMenu(new ForgetPasswordMenu()),
    TalentMenu(new HintMenu());

    public final Screen screen;

    Menus(Screen screen) {
        this.screen = screen;
    }
}
