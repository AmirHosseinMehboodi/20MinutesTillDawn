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
import io.github.some_example_name.Controller.ForgetPasswordController;
import io.github.some_example_name.Controller.LoginMenuController;
import io.github.some_example_name.Main;
import io.github.some_example_name.Model.GameAssetManager;

public class ForgetPasswordMenu implements Screen {
        private Stage stage;
        private Image backgroundLeft;
        private Image backgroundRight;
        private Image backgroundTop;

        private Texture openEyes;
        private Texture semiCloseEyes;
        private Texture closeEyes;
        private Image eyeImage;

        private TextButton checkUsernameButton;
        private TextButton setPasswordButton;

        private TextField username;
        private TextField answer;
        private TextField password;


        private Label error;
        private boolean errorShown = false;

        public ForgetPasswordMenu() {
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


            this.username = new TextField("username", skin);
            this.password = new TextField("new password", skin);

            this.answer = new TextField("answer", skin);

            this.checkUsernameButton = new TextButton("check", skin);
            this.setPasswordButton = new TextButton("set password", skin);


            this.error = new Label("", skin);
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
            table.setPosition(0,-200);


            table.add(username).width(300).height(80);
            table.row().pad(30,0,5,0);

            table.add(checkUsernameButton).height(100);
            table.row().pad(5,0,2,0);

            table.add(answer).width(300).height(80);
            table.row().pad(2,0,2,0);

            table.add(password).width(300).height(80);
            table.row().pad(30,0,2,0);


            table.add(setPasswordButton).height(100);
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

            String message = ForgetPasswordController.handleButton(this);
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
            table.setPosition(0,-35);
            table.add(error);
            stage.addActor(table);



            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    error.setVisible(false);
                    errorShown = false;
                }
            }, 3);
        }

        public TextButton getCheckUsernameButton() {
            return checkUsernameButton;
        }


        public TextField getUsername() {
            return username;
        }

        public TextField getPassword() {
            return password;
        }

    public TextButton getSetPasswordButton() {
        return setPasswordButton;
    }

    public TextField getAnswer() {
        return answer;
    }
}
