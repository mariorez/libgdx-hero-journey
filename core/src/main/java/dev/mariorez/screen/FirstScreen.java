package dev.mariorez.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import dev.mariorez.Action;
import dev.mariorez.BaseScreen;
import dev.mariorez.Sizes;
import dev.mariorez.Tools;
import dev.mariorez.component.Player;
import dev.mariorez.component.Render;
import dev.mariorez.component.Reward;
import dev.mariorez.component.Solid;
import dev.mariorez.component.Transform;
import dev.mariorez.system.BoundToWorldSystem;
import dev.mariorez.system.CameraSystem;
import dev.mariorez.system.CollisionSystem;
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
        spawnEntities();

        var mapRenderer = new OrthoCachedTiledMapRenderer(map);
        mapRenderer.setBlending(true);

        engine.addSystem(new InputSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new BoundToWorldSystem(sizes));
        engine.addSystem(new CameraSystem(camera, sizes));
        engine.addSystem(new RenderSystem(batch, camera, mapRenderer));
        engine.addSystem(new CollisionSystem(player));
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

    private void spawnEntities() {
        var zIndex = 0f;

        for (MapObject object : map.getLayers().get("objects").getObjects()) {

            var transform = engine.createComponent(Transform.class);
            transform.zIndex = zIndex++;
            var render = engine.createComponent(Render.class);

            if (object instanceof TiledMapTileMapObject) {
                var obj = (TiledMapTileMapObject) object;
                var type = (String) obj.getTile().getProperties().get("type");
                transform.position.set(obj.getX(), obj.getY());

                switch (type) {
                    case "bush":
                    case "rock":
                        render.sprite = new Sprite(assets.get(type + ".png", Texture.class));
                        engine.addEntity(
                            engine.createEntity()
                                .add(new Solid(type))
                                .add(render)
                                .add(transform));
                        break;

                    case "treasure":
                    case "heart-icon":
                    case "arrow-icon":
                    case "coin":
                        render.sprite = new Sprite(assets.get(type + ".png", Texture.class));
                        engine.addEntity(
                            engine.createEntity()
                                .add(new Reward(type))
                                .add(render)
                                .add(transform));
                        break;

                    case "npc":
                        render.sprite = new Sprite(assets.get(obj.getName() + ".png", Texture.class));
                        engine.addEntity(
                            engine.createEntity()
                                .add(new Solid(obj.getName()))
                                .add(render)
                                .add(transform));
                        break;
                }
            } else {
                var type = (String) object.getProperties().get("type");
                var x = (float) object.getProperties().get("x");
                var y = (float) object.getProperties().get("y");

                switch (type) {
                    case "solid":
                        var width = (float) object.getProperties().get("width");
                        var height = (float) object.getProperties().get("height");
                        render.sprite.setPosition(x, y);
                        render.sprite.setSize(width, height);
                        render.visible = false;
                        engine.addEntity(
                            engine.createEntity()
                                .add(new Solid(object.getProperties().get("type").toString()))
                                .add(render)
                                .add(transform));
                        break;

                    case "player":
                        spawnPlayer(x, y);
                        break;
                }
            }
        }
    }

    private void spawnPlayer(float x, float y) {
        var transform = engine.createComponent(Transform.class);
        transform.position.set(x, y);
        transform.acceleration = 800f;
        transform.deceleration = 800f;
        transform.maxSpeed = 150f;

        var render = engine.createComponent(Render.class);
        render.sprite = new Sprite(assets.get("gatekeeper.png", Texture.class));

        player = engine.createEntity()
            .add(new Player())
            .add(render)
            .add(transform);

        engine.addEntity(player);
    }
}
