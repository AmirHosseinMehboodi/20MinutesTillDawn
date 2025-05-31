package io.github.some_example_name.Model;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Objects;

public class CollisionManger {
    private static final float COLLISION_DISTANCE = 32f;
    private static final float PICKUP_DISTANCE = 40f;

    public void checkCollisions(Player player, ArrayList<Enemy> enemies, ArrayList<Bullet> bullets, ArrayList<Pickup> pickups) {
        // Bullet vs Enemy collisions
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);

            for (int j = enemies.size() - 1; j >= 0; j--) {
                Enemy enemy = enemies.get(j);

                if (bullet.getPosition().dst(enemy.getPosition()) < COLLISION_DISTANCE) {
                    enemy.takeDamage(bullet.getDamage());
                    bullet.markForRemoval();
                    break;
                }
            }
        }

        // Player vs Enemy collisions
        for (Enemy enemy : enemies) {
            if (player.getPosition().dst(enemy.getPosition()) < COLLISION_DISTANCE) {
                player.takeDamage((int)enemy.getDamage());
                // Optionally push player away or add invincibility frames
            }
        }

        // Player vs Pickup collisions
        for (int i = pickups.size() - 1; i >= 0; i--) {
            Pickup pickup = pickups.get(i);

            if (player.getPosition().dst(pickup.getPosition()) < PICKUP_DISTANCE) {
                pickup.applyEffect(player);
                pickups.remove(i);
            }
        }
    }
}


