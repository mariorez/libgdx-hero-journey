package dev.mariorez.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import dev.mariorez.BaseScreen;
import dev.mariorez.component.RenderComponent;
import dev.mariorez.component.TransformComponent;
import dev.mariorez.system.RenderSystem;

public class FirstScreen extends BaseScreen {

    private final PooledEngine engine = new PooledEngine();

    public FirstScreen(AssetManager assets) {

        var tiledMap = assets.get("map.tmx", TiledMap.class);
        var mapRenderer = new OrthoCachedTiledMapRenderer(tiledMap);
        mapRenderer.setBlending(true);

        var texture = assets.get("npc-1.png", Texture.class);

        var render = new RenderComponent(new Sprite(
                texture,
                texture.getWidth(),
                texture.getHeight()
        ));

        var transform = new TransformComponent(new Vector2(50, 50));

        engine.addEntity(new Entity().add(render).add(transform));
        engine.addSystem(new RenderSystem(batch, camera, mapRenderer));
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
    }
}
