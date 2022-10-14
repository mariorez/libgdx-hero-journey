package dev.mariorez;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.maps.tiled.TiledMap;
import dev.mariorez.component.PlayerComponent;
import dev.mariorez.component.RenderComponent;
import dev.mariorez.component.TransformComponent;

import static com.badlogic.ashley.core.ComponentMapper.getFor;

public class Tools {
    public static final ComponentMapper<PlayerComponent> playerMapper = getFor(PlayerComponent.class);
    public static final ComponentMapper<TransformComponent> transformMapper = getFor(TransformComponent.class);
    public static final ComponentMapper<RenderComponent> renderMapper = getFor(RenderComponent.class);

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
