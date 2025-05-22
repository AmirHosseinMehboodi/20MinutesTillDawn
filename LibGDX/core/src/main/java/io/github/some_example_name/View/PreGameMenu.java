package io.github.some_example_name.View;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.github.some_example_name.Model.GameAssetManager;

import javax.swing.text.View;

public class PreGameMenu implements Screen {

    private Stage stage;
    private TextButton playButton;
    private Label gameTitle;
    private TextField field;
    public Table table;

    public PreGameMenu() {
        Skin skin = GameAssetManager.getSkin();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
