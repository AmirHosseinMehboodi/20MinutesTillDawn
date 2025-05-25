package io.github.some_example_name.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Player {
    private User user;
    private Vector2 position;
    private Vector2 movement;
    private float speed;
    private int health;
    private int maxHealth;
    private Gun gun;

    private float shootTimer = 0f;
    private float shootCooldown = 0.2f;

    // Stats that can be upgraded
    private float damage = 0;
    private float range = 1000f;
    private float bulletSpeed = 1000f;

    // Animation variables
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private float stateTime = 0f;
    private boolean isMoving = false;
    private boolean facingLeft = false;

    public Player(float x, float y, ArrayList<Texture> textures, int health, int speed) {
        position = new Vector2(x, y);
        movement = new Vector2();
        this.health = health;
        this.speed = 100 * speed;

        // Initialize animations
        initializeAnimations(textures);
    }

    private void initializeAnimations(ArrayList<Texture> textures) {
        // Assuming the textures are ordered as:
        // Index 0-5: Idle animation frames (6 textures)
        // Index 6-9: Running animation frames (4 textures)

        if (textures.size() < 10) {
            throw new IllegalArgumentException("Need at least 10 textures: 6 for idle, 4 for running");
        }

        // Create idle animation (6 frames)
        TextureRegion[] idleFrames = new TextureRegion[6];
        for (int i = 0; i < 6; i++) {
            idleFrames[i] = new TextureRegion(textures.get(i));
        }
        idleAnimation = new Animation<TextureRegion>(0.15f, idleFrames); // 0.15 seconds per frame
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // Create running animation (4 frames)
        TextureRegion[] runFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            runFrames[i] = new TextureRegion(textures.get(6 + i));
        }
        runAnimation = new Animation<TextureRegion>(0.1f, runFrames); // 0.1 seconds per frame (faster)
        runAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public void update(float delta) {
        // Update state time for animations
        stateTime += delta;

        // Check if player is moving
        isMoving = movement.len() > 0;

        // Update facing direction based on horizontal movement
        if (movement.x < 0) {
            facingLeft = true;
        } else if (movement.x > 0) {
            facingLeft = false;
        }
        // If only moving vertically, keep the current facing direction

        // Update position
        if (isMoving) {
            Vector2 vel = new Vector2(movement).nor().scl(speed * delta);
            position.add(vel);
        }

        // Update timers
        shootTimer -= delta;
    }

    public void setMovement(Vector2 movement) {
        this.movement.set(movement);
    }

    public boolean canShoot() {
        return shootTimer <= 0;
    }

    public ArrayList<Bullet> shoot(Vector2 direction) {
        if (canShoot()) {
            shootTimer = shootCooldown;
            ArrayList<Bullet> shot = new ArrayList<>();

            int projectileCount = gun.getProjectile();

            if (projectileCount == 1) {
                // Fire straight with no spread
                shot.add(new Bullet(new Vector2(gun.getPosition()), new Vector2(direction).nor(), bulletSpeed, gun.getDamage(), range));
            } else {
                // Spread angle in degrees
                float spreadAngle = 20f;

                // Angle between each bullet
                float angleStep = spreadAngle / (projectileCount - 1);

                // Convert base direction to angle
                float baseAngle = direction.angleDeg();

                for (int i = 0; i < projectileCount; i++) {
                    float offset = -spreadAngle / 2 + i * angleStep;

                    // Rotate direction
                    Vector2 spreadDir = new Vector2(1, 0).setAngleDeg(baseAngle + offset).nor();

                    shot.add(new Bullet(new Vector2(gun.getPosition()), spreadDir, bulletSpeed, gun.getDamage(), range));
                }
            }
            
            return shot;
        }
        return null;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) health = maxHealth;
    }

    // Alternative draw method if you want to specify size
    public void draw(SpriteBatch batch, float width, float height) {
        TextureRegion currentFrame;

        if (isMoving) {
            width *= 1.3f;
            currentFrame = runAnimation.getKeyFrame(stateTime);
        } else {
            currentFrame = idleAnimation.getKeyFrame(stateTime);
        }

        // Create a copy of the texture region to avoid modifying the original
        TextureRegion frameToRender = new TextureRegion(currentFrame);

        // Flip horizontally if facing left and moving
        if (facingLeft) {
            if (!frameToRender.isFlipX()) {
                frameToRender.flip(true, false);
            }
        } else {
            if (frameToRender.isFlipX()) {
                frameToRender.flip(true, false);
            }
        }

        batch.draw(frameToRender,
                position.x - width/2,
                position.y - height/2,
                width, height);
    }

    // Method to reset animation state (useful when switching between animations)
    public void resetAnimation() {
        stateTime = 0f;
    }

    // Getters
    public Vector2 getPosition() { return position; }
    public boolean isDead() { return health <= 0; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public float getDamage() { return damage; }
    public boolean isMoving() { return isMoving; }
    public void setGun(Gun gun) {
        this.gun = gun;
    }
}
