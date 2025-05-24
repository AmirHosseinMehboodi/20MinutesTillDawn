package io.github.some_example_name.Model;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class EnemySpawner {
    private float spawnTimer = 0f;
    private float spawnRate = 2f; // Start spawning every 2 seconds

    public void update(float delta, ArrayList<Enemy> enemies, float gameTime) {
        spawnTimer -= delta;

        // Increase difficulty over time
        float difficulty = gameTime / 60f; // Difficulty increases each minute
        float currentSpawnRate = Math.max(0.3f, spawnRate - difficulty * 0.1f);

        if (spawnTimer <= 0) {
            spawnTimer = currentSpawnRate;
            spawnEnemy(enemies, difficulty);
        }
    }

    private void spawnEnemy(ArrayList<Enemy> enemies, float difficulty) {
        // Spawn enemy at random position off screen
        float angle = (float) (Math.random() * Math.PI * 2);
        float distance = 600f; // Spawn distance from center

        float x = (float) (Math.cos(angle) * distance);
        float y = (float) (Math.sin(angle) * distance);

        // Choose enemy type based on difficulty
        EnemyType type = EnemyType.tentacle;
        if (difficulty + 5 > 2f && Math.random() < 0.3f) {
            type = EnemyType.eyebat;
        }
//        if (difficulty > 4f && Math.random() < 0.2f) {
//            type = EnemyType.TANK;
//        }

        ArrayList<Texture> textures = new ArrayList<>();

        if(type.equals(EnemyType.tentacle)){
        textures.add(new Texture("Sprite/BrainMonster_0.png"));
        textures.add(new Texture("Sprite/BrainMonster_1.png"));
        textures.add(new Texture("Sprite/BrainMonster_2.png"));
        textures.add(new Texture("Sprite/BrainMonster_3.png"));

        } else {
            textures.add(new Texture("Sprite/T_EyeBat_0.png"));
            textures.add(new Texture("Sprite/T_EyeBat_1.png"));
            textures.add(new Texture("Sprite/T_EyeBat_2.png"));
            textures.add(new Texture("Sprite/T_EyeBat_3.png"));
        }



        enemies.add(new Enemy(x, y, type,textures));
    }
}
