package dev.mariorez;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import dev.mariorez.screen.FirstScreen;

public class GameBoot extends Game {

    public Sizes sizes = new Sizes(960f, 540f);
    private final AssetManager assets = new AssetManager();

    @Override
    public void create() {

        var param = new TextureLoader.TextureParameter();
        param.minFilter = Texture.TextureFilter.Linear;

        assets.setLoader(TiledMap.class, new TmxMapLoader());

        assets.load("map.tmx", TiledMap.class);
        assets.load("npc-1.png", Texture.class, param);

        assets.finishLoading();

        setScreen(new FirstScreen(sizes, assets));
    }
}
