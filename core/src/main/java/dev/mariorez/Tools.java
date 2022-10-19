package dev.mariorez;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import dev.mariorez.component.AnimationBag;

import java.util.HashMap;

public class Tools {
    public static final String ANIMATION_HERO_NORTH = "north";
    public static final String ANIMATION_HERO_SOUTH = "south";
    public static final String ANIMATION_HERO_EAST = "east";
    public static final String ANIMATION_HERO_WEST = "west";
    public static final String ANIMATION_ENEMY_FLYER = "flyer";

    public static float getWorldWidthFromMap(TiledMap map) {
        int tileWidth = (int) map.getProperties().get("tilewidth");
        int numTilesHorizontal = (int) map.getProperties().get("width");
        return tileWidth * numTilesHorizontal;
    }

    public static float getWorldHeightFromMap(TiledMap map) {
        int tileHeight = (int) map.getProperties().get("tileheight");
        int numTilesVertical = (int) map.getProperties().get("height");
        return tileHeight * numTilesVertical;
    }

    public static AnimationBag generateAnimationBag(Texture texture,
                                                    int cols,
                                                    int rows,
                                                    float frameDuration,
                                                    boolean loop,
                                                    String... names) {

        var regions = new TextureRegion(texture).split(
            texture.getWidth() / cols,
            texture.getHeight() / rows
        );

        var mapNames = new HashMap<String, Array<TextureRegion>>();

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                if (mapNames.containsKey(names[row])) {
                    mapNames.get(names[row]).add(new TextureRegion(regions[row][col]));
                } else {
                    var array = new Array<TextureRegion>();
                    array.add(new TextureRegion(regions[row][col]));
                    mapNames.put(names[row], array);
                }
            }
        }

        var bag = new AnimationBag();
        bag.current = names[0];

        for (String name : names) {
            var animate = new AnimationBag.Animate();
            animate.animation = new Animation<>(frameDuration, mapNames.get(name));
            animate.loop = loop;
            bag.animations.put(name, animate);
        }

        return bag;
    }
}
