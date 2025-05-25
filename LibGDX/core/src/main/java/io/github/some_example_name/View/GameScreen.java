package io.github.some_example_name.View;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.Model.*;

import java.awt.*;
import java.util.ArrayList;

public class GameScreen implements Screen {
    private final Game game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Map tileMap;

    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;
    private ArrayList<Pickup> pickups;
    private ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();
    private Gun playerGun;
    private Vector2 currentShootDirection;
    private Vector2 mouseWorldPos;

    private float gameTimer;
    private static final float GAME_DURATION = 20 * 60f; // 20 minutes in seconds

    private EnemySpawner enemySpawner;
    private CollisionManger collisionManager;

    public GameScreen(Game game) {
        this.game = game;

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
            case "SMGs DUAL":
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


        gameTimer = 0f;
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float delta) {
        gameTimer += delta;

        // Check win condition
        if (gameTimer >= GAME_DURATION) {
            // Player survived! Show victory screen
//            game.setScreen(new VictoryScreen(game)); TODO
            return;
        }

        // Update player
        player.update(delta);

        // Handle input
        handleInput();

        // Spawn enemies
        enemySpawner.update(delta, enemies, gameTimer);

        // Update enemies
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            EnemyBullet newBullet = enemy.update(delta, player.getPosition());

            if (newBullet != null) {
                enemyBullets.add(newBullet); // Add bullet to your bullet list
            }

            if (enemy.isDead()) {
                // Drop pickup chance
                pickups.add(new Pickup(enemy.getPosition()));
                enemies.remove(i);
            }
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

        playerGun.update(delta,player.getPosition(), currentShootDirection);

        // Update bullets
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

            // Check if bullet hit player
            if (bullet.checkCollision(player.getPosition(), 16f)) {
                player.takeDamage((int)bullet.getDamage());
                bullet.deactivate();
            }

            // Remove inactive bullets
            if (!bullet.isActive()) {
                enemyBullets.remove(i);
            }
        }

        // Update pickups
        for (int i = pickups.size() - 1; i >= 0; i--) {
            Pickup pickup = pickups.get(i);
            pickup.update(delta);

            if (pickup.shouldRemove()) {
                pickups.remove(i);
            }
        }

        // Handle collisions
        collisionManager.checkCollisions(player, enemies, bullets, pickups);

        // Check game over
        if (player.isDead()) {
//            game.setScreen(new GameOverScreen(game, gameTimer)); TODO
        }

        // Update camera to follow player
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

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (!enemies.isEmpty()) {
                Enemy nearest = findNearestEnemy();
                if (nearest != null) {
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

        player.setMovement(movement);
    }

    private Enemy findNearestEnemy() {
        Enemy nearest = null;
        float nearestDistance = Float.MAX_VALUE;

        for (Enemy enemy : enemies) {
            float distance = player.getPosition().dst(enemy.getPosition());
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = enemy;
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

        // Draw game objects
        player.draw(batch,40,65);

        for (Enemy enemy : enemies) {
            enemy.draw(batch);
        }


        playerGun.draw(batch);

        for (EnemyBullet bullet : enemyBullets) {
            bullet.draw(batch);
        }

        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }

        for (Pickup pickup : pickups) {
            pickup.draw(batch);
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
    }


    public void draw(SpriteBatch batch, Player player, float gameTime, float maxTime) {
        batch.begin();
        BitmapFont font = GameAssetManager.getSkin().getFont("font");

        // Draw health
        font.draw(batch, "Health: " + player.getHealth() + "/" + player.getMaxHealth(), 10, 50);

        // Draw timer
        float timeLeft = maxTime - gameTime;
        int minutes = (int)(timeLeft / 60);
        int seconds = (int)(timeLeft % 60);
        font.draw(batch, String.format("Time: %02d:%02d\nammo: %d", minutes, seconds, playerGun.getAmmo()), 10, 100);

        batch.end();
    }
}
