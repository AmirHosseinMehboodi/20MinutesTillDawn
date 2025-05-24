package io.github.some_example_name.Model;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Pickup {

    private Vector2 position;
    private float lifeTime = 30f; // Disappear after 30 seconds

    public Pickup(Vector2 pos) {
        position = new Vector2(pos);
    }

    public void update(float delta) {
        lifeTime -= delta;
    }

    public void applyEffect(Player player) {
//        player.(25); TODO
    }

    public void draw(SpriteBatch batch) {
        // Draw pickup sprite based on type
    }

    public Vector2 getPosition() { return position; }
    public boolean shouldRemove() { return lifeTime <= 0; }
}
