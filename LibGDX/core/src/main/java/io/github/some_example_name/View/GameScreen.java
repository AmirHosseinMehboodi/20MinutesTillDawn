package io.github.some_example_name.View;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.Main;
import io.github.some_example_name.Model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GameScreen implements Screen {
    private static GameScreen gameScreen;
    private final Game game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Map tileMap;
    private boolean paused = false;
    private boolean ability = false;
    private boolean lost = false;
    private boolean win = false;

    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;
    private ArrayList<Pickup> pickups;
    private ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();
    private Gun playerGun;
    private Vector2 currentShootDirection;
    private Vector2 mouseWorldPos;
    private Boolean autoAim = false;
    private Texture cursor;
    Texture heartLost = new Texture(Gdx.files.internal("Sprite/HeartAnimation_3.png"));
    Texture heart = new Texture(Gdx.files.internal("Sprite/HeartAnimation_0.png"));
    private Elder elder;
    private Barrier barrier;
    private boolean elderSpawned = false;
    private float barrierDamageTimer = 0f;

    private TextButton resume = new TextButton("Resume", GameAssetManager.getSkin());
    private TextButton giveUp = new TextButton("Give Up", GameAssetManager.getSkin());

    private TextButton tryAgain = new TextButton("Try Again", GameAssetManager.getSkin());
    private TextButton backToMenu = new TextButton("Main Menu", GameAssetManager.getSkin());

    private TextButton ability1Button = new TextButton("Ability 1", GameAssetManager.getSkin());
    private TextButton ability2Button = new TextButton("Ability 2", GameAssetManager.getSkin());
    private TextButton ability3Button = new TextButton("Ability 3", GameAssetManager.getSkin());

    int ability1 = -1;

    Stage stage = new Stage(new ScreenViewport());
    Stage stageAbility = new Stage(new ScreenViewport());
    Stage stageEndGame = new Stage(new ScreenViewport());

    private float gameTimer;
    private static float GAME_DURATION; // 20 minutes in seconds

    private EnemySpawner enemySpawner;
    private CollisionManger collisionManager;

    public GameScreen(Game game) {
        gameScreen = this;
        this.game = game;
        cursor  = new Texture(Gdx.files.internal("Sprite/T_CursorSprite.png"));
        GAME_DURATION = game.getTime() * 60;
        elder = null;
        barrier = null;

        // Setup camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        batch = new SpriteBatch();

        // Initialize game objects
        ArrayList<Texture> temp = new ArrayList<>();
        int health = 0;
        int speed = 0;
        switch (game.getHero()){
            case "SHANA":
                health = 4;
                speed = 4;
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_0 #8330.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_1 #8360.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_2 #8819.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_3 #8457.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_4 #8318.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_5 #8307.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_0 #8762.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_1 #8778.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_2 #8286.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_3 #8349.png")));
                break;
            case "DIAMOND":
                health = 7;
                speed = 1;
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_0 #8328.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_1 #8358.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_2 #8817.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_3 #8455.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_4 #8316.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_5 #8305.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_0 #8760.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_1 #8776.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_2 #8284.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_3 #8347.png")));
                break;
            case "DASHER":
                health = 2;
                speed = 10;
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_0 #8325.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_1 #8355.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_2 #8814.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_3 #8452.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_4 #8313.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_5 #8302.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_0 #8757.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_1 #8773.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_2 #8281.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_3 #8344.png")));
                break;
            case "LILITH":
                health = 5;
                speed = 3;
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_0 #8333.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_1 #8363.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_2 #8822.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_3 #8460.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_4 #8321.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_5 #8310.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_0 #8765.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_1 #8781.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_2 #8289.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_3 #8352.png")));
                break;
            case "SCARLET":
                health = 3;
                speed = 5;
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_0 #8327.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_1 #8357.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_2 #8816.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_3 #8454.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_4 #8315.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/Idle_5 #8304.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_0 #8759.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_1 #8775.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_2 #8283.png")));
                temp.add(new Texture(Gdx.files.internal("Sprite/run_3 #8346.png")));
                break;
        }


        player = new Player(960, 540 , temp, health,speed); // Center of screen
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        pickups = new ArrayList<>();

        Texture gunTexture = null;

        int damage = 0;
        int projectile = 0;
        int timeReload = 0;
        int ammoMax = 0;
        switch (game.getGun()){
            case "REVOLVER":
                damage = 20;
                projectile = 1;
                timeReload = 1;
                ammoMax = 6;
                gunTexture = new Texture(Gdx.files.internal("Sprite/RevolverStill.png"));
                break;
            case "SHOTGUN":
                damage = 10;
                projectile = 4;
                timeReload = 1;
                ammoMax = 2;
                gunTexture = new Texture(Gdx.files.internal("Sprite/T_Shotgun_SS_0.png"));
                break;
            case "SMG DUAL":
                damage = 8;
                projectile = 1;
                timeReload = 2;
                ammoMax = 24;
                gunTexture = new Texture(Gdx.files.internal("Sprite/SMGStill.png"));
                break;
        }

        playerGun = new Gun(gunTexture, 20f, damage, projectile, timeReload, ammoMax);
        currentShootDirection = new Vector2(1, 0);
        mouseWorldPos = new Vector2();


        player.setGun(playerGun);


        enemySpawner = new EnemySpawner();
        collisionManager = new CollisionManger();

        tileMap = new Map();


        ability1Button.setPosition(camera.position.x + 840, camera.position.y + 750);
        ability2Button.setPosition(camera.position.x + 840, camera.position.y + 580);
        ability3Button.setPosition(camera.position.x + 840 , camera.position.y + 410);
        stageAbility.addActor(ability1Button);
        stageAbility.addActor(ability2Button);
        stageAbility.addActor(ability3Button);

        resume.setPosition(camera.position.x + 840, camera.position.y + 520);
        giveUp.setPosition(camera.position.x + 840 , camera.position.y + 400);
        stage.addActor(resume);
        stage.addActor(giveUp);

        tryAgain.setPosition(camera.position.x + 820, camera.position.y + 350);
        backToMenu.setPosition(camera.position.x + 810, camera.position.y + 210);
        stageEndGame.addActor(tryAgain);
        stageEndGame.addActor(backToMenu);


        gameTimer = 0f;
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
        this.draw(batch, player, gameTimer, GAME_DURATION);

        if(lost || win){
            stageEndGame.act(Gdx.graphics.getDeltaTime());
            stageEndGame.draw();
            return;
        }

        if(paused){
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }

        if(ability) {
            stageAbility.act(Gdx.graphics.getDeltaTime());
            stageAbility.draw();
        }
    }

    private void update(float delta) {
        if(lost || win){
            Gdx.input.setCursorCatched(false);
            if(backToMenu.isPressed()) {
                player.getUser().setKills(player.getUser().getKills() + player.getKills());
                player.getUser().setScore(player.getUser().getScore() + ((int)gameTimer * player.getKills()));
                player.getUser().setSurvive(player.getUser().getSurvive() + (int)gameTimer);
                DatabaseManager.getInstance().updateUser(player.getUser());
                Gdx.input.setInputProcessor(null);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        Main.getMain().setScreen(new MainMenu());
                    }
                }, 0.5f);
                Main.getMain().setScreen(new MainMenu());
            }

            if(tryAgain.isPressed()) {
                player.getUser().setKills(player.getUser().getKills() + player.getKills());
                player.getUser().setScore(player.getUser().getScore() + ((int)gameTimer * player.getKills()));
                player.getUser().setSurvive(player.getUser().getSurvive() + (int)gameTimer);
                DatabaseManager.getInstance().updateUser(player.getUser());
                Main.getMain().setScreen(new GameScreen(game));
            }
            return;
        }

        if (paused) {
            Gdx.input.setCursorCatched(false);
            if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) || resume.isPressed()) {
                paused = false;
            }

            if(giveUp.isPressed()) {
                lost = true;
            }
            return;
        }

        if(ability){
            Gdx.input.setCursorCatched(false);
            String abilityName = "";


            if(ability1Button.isPressed()){
                ability1= -1;
                abilityName = ability1Button.getText().toString();
                ability = false;
            }

            if(ability2Button.isPressed()){
                ability1 = -1;
                abilityName = ability2Button.getText().toString();
                ability = false;
            }

            if(ability3Button.isPressed()){
                ability1 = -1;
                abilityName = ability3Button.getText().toString();
                ability = false;
            }

            switch (abilityName){
                case "VITALITY":
                    player.heal(1);
                    break;
                case "DAMAGER":
                    playerGun.setIncreaseDamageTime(10f);
                    break;
                case "PROCREASE":
                    playerGun.setProjectile(1);
                    break;
                case "AMOCREASE":
                    playerGun.setAmmoMax(5);
                    break;
                case "SPEEDY":
                    player.setIncreaseSpeed(10f);
                    break;
                default:
                    break;
            }


            return;
        }

        if (gameTimer >= GAME_DURATION) {
            win = true;
            return;
        }

        Gdx.input.setCursorCatched(autoAim);


        gameTimer += delta;


        player.update(delta);

        handleInput();

        enemySpawner.update(delta, enemies, gameTimer, this);

        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            EnemyBullet newBullet = enemy.update(delta, player.getPosition());

            if (newBullet != null) {
                enemyBullets.add(newBullet);
            }

            if (enemy.isDead()) {
                pickups.add(new Pickup(enemy.getPosition()));
                player.setKills(player.getKills() + 1);
                enemies.remove(i);
            }
        }

        if (!elderSpawned && gameTimer >= GAME_DURATION / 2) {
            spawnElder();
            elderSpawned = true;
        }

        if (!enemies.isEmpty()) {
            Enemy nearest = findNearestEnemy();
            if (nearest != null) {
                Vector2 shootDirection = new Vector2(nearest.getPosition()).sub(player.getPosition()).nor();
                currentShootDirection = shootDirection;
            } else {
                Vector3 mouseScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(mouseScreen);
                Vector2 shootDirection = new Vector2(mouseScreen.x, mouseScreen.y).sub(player.getPosition()).nor();
                if(player.canShoot()){
                    currentShootDirection = shootDirection;
                }
            }
        }

        playerGun.update(delta,player.getPosition(), currentShootDirection, autoAim, camera);

        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            if(bullets.get(i) == null){
                System.out.println(i);
            }
            bullet.update(delta);

            if (bullet.isOffScreen() || bullet.shouldRemove()) {
                bullets.remove(i);
            }
        }


        for (int i = enemyBullets.size() - 1; i >= 0; i--) {
            EnemyBullet bullet = enemyBullets.get(i);
            bullet.update(delta);

            if (bullet.checkCollision(player.getPosition(), 16f)) {
                player.takeDamage((int)bullet.getDamage());
                bullet.deactivate();
            }

            if (!bullet.isActive()) {
                enemyBullets.remove(i);
            }
        }

        for (int i = pickups.size() - 1; i >= 0; i--) {
            Pickup pickup = pickups.get(i);
        }

        collisionManager.checkCollisions(player , enemies, bullets, pickups);
        if (barrier != null && barrier.isActive()) {
            barrier.update(delta);

            if (barrier.checkCollision(player.getPosition())) {
                barrierDamageTimer += delta;
                if (barrierDamageTimer >= 1f) {
                    player.takeDamage((int)barrier.getDamage());
                    barrierDamageTimer = 0f;
                }
            } else {
                barrierDamageTimer = 0f;
            }
        }
        if (elder != null && !elder.isDead()) {
            elder.update(delta, player.getPosition());

            // Check collision between player and elder
            if (player.getPosition().dst(elder.getPosition()) < 50f) {
                player.takeDamage(1);
            }

            // Check collision between bullets and elder
            for (int i = bullets.size() - 1; i >= 0; i--) {
                Bullet bullet = bullets.get(i);
                if (elder.getPosition().dst(bullet.getPosition()) < 40f) {
                    elder.takeDamage(bullet.getDamage());
                    bullets.remove(i);

                    if (elder.isDead()) {
                        if (barrier != null) {
                            barrier.deactivate();
                        }
                        player.setKills(player.getKills() + 100); // Big bonus for boss
                        elder = null;
                        break;
                    }
                }
            }
        }

        if (player.isDead()) {
            lost = true;
            return;
        }

        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        camera.update();
    }

    private void handleInput() {
        Vector2 movement = new Vector2();

        // Movement input
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            movement.y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            movement.y -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movement.x -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movement.x += 1;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if (!enemies.isEmpty()) {
                Enemy nearest = findNearestEnemy();
                if (autoAim) {
                    Vector2 shootDirection = new Vector2(nearest.getPosition()).sub(player.getPosition()).nor();
                    if (player.canShoot() && playerGun.getAmmo() > 0 && !playerGun.isReloading()) {
                        bullets.addAll(player.shoot(shootDirection));

                        playerGun.shoots();
                        if(playerGun.getAmmo() == 0 && App.isAutoReload()){
                            playerGun.setReloading(true);
                            Timer.schedule(playerGun.getReloadTimeTask(),playerGun.getTimeReload());
                        }

                    }
                } else {
                    Vector3 mouseScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                    camera.unproject(mouseScreen);
                    Vector2 shootDirection = new Vector2(mouseScreen.x, mouseScreen.y).sub(player.getPosition()).nor();
                    if (player.canShoot() && playerGun.getAmmo() > 0 && !playerGun.isReloading()) {
                        bullets.addAll(player.shoot(shootDirection));


                        playerGun.shoots();
                        if(playerGun.getAmmo() == 0 && App.isAutoReload()){
                            playerGun.setReloading(true);
                            Timer.schedule(playerGun.getReloadTimeTask(),playerGun.getTimeReload());
                        }
                    }
                }
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
            autoAim = !autoAim;
            Gdx.input.setCursorCatched(autoAim);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            if(!playerGun.isReloading()){
                playerGun.setReloading(true);
                Timer.schedule(playerGun.getReloadTimeTask(),playerGun.getTimeReload());
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.T)){
            gameTimer += 60;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.L)){
            player.setXp(player.getXp() + player.getLevel() * 20);
            ability = true;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.H)){
            if(player.getHealth() < player.getMaxHealth()){
                player.setHealth(1);
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            playerGun.setProjectile(1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            paused = !paused;
        }

        player.setMovement(movement);
    }

    private Enemy findNearestEnemy() {
        Enemy nearest = null;
        float nearestDistance = Float.MAX_VALUE;

        ArrayList<Enemy> plus = new ArrayList<>(enemies);
        if(elder != null){
            plus.add(elder);
        }
        for (Enemy enemy : plus) {
            float distance = player.getPosition().dst(enemy.getPosition());
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = enemy;
                if(autoAim && !paused && !ability && !lost && !win){
                    Gdx.input.setCursorPosition((int)enemy.getPosition().x, (int)enemy.getPosition().y);
                }
            }
        }

        return nearest;
    }

    private void draw() {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        tileMap.render(batch,camera);

        player.draw(batch,40,65);

        for (Enemy enemy : enemies) {
            enemy.draw(batch);
        }


        playerGun.draw(batch);

        for (EnemyBullet bullet : enemyBullets) {
            bullet.draw(batch);
        }

        if (elder != null && !elder.isDead()) {
            elder.draw(batch);
        }

        if (barrier != null && barrier.isActive()) {
            barrier.draw(batch);
        }


        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }

        for (Pickup pickup : pickups) {
            pickup.draw(batch);
        }

        if(autoAim && findNearestEnemy() != null){
            batch.draw(cursor,findNearestEnemy().getPosition().x,findNearestEnemy().getPosition().y);
        }


        if(win || lost){
            drawEndGame(batch);
        } else if (paused) {
            drawPauseOverlay(batch);
        } else if(ability){
            drawAbility(batch);
        }

        batch.end();

        this.draw(batch, player, gameTimer, GAME_DURATION);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        tileMap.dispose();
        stageEndGame.dispose();
        stage.dispose();
        stageAbility.dispose();
//        if (Gdx.input.getInputProcessor() == stageEndGame ||
//            Gdx.input.getInputProcessor() == stage ||
//            Gdx.input.getInputProcessor() == stageAbility) {
//            Gdx.input.setInputProcessor(null);
//        }
    }


    public void draw(SpriteBatch batch, Player player, float gameTime, float maxTime) {
        batch.begin();
        BitmapFont font = GameAssetManager.getSkin().getFont("font");


        batch.setProjectionMatrix(camera.combined);

        for(int i = 0; i < player.getMaxHealth(); i++){
            if(i < player.getHealth()){
                batch.draw(heart, camera.position.x - 950 + 60 * i, camera.position.y + 450, 50, 60);
            } else{
                batch.draw(heartLost, camera.position.x - 950 + 60 * i, camera.position.y + 450, 50, 60);
            }
        }

        batch.draw(new Texture(Gdx.files.internal("Sprite/T_AmmoIcon.png")),camera.position.x - 950, camera.position.y + 380, 30, 60);
        font.setColor(Color.RED);
        font.getData().setScale(1.5f);
        font.draw(batch, String.format("%d / %d",playerGun.getAmmo(),playerGun.getAmmoMax()),camera.position.x - 890, camera.position.y + 420);
        // Draw timer
        float timeLeft = maxTime - gameTime;
        int minutes = (int)(timeLeft / 60);
        int seconds = (int)(timeLeft % 60);
        font.getData().setScale(3f);
        font.draw(batch, String.format("%02d:%02d", minutes, seconds, playerGun.getAmmo()),camera.position.x + 750, camera.position.y + 500);

        font.getData().setScale(1.5f);
        font.draw(batch, String.format("Level %d\nxp: %d / %d", player.getLevel(),player.getXp(),player.getMaxXpLevel()),camera.position.x + 750, camera.position.y + 400);


        batch.end();
    }

    public static float getGameDuration() {
        return GAME_DURATION;
    }

    public Player getPlayer() {
        return player;
    }

    private void drawPauseOverlay(SpriteBatch batch) {
        BitmapFont font = GameAssetManager.getSkin().getFont("font");

        batch.setColor(0, 0, 0, 0.7f);

        Texture whitePixel = new Texture(Gdx.files.internal("Sprite/T_UIPanel.png"));
        batch.draw(whitePixel, camera.position.x - 400, camera.position.y - 200, 800, 400);


        batch.setColor(1, 1, 1, 1);


        font.setColor(Color.WHITE);
        font.getData().setScale(2f);

        String pauseText = "GAME PAUSED";
        font.draw(batch, pauseText, camera.position.x - 200, camera.position.y + 150);

        Gdx.input.setInputProcessor(stage);
    }


    public void drawAbility(SpriteBatch batch) {
        batch.setColor(0, 0, 0, 0.7f);

        Texture whitePixel = new Texture(Gdx.files.internal("Sprite/T_UIPanel.png"));
        batch.draw(whitePixel, camera.position.x - 200, camera.position.y - 200, 400, 600);

        // Reset color
        batch.setColor(1, 1, 1, 1);

        if(ability1 == -1) {

            Gdx.input.setInputProcessor(stageAbility);
            // Draw pause text
            Random random = new Random();

            Set<Integer> abilitySet = new HashSet<>();
            while (abilitySet.size() < 3) {
                abilitySet.add(random.nextInt(5));
            }
            Integer[] abilityArray = abilitySet.toArray(new Integer[0]);
            ability1 = abilityArray[0];

            String[] abilityNames = {"VITALITY", "DAMAGER", "PROCREASE", "AMOCREASE", "SPEEDY"};

            ability1Button.setText(abilityNames[abilityArray[0]]);
            ability2Button.setText(abilityNames[abilityArray[1]]);
            ability3Button.setText(abilityNames[abilityArray[2]]);

        }

    }

    public void drawEndGame(SpriteBatch batch) {
        batch.setColor(0, 0, 0, 0.7f);

        Texture whitePixel = new Texture(Gdx.files.internal("Sprite/T_UIPanel.png"));
        batch.draw(whitePixel, camera.position.x - 300, camera.position.y - 400, 600, 800);

        batch.setColor(1, 1, 1, 1);

        BitmapFont font = GameAssetManager.getSkin().getFont("font");
        font.setColor(Color.WHITE);
        font.getData().setScale(4f);

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;

        String title;

        if(win){
            title = "You won!";
        } else {
            title = "You Died!";
        }
        font.draw(batch, title, camera.position.x - 240, camera.position.y + 300);
        font.getData().setScale(1.5f);

        int minutes2 = (int)(gameTimer / 60);
        int seconds2 = (int)(gameTimer % 60);

        font.draw(batch, String.format("%s",player.getUser().getName()),
            camera.position.x - 200, camera.position.y + 150);
        font.draw(batch, String.format("Time Survived (%02d:%02d)", minutes2, seconds2)
            ,camera.position.x - 200, camera.position.y + 100);
        font.draw(batch, String.format("Enemies Killed           %d",
            player.getKills()),camera.position.x - 200, camera.position.y + 50);
        font.draw(batch, String.format("Points Earned           %d",
            ((int)gameTimer * player.getKills())), camera.position.x - 200, camera.position.y);

        Gdx.input.setInputProcessor(stageEndGame);
    }


    private void spawnElder() {
        // Spawn elder at a random position around the player
        float spawnDistance = 400f;
        float angle = (float)(Math.random() * 360);
        float x = player.getPosition().x + (float)Math.cos(Math.toRadians(angle)) * spawnDistance;
        float y = player.getPosition().y + (float)Math.sin(Math.toRadians(angle)) * spawnDistance;

        elder = new Elder(x, y);

        Vector2 barrierCenter = new Vector2(camera.position.x, camera.position.y);
        float initialRadius = Math.max(viewport.getWorldWidth(), viewport.getWorldHeight()) / 2f;
        float shrinkRate = initialRadius / (GAME_DURATION / 2f);

        barrier = new Barrier(barrierCenter, initialRadius, shrinkRate);
    }

    public static GameScreen getGameScreen() {
        return gameScreen;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public boolean isAbility() {
        return ability;
    }

    public void setAbility(boolean ability) {
        this.ability = ability;
    }
}
