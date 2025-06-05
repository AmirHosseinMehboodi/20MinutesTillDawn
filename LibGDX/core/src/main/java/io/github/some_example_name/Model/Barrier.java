package io.github.some_example_name.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Barrier {
    private Vector2 center;
    private float currentRadius;
    private float initialRadius;
    private float shrinkRate;
    private boolean active;
    private Texture barrierTexture;
    private float damage;

    public Barrier(Vector2 center, float initialRadius, float shrinkRate) {
        this.center = new Vector2(center);
        this.currentRadius = initialRadius;
        this.initialRadius = initialRadius;
        this.shrinkRate = shrinkRate;
        this.active = true;
        this.damage = 1f; // Damage per second when touching barrier

        // Create a simple barrier texture (white pixel that we'll tint)
        this.barrierTexture = new Texture(Gdx.files.internal("Sprite/border.jpg")); // Use existing white texture
    }

    public void update(float delta) {
        if (active && currentRadius > 50f) { // Minimum barrier size
            currentRadius -= shrinkRate * delta;
            if (currentRadius <= 50f) {
                currentRadius = 50f;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        if (!active) return;

        batch.setColor(1f, 0f, 0f, 0.3f); // Red, semi-transparent

        int thickness = 10;

        // Calculate rectangle bounds based on center and current radius
        float left = center.x - currentRadius;
        float right = center.x + currentRadius;
        float bottom = center.y - currentRadius;
        float top = center.y + currentRadius;

        float width = currentRadius * 2;
        float height = currentRadius * 2;

        // Draw rectangle outline that shrinks over time
        // Top edge
        batch.draw(barrierTexture, left, top - thickness, width, thickness);

        // Bottom edge
        batch.draw(barrierTexture, left, bottom, width, thickness);

        // Left edge
        batch.draw(barrierTexture, left, bottom, thickness, height);

        // Right edge
        batch.draw(barrierTexture, right - thickness, bottom, thickness, height);

        batch.setColor(1f, 1f, 1f, 1f); // Reset color
    }

    public boolean checkCollision(Vector2 position) {
        if (!active) return false;

        // Check if position is outside the rectangular barrier
        float left = center.x - currentRadius;
        float right = center.x + currentRadius;
        float bottom = center.y - currentRadius;
        float top = center.y + currentRadius;

        return position.x < left || position.x > right ||
            position.y < bottom || position.y > top;
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public float getDamage() {
        return damage;
    }

    public float getCurrentRadius() {
        return currentRadius;
    }

    public Vector2 getCenter() {
        return center;
    }

    public void dispose() {
        if (barrierTexture != null) {
            barrierTexture.dispose();
        }
    }
}
