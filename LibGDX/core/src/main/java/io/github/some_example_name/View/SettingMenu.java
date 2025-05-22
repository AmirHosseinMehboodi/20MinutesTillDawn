package io.github.some_example_name.View;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.some_example_name.Controller.LoginMenuController;
import io.github.some_example_name.Controller.SettingMenuController;
import io.github.some_example_name.Main;
import io.github.some_example_name.Model.App;
import io.github.some_example_name.Model.DatabaseManager;
import io.github.some_example_name.Model.GameAssetManager;
import io.github.some_example_name.Model.User;

import java.util.ArrayList;


public class SettingMenu implements Screen {
    private Stage stage;
    private Image backgroundLeft;
    private Image backgroundRight;
    private Image backgroundTop;

    private Texture openEyes;
    private Texture semiCloseEyes;
    private Texture closeEyes;
    private Image eyeImage;

    private Slider musicVolume;
    private Slider sfxVolume;

    private Label musicVolumeLabel;
    private Label sfxVolumeLabel;


    private SelectBox musics;
    private boolean changed = false;

    private TextButton autoReload;
    private TextButton noColor;
    private TextButton backToMainMenu;

    public SettingMenu() {
        Skin skin = GameAssetManager.getSkin();
        this.backgroundLeft = new Image(new Texture(Gdx.files.internal("Texture2D/T_TitleLeaves.png")));
        backgroundLeft.setSize(Gdx.graphics.getWidth()/3 - 30, Gdx.graphics.getHeight());

        this.backgroundRight = new Image(new Texture(Gdx.files.internal("Texture2D/T_TitleLeaves.png")));
        backgroundRight.setSize(Gdx.graphics.getWidth()/3 - 30, Gdx.graphics.getHeight());
        backgroundRight.setScaleX(-1);
        backgroundRight.setPosition(Gdx.graphics.getWidth(), 0);

        this.backgroundTop = new Image(new Texture(Gdx.files.internal("Texture2D/T_20Logo.png")));
        backgroundTop.setSize((float) (Gdx.graphics.getWidth() / 2) - 180f , (float) ( Gdx.graphics.getHeight() / 2)- 40f) ;
        backgroundTop.setPosition(Gdx.graphics.getWidth()/ 4 + 90, 520);

        openEyes = new Texture("Sprite/T_EyeBlink_0.png");
        semiCloseEyes = new Texture("Sprite/T_EyeBlink_1.png");
        closeEyes = new Texture("Sprite/T_EyeBlink_2.png");

        musicVolume = new Slider(0.0f,1.0f,0.1f,false,skin);
        musicVolume.setValue(App.getMusic().getVolume());
        sfxVolume = new Slider(0.0f,1.0f,0.1f,false,skin);
        sfxVolume.setValue(App.getSFXVolume());

        musicVolumeLabel = new Label("Music Volume", skin);
        musicVolumeLabel.setColor(new Color(253/255f, 81/255f, 97/255f, 1f));
        musicVolumeLabel.setFontScale(1.5f);
        sfxVolumeLabel = new Label("SFX Volume", skin);
        sfxVolumeLabel.setColor(new Color(253/255f, 81/255f, 97/255f, 1f));
        sfxVolumeLabel.setFontScale(1.5f);

        musics = new SelectBox(skin);
        musics.setItems("select music ...","Pretty Dungeon","Wasteland Combat");
        musics.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                changed = true;
            }
        });

        if(App.isAutoReload()){
            autoReload = new TextButton("Auto Reload : on" , skin);
        } else {
            autoReload = new TextButton("Auto Reload : off", skin);
        }

        noColor = new TextButton("Colorful : on", skin);

        backToMainMenu = new TextButton("Back to Main Menu", skin);

    }

    @Override
    public void show() {
        startBlinkLoop();

        stage = new Stage(new FitViewport(1920,1080));
        Gdx.input.setInputProcessor(stage);

        stage.addActor(backgroundLeft);
        stage.addActor(backgroundRight);
        stage.addActor(backgroundTop);

        eyeImage = new Image(openEyes);
        eyeImage.setPosition(560, 340);
        eyeImage.setSize(800, 250);
        stage.addActor(eyeImage);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.setPosition(0,-230);


        table.add(musicVolumeLabel);
        table.row();

        table.add(musicVolume).width(400);
        table.row().pad(5,0,5,0);

        table.add(musics).width(500);
        table.row();

        table.add(sfxVolumeLabel);
        table.row();

        table.add(sfxVolume).width(400);
        table.row().pad(5,0,5,0);


        table.add(autoReload).height(100);
        table.row().pad(5,0,5,0);


        table.add(noColor).height(100);
        table.row().pad(5,0,5,0);

        table.add(backToMainMenu).height(100);
        table.row().pad(5,0,5,0);

        stage.addActor(table);

    }

    private void startBlinkLoop() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                blinkAnimation();
            }
        }, 2, 3 + (float)Math.random() * 2);
    }

    private void blinkAnimation() {
        eyeImage.setDrawable(new Image(semiCloseEyes).getDrawable());

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                eyeImage.setDrawable(new Image(closeEyes).getDrawable());

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        eyeImage.setDrawable(new Image(semiCloseEyes).getDrawable());

                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                eyeImage.setDrawable(new Image(openEyes).getDrawable());
                            }
                        }, 0.1f);
                    }
                }, 0.1f);
            }
        }, 0.1f);
    }






    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glClearColor(39/255f, 32/255f, 48/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        SettingMenuController.handleButton(this);
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

    public Slider getMusicVolume() {
        return musicVolume;
    }

    public Slider getSfxVolume() {
        return sfxVolume;
    }

    public Label getMusicVolumeLabel() {
        return musicVolumeLabel;
    }

    public Label getSfxVolumeLabel() {
        return sfxVolumeLabel;
    }

    public SelectBox getMusics() {
        return musics;
    }

    public TextButton getAutoReload() {
        return autoReload;
    }

    public TextButton getNoColor() {
        return noColor;
    }

    public TextButton getBackToMainMenu() {
        return backToMainMenu;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}

