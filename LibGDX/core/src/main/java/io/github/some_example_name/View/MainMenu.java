package io.github.some_example_name.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.some_example_name.Controller.ForgetPasswordController;
import io.github.some_example_name.Controller.MainMenuController;
import io.github.some_example_name.Main;
import io.github.some_example_name.Model.App;
import io.github.some_example_name.Model.GameAssetManager;

import javax.swing.text.View;

public class MainMenu implements Screen {
    private Stage stage;
    private Image backgroundLeft;
    private Image backgroundRight;
    private Image backgroundTop;

    private Texture openEyes;
    private Texture semiCloseEyes;
    private Texture closeEyes;
    private Image eyeImage;

    private Image avatar;
    private Image backgroundAvatar;
    private Label username;
    private Label score;

    private TextButton newGame;
    private TextButton loadGame;
    private TextButton setting;
    private TextButton scoreboard;
    private TextButton profile;
    private TextButton hint;
    private TextButton logout;

    public MainMenu() {
        stage = new Stage(new FitViewport(1920, 1080));

        Skin skin = GameAssetManager.getSkin();
        this.backgroundLeft = new Image(new Texture(Gdx.files.internal("Texture2D/T_TitleLeaves.png")));
        backgroundLeft.setSize(Gdx.graphics.getWidth() / 3 - 30, Gdx.graphics.getHeight());

        this.backgroundRight = new Image(new Texture(Gdx.files.internal("Texture2D/T_TitleLeaves.png")));
        backgroundRight.setSize(Gdx.graphics.getWidth() / 3 - 30, Gdx.graphics.getHeight());
        backgroundRight.setScaleX(-1);
        backgroundRight.setPosition(Gdx.graphics.getWidth(), 0);

        this.backgroundTop = new Image(new Texture(Gdx.files.internal("Texture2D/T_20Logo.png")));
        backgroundTop.setSize((float) (Gdx.graphics.getWidth() / 2) - 180f, (float) (Gdx.graphics.getHeight() / 2) - 40f);
        backgroundTop.setPosition(Gdx.graphics.getWidth() / 4 + 90, 520);

        openEyes = new Texture("Sprite/T_EyeBlink_0.png");
        semiCloseEyes = new Texture("Sprite/T_EyeBlink_1.png");
        closeEyes = new Texture("Sprite/T_EyeBlink_2.png");

        newGame = new TextButton("New Game", skin);
        loadGame = new TextButton("Load Game", skin);
        setting = new TextButton("Settings", skin);
        scoreboard = new TextButton("Scoreboard", skin);
        profile = new TextButton("Profile", skin);
        hint = new TextButton("Hint", skin);
        logout = new TextButton("Logout", skin);

        Image image = new Image();

        switch (App.getCurrentUser().getAvatarNumber()){
            case 0:
                image = new Image(new Texture(Gdx.files.internal("Texture2D/T_Spark_Portrait.png")));
                break;
            case 1:
                image = new Image(new Texture(Gdx.files.internal("Texture2D/T_Abby_Portrait.png")));
                break;
            case 2:
                image = new Image(new Texture(Gdx.files.internal("Texture2D/T_Raven_Portrait.png")));
                break;
            case 3:
                image = new Image(new Texture(Gdx.files.internal("Texture2D/T_Hastur_Portrait.png")));
                break;
            case 4:
                image = new Image(new Texture(Gdx.files.internal("Texture2D/T_Scarlett_Portrait.png")));
        }

        if(App.getCurrentUser().getCostumeAvatar() != null){
            this.avatar = App.getCurrentUser().getCostumeAvatar();
        } else {
            this.avatar = image;
        }
        this.backgroundAvatar = new Image(new Texture(Gdx.files.internal("Texture2D/T_UIPanel.png")));
        this.username = new Label(App.getCurrentUser().getName(), skin);
        this.score = new Label("score: " + App.getCurrentUser().getScore(), skin);

        setupStage();
    }

    private void setupStage() {
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
        table.setPosition(0, -180);

        table.add(newGame).height(95);
        table.row().pad(2);
        table.add(loadGame).height(95);
        table.row().pad(2);
        table.add(scoreboard).height(95);
        table.row().pad(2);
        table.add(setting).height(95);
        table.row().pad(2);
        table.add(profile).height(95);
        table.row().pad(2);
        stage.addActor(table);

        hint.setPosition(1000,2);
        stage.addActor(hint);
        logout.setPosition(770,2);
        stage.addActor(logout);

        backgroundAvatar.setSize(250,320);
        backgroundAvatar.setPosition(1600, 740);
        stage.addActor(backgroundAvatar);

        avatar.setSize(220, 220);
        avatar.setPosition(1605, 816);
        stage.addActor(avatar);

        Table table2 = new Table();
        table2.setFillParent(true);
        table2.center();
        table2.setPosition(760, 250);
        username.setColor(new Color(253/255f, 81/255f, 97/255f, 1f));
        table2.add(username);
        table2.row();

        score.setColor(new Color(253/255f, 81/255f, 97/255f, 1f));
        table2.add(score);
        stage.addActor(table2);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        startBlinkLoop();
    }

    private void startBlinkLoop() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                blinkAnimation();
            }
        }, 2, 3 + (float) Math.random() * 2);
    }

    private void blinkAnimation() {
        if (eyeImage != null) {
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
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glClearColor(39 / 255f, 32 / 255f, 48 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        MainMenuController.handleButton(this);
    }

    @Override
    public void resize(int i, int i1) {
        stage.getViewport().update(i, i1, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        if (stage != null) {
            stage.dispose();
        }
    }

    public TextButton getLogout() {
        return logout;
    }

    public TextButton getHint() {
        return hint;
    }

    public TextButton getProfile() {
        return profile;
    }

    public TextButton getScoreboard() {
        return scoreboard;
    }

    public TextButton getSetting() {
        return setting;
    }

    public TextButton getLoadGame() {
        return loadGame;
    }

    public TextButton getNewGame() {
        return newGame;
    }
}
