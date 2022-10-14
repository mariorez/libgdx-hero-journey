package dev.mariorez;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
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

        Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter() {
            public boolean keyDown(int keycode) {
                var currentScreen = (BaseScreen) getScreen();
                if (currentScreen.actionMap.containsKey(keycode)) {
                    var action = currentScreen.actionMap.get(keycode);
                    action.starting = true;
                    currentScreen.doAction(action);
                }
                return super.keyDown(keycode);
            }

            public boolean keyUp(int keycode) {
                var currentScreen = (BaseScreen) getScreen();
                if (currentScreen.actionMap.containsKey(keycode)) {
                    var action = currentScreen.actionMap.get(keycode);
                    action.starting = false;
                    currentScreen.doAction(action);
                }
                return super.keyDown(keycode);
            }
        }));

        var param = new TextureLoader.TextureParameter();
        param.minFilter = Texture.TextureFilter.Linear;

        assets.setLoader(TiledMap.class, new TmxMapLoader());

        assets.load("map.tmx", TiledMap.class);
        assets.load("bush.png", Texture.class, param);
        assets.load("rock.png", Texture.class, param);
        assets.load("gatekeeper.png", Texture.class, param);
        assets.load("shopkeeper.png", Texture.class, param);
        assets.load("treasure.png", Texture.class, param);
        assets.load("heart-icon.png", Texture.class, param);
        assets.load("arrow-icon.png", Texture.class, param);
        assets.load("coin.png", Texture.class, param);

        assets.finishLoading();

        setScreen(new FirstScreen(sizes, assets));
    }
}
