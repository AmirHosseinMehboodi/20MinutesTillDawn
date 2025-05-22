package io.github.some_example_name.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import io.github.some_example_name.Main;
import io.github.some_example_name.Model.App;
import io.github.some_example_name.View.*;

public class SettingMenuController {
    public static void handleButton(SettingMenu menu) {
        if(menu.getBackToMainMenu().isPressed()){
            Main.getMain().setScreen(new MainMenu());
        }

        if(menu.getAutoReload().isPressed()){
            if(App.isAutoReload()){
                App.setAutoReload(false);
                menu.getAutoReload().setText("Auto Reload : on");
            } else {
                App.setAutoReload(true);
                menu.getAutoReload().setText("Auto Reload : off");
            }
        }


        if (menu.isChanged()){
            menu.setChanged(false);
            if (menu.getMusics().getSelectedIndex() == 2) {
                App.getMusic().dispose();
                Music music1 = Gdx.audio.newMusic(Gdx.files.internal("Music/Wasteland Combat.mp3"));
                music1.setLooping(true);
                App.setMusic(music1);
                music1.play();
            } else if (menu.getMusics().getSelectedIndex() == 1) {
                App.getMusic().dispose();
                Music music0 = Gdx.audio.newMusic(Gdx.files.internal("Music/Pretty Dungeon.mp3"));
                music0.setLooping(true);
                App.setMusic(music0);
                music0.play();
            }
        }

        if(menu.getMusicVolume().isDragging()){
            App.getMusic().setVolume(menu.getMusicVolume().getValue());
        }

        if(menu.getMusicVolume().isDragging()){
            App.setSFXVolume(menu.getMusicVolume().getValue());
        }
    }
}
