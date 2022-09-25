package dev.mariorez.screen;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import dev.mariorez.BaseScreen;
import dev.mariorez.component.PlayerComponent;
import dev.mariorez.component.RenderComponent;
import dev.mariorez.component.TransformComponent;
import dev.mariorez.system.CameraSystem;
import dev.mariorez.system.RenderSystem;
import dev.mariorez.util.World;

public class FirstScreen extends BaseScreen {

    private final PooledEngine engine = new PooledEngine();
    private final AssetManager assets;
    private final TiledMap map;

    public FirstScreen(AssetManager assets) {
        this.assets = assets;
        map = assets.get("map.tmx", TiledMap.class);

        spawnPlayer();

        var mapRenderer = new OrthoCachedTiledMapRenderer(map);
        mapRenderer.setBlending(true);

        engine.addSystem(new CameraSystem(camera, new World(map)));
        engine.addSystem(new RenderSystem(batch, camera, mapRenderer));
    }

    private void spawnPlayer() {
        var x = 0f;
        var y = 0f;

        for (MapObject object : map.getLayers().get("objects").getObjects()) {
            if ("start".equals(object.getName())) {
                x = (float) object.getProperties().get("x");
                y = (float) object.getProperties().get("y");
                break;
            }
        }

        var transform = engine.createComponent(TransformComponent.class);
        transform.position.set(x, y);

        var texture = assets.get("npc-1.png", Texture.class);
        var render = engine.createComponent(RenderComponent.class);
        render.sprite.setRegion(texture);
        render.sprite.setSize(texture.getWidth(), texture.getHeight());

        engine.addEntity(engine.createEntity()
                .add(new PlayerComponent())
                .add(render)
                .add(transform));
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
    }
}
