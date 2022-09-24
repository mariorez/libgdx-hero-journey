package dev.mariorez;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import dev.mariorez.screen.FirstScreen;

public class GameBoot extends Game {

    public static final int WINDOW_WIDTH = 960;
    public static final int WINDOW_HEIGHT = 540;
    private AssetManager assets = new AssetManager();

    @Override
    public void create() {

        var param = new TextureLoader.TextureParameter();
        param.minFilter = Texture.TextureFilter.Linear;
        param.genMipMaps = true;

        assets.load("npc-1.png", Texture.class, param);

        assets.finishLoading();

        setScreen(new FirstScreen(assets));
    }
}
