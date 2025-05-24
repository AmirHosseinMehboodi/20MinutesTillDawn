package io.github.some_example_name.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class EnemyBullet {
    private Vector2 position;
    private Vector2 velocity;
    private float damage;
    private boolean active;
    private float lifespan;
    private float maxLifespan = 5f; // Bullet disappears after 3 seconds

    private Texture texture;
    private float size = 25f; // Bullet size

    public EnemyBullet(Vector2 startPos, Vector2 direction, float speed, float damage) {
        this.position = new Vector2(startPos);
        this.velocity = new Vector2(direction).nor().scl(speed);
        this.damage = damage;
        this.active = true;
        this.lifespan = 0f;

        // You can set a texture for the enemy bullet here
         this.texture = new Texture("Sprite/EyeMonsterProjecitle.png");
    }

    public void update(float delta) {
        if (!active) return;

        // Update position
        position.add(velocity.cpy().scl(delta));

        // Update lifespan
        lifespan += delta;
        if (lifespan >= maxLifespan) {
            active = false;
        }
    }

    public void draw(SpriteBatch batch) {
        if (!active) return;

        if (texture != null) {
            batch.draw(texture, position.x - size/2, position.y - size/2, size, size);
        } else {
            // If no texture, you could draw a simple colored rectangle or circle
            // For now, we'll skip drawing if no texture is provided
        }
    }

    // Method to set texture after creation
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    // Check collision with a circular area (like player)
    public boolean checkCollision(Vector2 targetPos, float targetRadius) {
        if (!active) return false;

        float distance = position.dst(targetPos);
        return distance <= (size/2 + targetRadius);
    }

    public void deactivate() {
        this.active = false;
    }

    // Getters
    public Vector2 getPosition() { return position; }
    public boolean isActive() { return active; }
    public float getDamage() { return damage; }
}

