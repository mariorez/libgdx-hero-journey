package dev.mariorez.util;

import com.badlogic.gdx.maps.tiled.TiledMap;

public class World {

    public final float width;
    public final float height;

    public World(TiledMap map) {
        int tileWidth = (int) map.getProperties().get("tilewidth");
        int tileHeight = (int) map.getProperties().get("tileheight");
        int numTilesHorizontal = (int) map.getProperties().get("width");
        int numTilesVertical = (int) map.getProperties().get("height");
        this.width = tileWidth * numTilesHorizontal;
        this.height = tileHeight * numTilesVertical;
    }
}
