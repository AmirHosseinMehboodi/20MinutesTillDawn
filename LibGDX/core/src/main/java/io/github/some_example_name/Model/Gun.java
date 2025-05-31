package io.github.some_example_name.Model;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import io.github.some_example_name.View.GameScreen;

import java.sql.Time;
import java.util.TimerTask;

public class Gun {
    private Vector2 position;
    private Vector2 playerPosition;
    private float gunDistance; // Distance from player (renamed from orbitRadius)
    private float gunRotation; // angle the gun is facing
    private Texture gunTexture;
    private Vector2 shootDirection;
    private int damage;
    private float increaseDamageTime = 0f;
    private int projectile;
    private int timeReload;
    private int ammoMax;
    private int ammo;
    private boolean reloading = false;
    private com.badlogic.gdx.utils.Timer reloadTime = new com.badlogic.gdx.utils.Timer();
    private com.badlogic.gdx.utils.Timer.Task reloadTimeTask = new com.badlogic.gdx.utils.Timer.Task() {
        @Override
        public void run() {
            reloading = false;
            ammo = ammoMax;
        }
    };

    public Gun(Texture gunTexture, float gunDistance, int damage, int projectile, int timeReload, int ammoMax) {
        this.gunTexture = gunTexture;
        this.gunDistance = gunDistance; // Now represents distance in shoot direction
        this.gunRotation = 0f;
        this.position = new Vector2();
        this.playerPosition = new Vector2();
        this.shootDirection = new Vector2(1, 0);
        this.damage = damage;
        this.projectile = projectile;
        this.timeReload = timeReload;
        this.ammoMax = this.ammo = ammoMax;
    }

    public void update(float delta, Vector2 playerPos, Vector2 shootDir, Boolean autoAim, OrthographicCamera camera) {
        // Update player position
        playerPosition.set(playerPos);

        // Always update shoot direction if provided (remove the zero check)

        if(autoAim) {
            shootDirection.set(shootDir);
        } else{
            Vector3 mouseScreen = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mouseScreen);
            Vector2 shootDirect = new Vector2(mouseScreen.x, mouseScreen.y).sub(playerPos).nor();
            shootDirection.set(shootDirect);
        }

            // Normalize only if it's not zero
        if (shootDirection.len() > 0) {
            shootDirection.nor();
        } else {
            // Keep previous direction if new direction is zero
            shootDirection.set(1, 0); // or keep the last valid direction
        }

        // Position gun in the direction of shooting
        position.set(
            playerPosition.x + shootDirection.x * gunDistance,
            playerPosition.y + shootDirection.y * gunDistance
        );

        // Rotate gun to face the shoot direction
        gunRotation = shootDirection.angleDeg();


        if(increaseDamageTime > 0) {
            increaseDamageTime -= delta;
        }
    }

    public void draw(SpriteBatch batch) {
        if (gunTexture != null) {
            float width = gunTexture.getWidth();
            float height = gunTexture.getHeight();
            TextureRegion gunTextures = new TextureRegion(gunTexture);

            if (playerPosition.x > position.x) {
                if (!gunTextures.isFlipY()) {
                    gunTextures.flip(false, true);
                }
            } else {
                if (gunTextures.isFlipY()) {
                    gunTextures.flip(false, true);
                }
            }

            // Draw gun with rotation, centered on gun position
            batch.draw(gunTextures,
                position.x - width/2, position.y - height/2, // position
                width/2, height/2, // origin (center of texture)
                width * 2, height * 2, // size
                1f, 1f, // scale
                gunRotation); // rotation
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getShootDirection() {
        return shootDirection;
    }

    public float getGunRotation() {
        return gunRotation;
    }

    // Renamed methods to match new functionality
    public void setGunDistance(float distance) {
        this.gunDistance = distance;
    }

    public float getGunDistance() {
        return gunDistance;
    }

    // Method to manually set shoot direction
    public void setShootDirection(Vector2 direction) {
        if (direction != null && (direction.x != 0 || direction.y != 0)) {
            shootDirection.set(direction).nor();
        }
    }

    public int getDamage() {
        if (increaseDamageTime > 0) {
            return (int) (damage * 1.25f);
        }
        return damage;
    }

    public int getProjectile() {
        return projectile;
    }

    public int getTimeReload() {
        return timeReload;
    }

    public int getAmmoMax() {
        return ammoMax;
    }

    public int getAmmo() {
        return ammo;
    }

    public void shoots() {
        this.ammo -= 1;
    }

    public Timer getReloadTime() {
        return reloadTime;
    }

    public com.badlogic.gdx.utils.Timer.Task getReloadTimeTask() {
        return reloadTimeTask;
    }

    public boolean isReloading() {
        return reloading;
    }

    public void setReloading(boolean reloading) {
        this.reloading = reloading;
    }

    public void setIncreaseDamageTime(float increaseDamageTime) {
        this.increaseDamageTime = increaseDamageTime;
    }

    public void setProjectile(int projectile) {
        this.projectile += projectile;
    }


    public void setAmmoMax(int ammoMax) {
        this.ammoMax += ammoMax;
    }
}
