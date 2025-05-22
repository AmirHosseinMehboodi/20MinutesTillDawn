package io.github.some_example_name.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.some_example_name.Main;
import io.github.some_example_name.Model.App;
import io.github.some_example_name.Model.DatabaseManager;
import io.github.some_example_name.View.LoginMenu;
import io.github.some_example_name.View.MainMenu;
import io.github.some_example_name.View.ProfileMenu;
import com.badlogic.gdx.Input;

import javax.swing.*;
import java.awt.*;
import java.lang.ModuleLayer;
import java.util.Arrays;

public class ProfileMenuController {
    public static String handleButton (ProfileMenu menu) {
        if (menu.getBack().isPressed()) {
            Main.getMain().setScreen(new MainMenu());
        }

        if (menu.getDeleteAccount().isPressed()) {
            if (App.getCurrentUser().equals("guest")) {
                return null;
            }
            Main.getMain().setScreen(new LoginMenu(false));
            DatabaseManager.getInstance().deleteUser(App.getCurrentUser().getName());
            App.getUsers().remove(App.getCurrentUser());
            App.setCurrentUser(null);
        }

        if (menu.getChangeAvatar().isPressed()) {
            if(App.getCurrentUser().getCostumeAvatar() != null) {
                App.getCurrentUser().setCostumeAvatar(null);
            }
            App.getCurrentUser().setAvatarNumber(menu.getButtonGroup().getCheckedIndex());
        }
        
        return null;
    }



    public static void filesDropped(String[] files) {
        for (String filePath : files) {
            FileHandle file = new FileHandle(filePath);

            // Check if it's an image file
            if (isImageFile(file.extension())) {
                try {
                    // Load and validate the image
                    Pixmap pixmap = new Pixmap(file);

                    // Optional: Resize if needed
                    Pixmap resizedPixmap = resizePixmap(pixmap, 128, 128);

                    // Create texture and update avatar
                    Texture avatarTexture = new Texture(resizedPixmap);
//                    System.out.println("Dropped files: " + Arrays.toString(files));
                    App.getCurrentUser().setCostumeAvatar(new Image(avatarTexture));

                    // Clean up
                    pixmap.dispose();
                    if (resizedPixmap != pixmap) {
                        resizedPixmap.dispose();
                    }

                    break; // Only use the first valid image
                } catch (Exception e) {
                    System.err.println("Failed to load image: " + e.getMessage());
                }
            }
        }
    }

    private static boolean isImageFile(String extension) {
        extension = extension.toLowerCase();
        return extension.equals("png") || extension.equals("jpg") ||
            extension.equals("jpeg") || extension.equals("bmp") ||
            extension.equals("gif");
    }

    private static Pixmap resizePixmap(Pixmap original, int width, int height) {
        if (original.getWidth() == width && original.getHeight() == height) {
            return original;
        }

        Pixmap resized = new Pixmap(width, height, original.getFormat());
        resized.drawPixmap(original,
            0, 0, original.getWidth(), original.getHeight(),
            0, 0, width, height);

        return resized;
    }
}
