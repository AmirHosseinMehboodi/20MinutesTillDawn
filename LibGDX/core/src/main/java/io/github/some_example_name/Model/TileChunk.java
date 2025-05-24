package io.github.some_example_name.Model;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class TileChunk {

    private int chunkX, chunkY;
    private int[][] tiles; // 2D array of tile IDs
    private boolean[][] treeTiles;
    private boolean[][] collisionTiles;
    private float worldX, worldY;

    public TileChunk(int chunkX, int chunkY, TileGenerator generator) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.worldX = chunkX * Map.CHUNK_SIZE * Map.TILE_SIZE;
        this.worldY = chunkY * Map.CHUNK_SIZE * Map.TILE_SIZE;

        treeTiles = new boolean[Map.CHUNK_SIZE][Map.CHUNK_SIZE];
        collisionTiles = new boolean[Map.CHUNK_SIZE][Map.CHUNK_SIZE];

        // Generate tiles for this chunk
        tiles = new int[Map.CHUNK_SIZE][Map.CHUNK_SIZE];
        generator.generateChunk(chunkX, chunkY, treeTiles, collisionTiles, tiles);
    }

    public void render(SpriteBatch batch, ArrayList<Texture> tileTextures) {
        for (int x = 0; x < Map.CHUNK_SIZE; x++) {
            for (int y = 0; y < Map.CHUNK_SIZE; y++) {
                int tileId = tiles[x][y];

                if (tileId >= 0 && tileId < tileTextures.size()) {
                    Texture tileTexture = tileTextures.get(tileId);

                    float drawX = worldX + x * Map.TILE_SIZE;
                    float drawY = worldY + y * Map.TILE_SIZE;

                    batch.draw(tileTexture, drawX, drawY, Map.TILE_SIZE, Map.TILE_SIZE);
                }
            }
        }
    }


    public void renderTrees(SpriteBatch batch, Texture treeTexture) {
        if (treeTexture == null) return;

        for (int x = 0; x < Map.CHUNK_SIZE; x++) {
            for (int y = 0; y < Map.CHUNK_SIZE; y++) {
                if (treeTiles[x][y]) {
                    float drawX = worldX + x * Map.TILE_SIZE;
                    float drawY = worldY + y * Map.TILE_SIZE;

                    // Trees are larger than tiles - adjust size and position
                    float treeWidth = Map.TILE_SIZE * 2.5f;
                    float treeHeight = Map.TILE_SIZE * 3.5f;

                    // Center the tree on the tile and offset upward
                    float offsetX = (Map.TILE_SIZE - treeWidth) / 2f;
                    float offsetY = 0; // Trees grow upward from ground

                    batch.draw(treeTexture,
                        drawX + offsetX, drawY + offsetY,
                        treeWidth, treeHeight);
                }
            }
        }
    }

    public float getWorldX() { return worldX; }
    public float getWorldY() { return worldY; }
}

