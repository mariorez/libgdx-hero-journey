package dev.mariorez.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import dev.mariorez.Action;
import dev.mariorez.BaseScreen;
import dev.mariorez.Sizes;
import dev.mariorez.Tools;
import dev.mariorez.component.PlayerComponent;
import dev.mariorez.component.RenderComponent;
import dev.mariorez.component.TransformComponent;
import dev.mariorez.system.BoundToWorldSystem;
import dev.mariorez.system.CameraSystem;
import dev.mariorez.system.InputSystem;
import dev.mariorez.system.MovementSystem;
import dev.mariorez.system.RenderSystem;

import static dev.mariorez.Tools.playerMapper;

public class FirstScreen extends BaseScreen {

    private final PooledEngine engine = new PooledEngine();
    private final AssetManager assets;
    private final TiledMap map;
    private Entity player;

    public FirstScreen(Sizes sizes, AssetManager assets) {
        super(sizes);

        this.assets = assets;

        map = assets.get("map.tmx", TiledMap.class);
        sizes.worldWidth = Tools.getWorldWidth(map);
        sizes.worldHeight = Tools.getWorldHeight(map);

        buildControls();
        spawnPlayer();

        var mapRenderer = new OrthoCachedTiledMapRenderer(map);
        mapRenderer.setBlending(true);

        engine.addSystem(new InputSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new BoundToWorldSystem(sizes));
        engine.addSystem(new CameraSystem(camera, sizes));
        engine.addSystem(new RenderSystem(batch, camera, mapRenderer));
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
    }

    @Override
    public void doAction(Action action) {
        var playerInput = playerMapper.get(player);
        switch (action) {
            case UP:
                playerInput.up = action.starting;
                break;
            case DOWN:
                playerInput.down = action.starting;
                break;
            case LEFT:
                playerInput.left = action.starting;
                break;
            case RIGHT:
                playerInput.right = action.starting;
                break;
        }
        super.doAction(action);
    }

    private void buildControls() {
        actionMap.put(Input.Keys.UP, Action.UP);
        actionMap.put(Input.Keys.DOWN, Action.DOWN);
        actionMap.put(Input.Keys.LEFT, Action.LEFT);
        actionMap.put(Input.Keys.RIGHT, Action.RIGHT);
        actionMap.put(Input.Keys.W, Action.UP);
        actionMap.put(Input.Keys.S, Action.DOWN);
        actionMap.put(Input.Keys.A, Action.LEFT);
        actionMap.put(Input.Keys.D, Action.RIGHT);
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
        transform.acceleration = 800f;
        transform.deceleration = 800f;
        transform.maxSpeed = 150f;

        var texture = assets.get("npc-1.png", Texture.class);
        var render = engine.createComponent(RenderComponent.class);
        render.sprite.setRegion(texture);
        render.sprite.setSize(texture.getWidth(), texture.getHeight());

        player = engine.createEntity()
            .add(new PlayerComponent())
            .add(render)
            .add(transform);

        engine.addEntity(player);
    }
}
