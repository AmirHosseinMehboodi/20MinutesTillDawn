package io.github.some_example_name.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.some_example_name.Controller.LoginMenuController;
import io.github.some_example_name.Controller.ProfileMenuController;
import io.github.some_example_name.Main;
import io.github.some_example_name.Model.App;
import io.github.some_example_name.Model.GameAssetManager;

import javax.swing.*;
import javax.swing.text.View;

public class ProfileMenu implements Screen {
    private Stage stage;
    private Image background;


    private Image avatar0;
    private Image avatar1;
    private Image avatar2;
    private Image avatar3;
    private Image avatar4;
    private ImageButton backgroundAvatar;

    com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup<ImageButton> buttonGroup = new com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup<>();


    ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
    Texture upTexture = new Texture(Gdx.files.internal("Texture2D/T_UIPanel.png"));
    Texture downTexture = new Texture(Gdx.files.internal("Sprite/T_UIPanelSelected.png"));

    private TextButton changeAvatar;
    private TextButton chooseLocal;

    private TextButton back;

    private TextButton changeUser;
    private TextField username;
    private TextButton changePassword;
    private TextField password;

    private TextButton deleteAccount;

    private Label error;
    private boolean errorShown = false;




    public ProfileMenu() {
        Skin skin = GameAssetManager.getSkin();
        this.background = new Image(new Texture(Gdx.files.internal("Sprite/Background.png")));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        avatar0 = new Image(new Texture(Gdx.files.internal("Texture2D/T_Spark_Portrait.png")));
        avatar1 = new Image(new Texture(Gdx.files.internal("Texture2D/T_Abby_Portrait.png")));
        avatar2 = new Image(new Texture(Gdx.files.internal("Texture2D/T_Raven_Portrait.png")));
        avatar3 = new Image(new Texture(Gdx.files.internal("Texture2D/T_Hastur_Portrait.png")));
        avatar4 = new Image(new Texture(Gdx.files.internal("Texture2D/T_Scarlett_Portrait.png")));


        style.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        style.down = new TextureRegionDrawable(new TextureRegion(downTexture));
        style.checked = new TextureRegionDrawable(new TextureRegion(downTexture));
        this.backgroundAvatar = new ImageButton(style);


        changeAvatar = new TextButton("Apply", skin);
        chooseLocal = new TextButton("Local", skin);

        back = new TextButton("Back to Main Menu", skin);

        username = new TextField("", skin);
        changeUser = new TextButton("Change Username", skin);

        password = new TextField("", skin);
        changePassword = new TextButton("Change Password", skin);

        deleteAccount = new TextButton("Delete Account", skin);

        this.error = new Label("", skin);
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1920,1080));
        Gdx.input.setInputProcessor(stage);

        stage.addActor(background);

        style.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        style.down = new TextureRegionDrawable(new TextureRegion(downTexture));


        for(int i = 0; i < 5;i++){
            backgroundAvatar = new ImageButton(style);
            backgroundAvatar.setTouchable(Touchable.enabled);
            buttonGroup.add(backgroundAvatar);
            backgroundAvatar.setSize(150,180);
            backgroundAvatar.setPosition(1750 - 160 * i, 800);
            stage.addActor(backgroundAvatar);
        }


        buttonGroup.setMaxCheckCount(1);
        buttonGroup.setMinCheckCount(1);

        avatar0.setSize(130, 130);
        avatar0.setPosition(1750, 820);
        stage.addActor(avatar0);

        avatar1.setSize(130, 130);
        avatar1.setPosition(1590, 820);
        stage.addActor(avatar1);

        avatar2.setSize(130, 130);
        avatar2.setPosition(1435, 820);
        stage.addActor(avatar2);

        avatar3.setSize(130, 130);
        avatar3.setPosition(1280, 820);
        stage.addActor(avatar3);

        avatar4.setSize(130, 130);
        avatar4.setPosition(1120, 820);
        stage.addActor(avatar4);

        changeAvatar.setSize(180, 100);
        changeAvatar.setPosition(1320, 680);
        stage.addActor(changeAvatar);

        chooseLocal.setSize(180, 100);
        chooseLocal.setPosition(1520, 680);
        stage.addActor(chooseLocal);

        back.setSize(500,100);
        back.setPosition(20,20);
        stage.addActor(back);


        Table table = new Table();
        table.setFillParent(true);
        table.add(username).width(400);
        table.row().pad(20, 0, 20, 0);
        table.add(changeUser);
        table.row().pad(20, 0, 20, 0);
        table.add(password).width(400);
        table.row().pad(20, 0, 20, 0);
        table.add(changePassword);
        table.setPosition(-400,100);
        stage.addActor(table);


        deleteAccount.setPosition(1480,20);
        stage.addActor(deleteAccount);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 0);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        String message = ProfileMenuController.handleButton(this);
        if(message != null && !errorShown) {
            showMessage(message);
            errorShown = true;
        }
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


    private void showMessage(String message) {
        error = new Label(message, GameAssetManager.getSkin());
        error.setSize(200, 80);
        error.setColor(Color.RED);
        error.setVisible(true);
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.setPosition(0,-100);
        table.add(error);
        stage.addActor(table);



        com.badlogic.gdx.utils.Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                error.setVisible(false);
                errorShown = false;
            }
        }, 3);
    }


    public TextButton getChooseLocal() {
        return chooseLocal;
    }

    public TextButton getBack() {
        return back;
    }

    public TextButton getChangeUser() {
        return changeUser;
    }

    public TextField getUsername() {
        return username;
    }

    public TextButton getChangePassword() {
        return changePassword;
    }

    public TextField getPassword() {
        return password;
    }

    public TextButton getDeleteAccount() {
        return deleteAccount;
    }

    public TextButton getChangeAvatar() {
        return changeAvatar;
    }

    public ButtonGroup<ImageButton> getButtonGroup() {
        return buttonGroup;
    }
}
