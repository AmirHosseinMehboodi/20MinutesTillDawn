package io.github.some_example_name.Model;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {
    public static final int TILE_SIZE = 64; // Size of each tile in pixels
    public static final int CHUNK_SIZE = 16; // 16x16 tiles per chunk
    public static final int CHUNK_PIXEL_SIZE = CHUNK_SIZE * TILE_SIZE;

    private HashMap<String, TileChunk> loadedChunks;
    private ArrayList<Texture> tileTextures;
    private Texture treeTexture;
    private TileGenerator tileGenerator;

    public Map() {
        loadedChunks = new HashMap<>();
        tileTextures = new ArrayList<>();
        tileGenerator = new TileGenerator();

        loadTileTextures();
    }

    private void loadTileTextures() {
        // Load your tile textures here
         tileTextures.add(new Texture("Sprite/T_ForestTiles_0.png"));
         tileTextures.add(new Texture("Sprite/T_ForestTiles_1.png"));
         tileTextures.add(new Texture("Sprite/T_ForestTiles_3.png"));
         tileTextures.add(new Texture("Sprite/T_ForestTiles_4.png"));
         tileTextures.add(new Texture("Sprite/T_ForestTiles_5.png"));
         tileTextures.add(new Texture("Sprite/T_ForestTiles_6.png"));
         tileTextures.add(new Texture("Sprite/T_ForestTiles_7.png"));
         tileTextures.add(new Texture("Sprite/T_TileGrass.png"));

         treeTexture = new Texture("Sprite/T_TreeMonster_0.png");
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        // Calculate which chunks are visible
        float camX = camera.position.x;
        float camY = camera.position.y;
        float camWidth = camera.viewportWidth * camera.zoom;
        float camHeight = camera.viewportHeight * camera.zoom;

        // Calculate chunk boundaries
        int startChunkX = (int) Math.floor((camX - camWidth/2) / CHUNK_PIXEL_SIZE);
        int endChunkX = (int) Math.ceil((camX + camWidth/2) / CHUNK_PIXEL_SIZE);
        int startChunkY = (int) Math.floor((camY - camHeight/2) / CHUNK_PIXEL_SIZE);
        int endChunkY = (int) Math.ceil((camY + camHeight/2) / CHUNK_PIXEL_SIZE);

        // Load and render visible chunks
        for (int chunkX = startChunkX; chunkX <= endChunkX; chunkX++) {
            for (int chunkY = startChunkY; chunkY <= endChunkY; chunkY++) {
                TileChunk chunk = getOrCreateChunk(chunkX, chunkY);
                chunk.render(batch, tileTextures);
            }
        }

        for (int chunkX = startChunkX; chunkX <= endChunkX; chunkX++) {
            for (int chunkY = startChunkY; chunkY <= endChunkY; chunkY++) {
                TileChunk chunk = getOrCreateChunk(chunkX, chunkY);
                chunk.renderTrees(batch, treeTexture);
            }
        }

        // Unload chunks that are too far away (memory management)
        unloadDistantChunks(camX, camY);
    }

    private TileChunk getOrCreateChunk(int chunkX, int chunkY) {
        String key = chunkX + "," + chunkY;
        TileChunk chunk = loadedChunks.get(key);

        if (chunk == null) {
            chunk = new TileChunk(chunkX, chunkY, tileGenerator);
            loadedChunks.put(key, chunk);
        }

        return chunk;
    }

    private void unloadDistantChunks(float camX, float camY) {
        float unloadDistance = CHUNK_PIXEL_SIZE * 5; // Unload chunks 5 chunks away

        loadedChunks.entrySet().removeIf(entry -> {
            TileChunk chunk = entry.getValue();
            float chunkCenterX = chunk.getWorldX() + CHUNK_PIXEL_SIZE / 2f;
            float chunkCenterY = chunk.getWorldY() + CHUNK_PIXEL_SIZE / 2f;

            float distance = (float) Math.sqrt(
                Math.pow(chunkCenterX - camX, 2) +
                    Math.pow(chunkCenterY - camY, 2)
            );

            return distance > unloadDistance;
        });
    }

    public void dispose() {
        for (Texture texture : tileTextures) {
            texture.dispose();
        }
    }
}

