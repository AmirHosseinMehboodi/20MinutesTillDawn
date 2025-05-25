package io.github.some_example_name.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Vector2 position;
    private Vector2 velocity;
    private float damage;
    private float maxRange;
    private float distanceTraveled;
    private boolean shouldRemove = false;

    public Bullet(Vector2 startPos, Vector2 direction, float speed, float damage, float range) {
        position = new Vector2(startPos);
        velocity = new Vector2(direction).nor().scl(speed);
        this.damage = damage;
        this.maxRange = range;
        distanceTraveled = 0f;
    }

    public void update(float delta) {
        Vector2 movement = velocity.cpy().scl(delta);
        position.add(movement);
        distanceTraveled += movement.len();

        if (distanceTraveled >= maxRange) {
            shouldRemove = true;
        }
    }

    public void draw(SpriteBatch batch) {
        // Draw bullet
         batch.draw(new Texture("Sprite/T_ChargeUp_0.png"), position.x - 2, position.y - 2, 15, 15);
    }

    public Vector2 getPosition() { return position; }
    public float getDamage() { return damage; }
    public boolean shouldRemove() { return shouldRemove; }
    public boolean isOffScreen() {
        // Check if bullet is far off screen
        return Math.abs(position.x) > 2000 || Math.abs(position.y) > 2000;
    }

    public void markForRemoval() { shouldRemove = true; }
}
