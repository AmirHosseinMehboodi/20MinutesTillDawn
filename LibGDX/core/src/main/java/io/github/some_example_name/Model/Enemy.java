package io.github.some_example_name.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class Enemy {
    private Vector2 position;
    private Vector2 velocity;
    private float speed;
    private int health;
    private int maxHealth;
    private float damage;
    private EnemyType type;

    private boolean dead = false;
    private boolean facingLeft = false;

    // Animation variables
    private Animation<TextureRegion> moveAnimation;
    private float stateTime = 0f;

    // Shooting variables (for eyebat)
    private float shootTimer = 0f;
    private float shootCooldown = 1.5f; // Shoot every 1.5 seconds
    private float shootRange = 300f;
    private float bulletSpeed = 400f;

    public Enemy(float x, float y, EnemyType type, ArrayList<Texture> textures) {
        position = new Vector2(x, y);
        velocity = new Vector2();
        this.type = type;

        // Set stats based on enemy type
        switch (type) {
            case tentacle:
                speed = 100f;
                health = maxHealth = 25;
                damage = 1f;
                shootCooldown = 0f;
                break;
            case eyebat:
                speed = 75f;
                health = maxHealth = 50;
                damage = 1f;
                shootCooldown = 2f;
                break;
        }

        // Initialize animation
        initializeAnimation(textures);
    }

    private void initializeAnimation(ArrayList<Texture> textures) {
        if (textures == null || textures.isEmpty()) {
            return;
        }

        // Create animation frames from textures
        TextureRegion[] frames = new TextureRegion[textures.size()];
        for (int i = 0; i < textures.size(); i++) {
            frames[i] = new TextureRegion(textures.get(i));
        }

        // Create animation - adjust speed based on enemy type
        float frameTime = (type == EnemyType.eyebat) ? 0.1f : 0.15f;
        moveAnimation = new Animation<TextureRegion>(frameTime, frames);
        moveAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public EnemyBullet update(float delta, Vector2 playerPos) {
        if (dead) return null;

        // Update animation time
        stateTime += delta;

        // Move towards player
        Vector2 direction = new Vector2(playerPos).sub(position).nor();
        velocity.set(direction).scl(speed);
        position.add(velocity.cpy().scl(delta));

        // Update facing direction
        if (direction.x < 0) {
            facingLeft = true;
        } else if (direction.x > 0) {
            facingLeft = false;
        }

        EnemyBullet newBullet = null;
        if (type == EnemyType.eyebat && canShoot(playerPos)) {
            newBullet = shoot(playerPos);
        }

        // Update shoot timer for eyebat
        if (type == EnemyType.eyebat) {
            shootTimer -= delta;
        }
        return newBullet;

    }

    public void takeDamage(float damage) {
        health -= damage;
        if (health <= 0) {
            dead = true;
        }
    }

    // Method to check if eyebat can shoot
    public boolean canShoot(Vector2 playerPos) {
        if (type != EnemyType.eyebat || dead) {
            return false;
        }

        float distanceToPlayer = position.dst(playerPos);
        return shootTimer <= 0 && distanceToPlayer <= shootRange;
    }

    // Method to shoot at player (for eyebat)
    public EnemyBullet shoot(Vector2 playerPos) {
        if (!canShoot(playerPos)) {
            return null;
        }

        shootTimer = shootCooldown;
        Vector2 direction = new Vector2(playerPos).sub(position).nor();

        return new EnemyBullet(new Vector2(position), direction, bulletSpeed /2, damage);
    }

    public void draw(SpriteBatch batch) {
        if (dead || moveAnimation == null) return;

        // Get current animation frame
        TextureRegion currentFrame = moveAnimation.getKeyFrame(stateTime);

        // Create a copy to avoid modifying the original
        TextureRegion frameToRender = new TextureRegion(currentFrame);

        // Flip if facing left
        if (facingLeft) {
            if (!frameToRender.isFlipX()) {
                frameToRender.flip(true, false);
            }
        } else {
            if (frameToRender.isFlipX()) {
                frameToRender.flip(true, false);
            }
        }

        // Draw the enemy
        float width = frameToRender.getRegionWidth() * 2;
        float height = frameToRender.getRegionHeight() * 2;
        batch.draw(frameToRender,
            position.x - width/2,
            position.y - height/2,
            width, height);
    }


    // Getters
    public Vector2 getPosition() { return position; }
    public boolean isDead() { return dead; }
    public float getDamage() { return damage; }
    public EnemyType getType() { return type; }
    public boolean isFacingLeft() { return facingLeft; }
    public float getShootRange() { return shootRange; }
}
