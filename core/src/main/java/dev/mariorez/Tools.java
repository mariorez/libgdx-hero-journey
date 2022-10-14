package dev.mariorez;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.maps.tiled.TiledMap;
import dev.mariorez.component.Player;
import dev.mariorez.component.Render;
import dev.mariorez.component.Transform;

import static com.badlogic.ashley.core.ComponentMapper.getFor;

public class Tools {
    public static final ComponentMapper<Player> playerMapper = getFor(Player.class);
    public static final ComponentMapper<Transform> transformMapper = getFor(Transform.class);
    public static final ComponentMapper<Render> renderMapper = getFor(Render.class);

    public static float getWorldWidth(TiledMap map) {
        int tileWidth = (int) map.getProperties().get("tilewidth");
        int numTilesHorizontal = (int) map.getProperties().get("width");
        return tileWidth * numTilesHorizontal;
    }

    public static float getWorldHeight(TiledMap map) {
        int tileHeight = (int) map.getProperties().get("tileheight");
        int numTilesVertical = (int) map.getProperties().get("height");
        return tileHeight * numTilesVertical;
    }
}
