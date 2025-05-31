package io.github.some_example_name.Model;

import com.badlogic.gdx.graphics.Texture;
import io.github.some_example_name.View.GameScreen;

import java.util.ArrayList;

public class EnemySpawner {
    private float spawnTimer = 0f;
    private float spawnRate = 3f; // Start spawning every 2 seconds

    public void update(float delta, ArrayList<Enemy> enemies, float gameTime, GameScreen gameScreen) {
        spawnTimer -= delta;

        // Increase difficulty over time
        float difficulty = gameTime / 60f; // Difficulty increases each minute
        float currentSpawnRate = Math.max(0.3f, spawnRate - difficulty * 0.1f);

        if (spawnTimer <= 0) {
            spawnTimer = currentSpawnRate;
            spawnEnemy(enemies, gameTime, gameScreen);
        }
    }

    private void spawnEnemy(ArrayList<Enemy> enemies, float gameTime, GameScreen gameScreen) {
        // Spawn enemy at random position off screen
        float distance = 600f; // Spawn distance from center


        // Choose enemy type based on difficulty
        EnemyType type = EnemyType.tentacle;
        if (gameTime > GameScreen.getGameDuration() / 4 && Math.random() < 0.2f) {
            type = EnemyType.eyebat;
        }

        ArrayList<Texture> textures = new ArrayList<>();

        if(type.equals(EnemyType.tentacle)){
            textures.add(new Texture("Sprite/BrainMonster_0.png"));
            textures.add(new Texture("Sprite/BrainMonster_1.png"));
            textures.add(new Texture("Sprite/BrainMonster_2.png"));
            textures.add(new Texture("Sprite/BrainMonster_3.png"));
            for(int i = 0; i < gameTime/30; i++){
                float angle = (float) (Math.random() * Math.PI * 2);
                float x = gameScreen.getPlayer().getPosition().x + (float) (Math.cos(angle) * distance);
                float y = gameScreen.getPlayer().getPosition().y + (float) (Math.sin(angle) * distance);
                enemies.add(new Enemy(x, y, type,textures));
            }
        } else {
            textures.add(new Texture("Sprite/T_EyeBat_0.png"));
            textures.add(new Texture("Sprite/T_EyeBat_1.png"));
            textures.add(new Texture("Sprite/T_EyeBat_2.png"));
            textures.add(new Texture("Sprite/T_EyeBat_3.png"));
            for(int i = 0; i < (4 * gameTime - GameScreen.getGameDuration() + 30)/30; i++){
                float angle = (float) (Math.random() * Math.PI * 2);
                float x = gameScreen.getPlayer().getPosition().x + (float) (Math.cos(angle) * distance);
                float y = gameScreen.getPlayer().getPosition().y + (float) (Math.sin(angle) * distance);
                enemies.add(new Enemy(x, y, type,textures));
            }
        }

    }
}
