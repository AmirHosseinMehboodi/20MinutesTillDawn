package io.github.some_example_name.Model;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Pickup {

    private Vector2 position;
    private Texture texture;

    public Pickup(Vector2 pos) {
        position = new Vector2(pos);
        texture = new Texture(Gdx.files.internal("Sprite/T_DiamondFilled.png"));
    }


    public void applyEffect(Player player) {
        player.increaseXP();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, (float) texture.getWidth() / 16, (float) texture.getHeight() / 16);
    }

    public Vector2 getPosition() { return position; }
}
