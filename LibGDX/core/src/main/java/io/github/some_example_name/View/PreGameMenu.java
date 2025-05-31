package io.github.some_example_name.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.some_example_name.Controller.PreGameMenuController;
import io.github.some_example_name.Main;
import io.github.some_example_name.Model.GameAssetManager;

public class PreGameMenu implements Screen {
    private Stage stage;
    private Image background;


    private Image avatar0;
    private Image avatar1;
    private Image avatar2;
    private Image avatar3;
    private Image avatar4;
    private Image heroShow = new Image();


    private SelectBox<String> hero;
    private SelectBox<String> gun;
    private SelectBox<String> time;

    private String selectedHero;
    private String selectedGun;

    private Image backGun;
    private Image gun0;
    private Image gun1;
    private Image gun2;
    private Image gunShow = new Image();


    private TextButton startGame;

    private TextButton back;



    public PreGameMenu() {
        Skin skin = GameAssetManager.getSkin();
        this.background = new Image(new Texture(Gdx.files.internal("Sprite/Background.png")));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        avatar0 = new Image(new Texture(Gdx.files.internal("Texture2D/T_Shana_Portrait.png")));
        avatar1 = new Image(new Texture(Gdx.files.internal("Texture2D/T_Diamond_Portrait.png")));
        avatar2 = new Image(new Texture(Gdx.files.internal("Texture2D/T_Dasher_Portrait.png")));
        avatar3 = new Image(new Texture(Gdx.files.internal("Texture2D/T_Lilith_Portrait.png")));
        avatar4 = new Image(new Texture(Gdx.files.internal("Texture2D/T_Scarlett_Portrait.png")));

        gun0 = new Image(new Texture(Gdx.files.internal("Sprite/RevolverStill.png")));
        gun1 = new Image(new Texture(Gdx.files.internal("Sprite/T_Shotgun_SS_0.png")));
        gun2 = new Image(new Texture(Gdx.files.internal("Sprite/SMGStill.png")));

        backGun = new Image(new Texture(Gdx.files.internal("Sprite/T_SelectorBubble_1.png")));


        hero = new SelectBox<>(skin);
        hero.setItems("SHANA", "DIAMOND", "DASHER", "LILITH", "SCARLET");

        gun = new SelectBox<>(skin);
        gun.setItems("REVOLVER", "SHOTGUN", "SMG DUAL");

        time = new SelectBox<>(skin);
        time.setItems("2 minutes", "5 minutes", "10 minutes", "20 minutes");

        startGame = new TextButton("Start Game", skin);
        back = new TextButton("Back to Main Menu", skin);

    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1920,1080));
        Gdx.input.setInputProcessor(stage);

        stage.addActor(background);

        heroShow = avatar0;
        selectedHero = "SHANA";
        heroShow.setSize(820, 900);
        heroShow.setPosition(1090, 100);
        stage.addActor(heroShow);


        backGun.setSize(180, 180);
        backGun.setPosition(150,650);
        stage.addActor(backGun);


//        if(gun.getSelected().equals("REVOLVER")) {
//            gunShow = gun0;
//        } else if(gun.getSelected().equals("SHOTGUN")) {
//            gunShow = gun1;
//        } else if(gun.getSelected().equals("SMG DUAL")) {
//            gunShow = gun2;
//        }
        gunShow = gun0;
        selectedGun = "REVOLVER";
        gunShow.setSize(150, 150);
        gunShow.setPosition(165, 665);
        stage.addActor(gunShow);




        Table table = new Table();
        table.setFillParent(true);
        table.setPosition(-100, 100);
        table.add(hero);
        table.row().pad(30,0,30,0);
        table.add(gun);
        table.row().pad(30,0,30,0);
        table.add(time);
        table.row().pad(30,0,30,0);
        table.add(startGame);
        stage.addActor(table);


        stage.addActor(back);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 0);

        if(!selectedHero.equals(hero.getSelected())){
            heroShow.remove();

            if(hero.getSelected().equals("SHANA")) {
                heroShow = avatar0;
            } else if(hero.getSelected().equals("DIAMOND")) {
                heroShow = avatar1;
            } else if(hero.getSelected().equals("DASHER")) {
                heroShow = avatar2;
            } else if(hero.getSelected().equals("LILITH")) {
                heroShow = avatar3;
            } else if(hero.getSelected().equals("SCARLET")) {
                heroShow = avatar4;
            }
            selectedHero = hero.getSelected();
            heroShow.setSize(820, 900);
            heroShow.setPosition(1090, 100);
            stage.addActor(heroShow);
        }

        if(!selectedGun.equals(gun.getSelected())){
            gunShow.remove();
            if(gun.getSelected().equals("REVOLVER")) {
                gunShow = gun0;
            } else if(gun.getSelected().equals("SHOTGUN")) {
                gunShow = gun1;
            } else if(gun.getSelected().equals("SMG DUAL")) {
                gunShow = gun2;
            }
            selectedGun = gun.getSelected();
            gunShow.setSize(150, 150);
            gunShow.setPosition(165, 665);
            stage.addActor(gunShow);
        }


        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        PreGameMenuController.handleButton(this);
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

    public SelectBox<String> getHero() {
        return hero;
    }

    public SelectBox<String> getGun() {
        return gun;
    }

    public SelectBox<String> getTime() {
        return time;
    }

    public TextButton getStartGame() {
        return startGame;
    }

    public TextButton getBack() {
        return back;
    }

    public void setGunShow(Image gunShow) {
        this.gunShow = gunShow;
    }

    public void setHeroShow(Image heroShow) {
        this.heroShow = heroShow;
    }

    public Image getAvatar0() {
        return avatar0;
    }

    public Image getAvatar1() {
        return avatar1;
    }

    public Image getAvatar2() {
        return avatar2;
    }

    public Image getAvatar3() {
        return avatar3;
    }

    public Image getAvatar4() {
        return avatar4;
    }

    public Image getGun0() {
        return gun0;
    }

    public Image getGun1() {
        return gun1;
    }

    public Image getGun2() {
        return gun2;
    }
}
