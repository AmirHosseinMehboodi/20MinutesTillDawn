package io.github.some_example_name.Model;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;

public class App {
    private static ArrayList<Screen> menu = new ArrayList<>();
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser;
    private static boolean autoReload = true;
    private static Music music;
    private static float SFXVolume = 0.5f;


    public static ArrayList<Screen> getMenu() {
        return menu;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }


    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        App.currentUser = currentUser;
    }


    public static boolean isAutoReload() {
        return autoReload;
    }

    public static void setAutoReload(boolean autoReload) {
        App.autoReload = autoReload;
    }

    public static Music getMusic() {
        return music;
    }

    public static void setMusic(Music music) {
        App.music = music;
    }

    public static float getSFXVolume() {
        return SFXVolume;
    }

    public static void setSFXVolume(float SFXVolume) {
        App.SFXVolume = SFXVolume;
    }
}
