package io.github.some_example_name.Model;

public class TileGenerator {
    // Simple noise-like generation for variety
    // You can make this more sophisticated with actual Perlin noise

    public void generateChunk(int chunkX, int chunkY, boolean[][] treeTiles, boolean[][] collisionTiles, int[][] tiles) {
        for (int x = 0; x < Map.CHUNK_SIZE; x++) {
            for (int y = 0; y < Map.CHUNK_SIZE; y++) {
                // World coordinates for this tile
                int worldX = chunkX * Map.CHUNK_SIZE + x;
                int worldY = chunkY * Map.CHUNK_SIZE + y;
                treeTiles[x][y] = false;

                // Simple pseudo-random tile selection based on position
                int tileId = generateTileAt(worldX, worldY);
                tiles[x][y] = tileId;
            }
        }

        generateTrees(chunkX, chunkY, treeTiles, collisionTiles);
    }

    private int generateTileAt(int worldX, int worldY) {
        // Simple hash-based pseudo-random generation
        // This creates a consistent pattern that looks random
        int hash = hash(worldX, worldY);

        // Create different biome-like areas
        int biome = Math.abs(hash) % 100;

        if (biome < 93) {
            return 0; // Grass tile (most common)
        } else if (biome < 94) {
            return 1; // Dirt tile
        } else if (biome < 95) {
            return 2; // Stone tile (rarest)
        } else if (biome < 96) {
            return 3;
        } else if (biome < 97) {
            return 4;
        } else if (biome < 98) {
            return 5;
        } else if (biome < 99) {
            return 6;
        } else {
            return 7;
        }
    }

    private void generateTrees(int chunkX, int chunkY, boolean[][] treeTiles, boolean[][] collisionTiles) {
        // Try to place trees in this chunk
        for (int attempt = 0; attempt < 30; attempt++) { // 30 attempts per chunk
            int seedX = chunkX * 1000 + attempt * 37;
            int seedY = chunkY * 1000 + attempt * 43;
            int hash = hash(seedX, seedY);

            // Only place tree if hash meets criteria (controls density)
            if (Math.abs(hash) % 100 < 8) { // 8% chance per attempt
                int localX = Math.abs(hash % Map.CHUNK_SIZE);
                int localY = Math.abs((hash / 100) % Map.CHUNK_SIZE);

                // Make sure we don't place trees too close to each other
                if (canPlaceTree(localX, localY, treeTiles)) {
                    treeTiles[localX][localY] = true;
                    collisionTiles[localX][localY] = true; // Trees block movement
                }
            }
        }
    }

    private boolean canPlaceTree(int x, int y, boolean[][] treeTiles) {
        // Check minimum distance between trees (2 tiles)
        int minDistance = 2;

        for (int dx = -minDistance; dx <= minDistance; dx++) {
            for (int dy = -minDistance; dy <= minDistance; dy++) {
                int checkX = x + dx;
                int checkY = y + dy;

                // Check within chunk bounds
                if (checkX >= 0 && checkX < Map.CHUNK_SIZE &&
                    checkY >= 0 && checkY < Map.CHUNK_SIZE) {

                    if (treeTiles[checkX][checkY]) {
                        return false; // Too close to another tree
                    }
                }
            }
        }

        return true;
    }


    // Simple hash function for consistent pseudo-randomness
    private int hash(int x, int y) {
        int hash = x;
        hash = (hash << 5) + hash + y;
        hash = (hash << 5) + hash + x;
        return hash;
    }
}
