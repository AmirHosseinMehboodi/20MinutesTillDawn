package io.github.some_example_name.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Elder extends Enemy {
    private static final float DASH_COOLDOWN = 5.0f;
    private static final float DASH_SPEED = 800f;
    private static final float DASH_DURATION = 1.0f;

    private float dashTimer;
    private boolean isDashing;
    private Vector2 dashDirection;
    private float dashTimeRemaining;
    private Vector2 originalPosition;

    public Elder(float x, float y) {
        super(x, y, EnemyType.ELDER, loadElderTextures());
        this.dashTimer = 0f;
        this.isDashing = false;
        this.dashDirection = new Vector2();
        this.dashTimeRemaining = 0f;
        this.originalPosition = new Vector2();
    }

    private static ArrayList<Texture> loadElderTextures() {
        ArrayList<Texture> textures = new ArrayList<>();
        try {
            // Try to load actual elder textures first
            textures.add(new Texture(Gdx.files.internal("Sprite/T_ShubNiggurath_0.png")));
            textures.add(new Texture(Gdx.files.internal("Sprite/T_ShubNiggurath_1.png")));
            textures.add(new Texture(Gdx.files.internal("Sprite/T_ShubNiggurath_2.png")));
            textures.add(new Texture(Gdx.files.internal("Sprite/T_ShubNiggurath_3.png")));
        } catch (Exception e) {
            // Fallback to existing enemy textures as placeholder
            textures.add(new Texture(Gdx.files.internal("Sprite/Idle_0 #8330.png")));
            textures.add(new Texture(Gdx.files.internal("Sprite/Idle_1 #8360.png")));
        }
        return textures;
    }

    @Override
    public EnemyBullet update(float delta, Vector2 playerPosition) {
        if (isDead()) return null;

        dashTimer += delta;

        if (isDashing) {
            // Continue dash
            dashTimeRemaining -= delta;
            Vector2 dashMovement = new Vector2(dashDirection).scl(DASH_SPEED * delta);
            getPosition().add(dashMovement);

            if (dashTimeRemaining <= 0) {
                isDashing = false;
            }
        } else {
            // Call parent update for normal movement and animation
            super.update(delta, playerPosition);

            // Check if it's time to dash
            if (dashTimer >= DASH_COOLDOWN) {
                startDash(playerPosition);
                dashTimer = 0f;
            }
        }

        return null; // Elder doesn't shoot bullets, only dashes
    }

    private void startDash(Vector2 playerPosition) {
        isDashing = true;
        dashTimeRemaining = DASH_DURATION;

        // Calculate dash direction towards player
        dashDirection.set(playerPosition).sub(getPosition()).nor();
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isDead()) return;

        // Apply red tint when dashing
        if (isDashing) {
            batch.setColor(1f, 0.3f, 0.3f, 1f); // Red tint for dash
        }

        // Call parent draw method but with larger scale for boss
        super.draw(batch);

        if (isDashing) {
            batch.setColor(1f, 1f, 1f, 1f); // Reset color
        }
    }

    @Override
    public void takeDamage(float damage) {
        // Elder takes less damage to make it a proper boss fight
        super.takeDamage(damage * 0.5f);
    }

    public boolean isDashing() {
        return isDashing;
    }

    public float getDashSpeed() {
        return isDashing ? DASH_SPEED : 50f; // Return dash speed or normal speed
    }
}
