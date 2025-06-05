package io.github.some_example_name.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.Model.App;
import io.github.some_example_name.Model.GameAssetManager;
import io.github.some_example_name.Model.User;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreBoardMenu implements Screen {
    private Stage stage;
    private Image background;
    private ArrayList<User> rankings;
    private ArrayList<Label> labels = new ArrayList<>();
    private SelectBox<String> rankBy;

    private TextButton back;



    public ScoreBoardMenu() {
        rankings = App.getUsers();

        Skin skin = GameAssetManager.getSkin();
        this.background = new Image(new Texture(Gdx.files.internal("Sprite/Background.png")));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        rankBy = new SelectBox<>(GameAssetManager.getSkin());
        rankBy.setItems("Rank By","Kills","Survival Time","Score","Username");

        back = new TextButton("Back to Main Menu", skin);
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1920,1080));
        Gdx.input.setInputProcessor(stage);
        stage.addActor(background);

        stage.addActor(rankBy);

        rankBy.setPosition(800,200);
        rankBy.setSize(500,100);

        if(rankings.contains(App.getUsers().get(0))) {
            rankings.remove(App.getUsers().get(0));
        }
        rankings.sort(Comparator.comparing(User::getKills).reversed());

        for (int i = 0; i < 10 && i < rankings.size(); i++) {
            Label text = new Label(String.format("%d- %s    %d    %d    %d", i + 1, rankings.get(i).getName(), rankings.get(i).getKills(), rankings.get(i).getSurvive(), rankings.get(i).getScore()), GameAssetManager.getSkin());
            text.setPosition(800,1000 - 80 * (i + 1));
            text.setScale(2f);
            labels.add(text);

            if(App.getCurrentUser().equals(rankings.get(i))){
                text.setColor(Color.RED);
            } else if(i < 3){
                text.setColor(Color.GOLD);
            } else {
                text.setColor(Color.WHITE);
            }
            stage.addActor(text);
        }


        back.setPosition(10,10);
        stage.addActor(back);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 0);

        if(rankBy.getSelected().equals("Kills")){
            rankings.sort(Comparator.comparing(User::getKills).reversed());
        } else if(rankBy.getSelected().equals("Survival Time")){
            rankings.sort(Comparator.comparing(User::getSurvive).reversed());
        } else if(rankBy.getSelected().equals("Score")){
            rankings.sort(Comparator.comparing(User::getScore).reversed());
        } else if(rankBy.getSelected().equals("Username")){
            rankings.sort(Comparator.comparing(User::getName));
        }
        for (int i = 0; i < 10 && i < rankings.size(); i++) {
            labels.get(i).remove();
            Label text = new Label(String.format("%d- %s    %d    %d    %d", i + 1, rankings.get(i).getName(), rankings.get(i).getKills(), rankings.get(i).getSurvive(), rankings.get(i).getScore()), GameAssetManager.getSkin());
            text.setPosition(800,1000 - 80 * (i + 1));
            text.setScale(2f);
            labels.add(text);

            if(App.getCurrentUser().equals(rankings.get(i))){
                text.setColor(Color.RED);
            } else if(i < 3){
                text.setColor(Color.GOLD);
            } else {
                text.setColor(Color.WHITE);
            }
            stage.addActor(text);
        }

        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        if(back.isPressed()){
            Main.getMain().setScreen(new MainMenu());
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
}
