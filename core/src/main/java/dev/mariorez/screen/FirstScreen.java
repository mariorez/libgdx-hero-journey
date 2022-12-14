package dev.mariorez.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
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
import dev.mariorez.component.Flyer;
import dev.mariorez.component.Hero;
import dev.mariorez.component.InvisibleSolid;
import dev.mariorez.component.Npc;
import dev.mariorez.component.Render;
import dev.mariorez.component.Reward;
import dev.mariorez.component.Sword;
import dev.mariorez.component.Transform;
import dev.mariorez.component.VisibleSolid;
import dev.mariorez.system.AnimationSystem;
import dev.mariorez.system.BoundToWorldSystem;
import dev.mariorez.system.CameraSystem;
import dev.mariorez.system.CollisionSystem;
import dev.mariorez.system.InputSystem;
import dev.mariorez.system.MovementSystem;
import dev.mariorez.system.RandomMoveSystem;
import dev.mariorez.system.RenderSystem;
import dev.mariorez.system.SwordAttackSystem;

import java.util.Random;

import static dev.mariorez.Tools.ANIMATION_ENEMY_FLYER;
import static dev.mariorez.Tools.ANIMATION_HERO_EAST;
import static dev.mariorez.Tools.ANIMATION_HERO_NORTH;
import static dev.mariorez.Tools.ANIMATION_HERO_SOUTH;
import static dev.mariorez.Tools.ANIMATION_HERO_WEST;
import static dev.mariorez.Tools.generateAnimationBag;

public class FirstScreen extends BaseScreen {

    private final Engine engine = new Engine();
    private final AssetManager assets;
    private final TiledMap map;
    private Entity hero;

    public FirstScreen(Sizes sizes, AssetManager assets) {
        super(sizes);

        this.assets = assets;

        map = assets.get("map.tmx", TiledMap.class);
        sizes.worldWidth = Tools.getWorldWidthFromMap(map);
        sizes.worldHeight = Tools.getWorldHeightFromMap(map);

        buildControls();
        spawnEntities();
        spawnSword();

        var mapRenderer = new OrthoCachedTiledMapRenderer(map);
        mapRenderer.setBlending(true);

        var renderSystem = new RenderSystem(batch, camera, mapRenderer);

        engine.addSystem(new InputSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new SwordAttackSystem(renderSystem));
        engine.addSystem(new BoundToWorldSystem(sizes));
        engine.addSystem(new RandomMoveSystem());
        engine.addSystem(new AnimationSystem());
        engine.addSystem(new CameraSystem(camera, sizes));
        engine.addSystem(renderSystem);
        engine.addSystem(new CollisionSystem());
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
    }

    @Override
    public void doAction(Action action) {
        var heroComponent = hero.getComponent(Hero.class);
        switch (action) {
            case UP:
                heroComponent.up = action.starting;
                break;
            case DOWN:
                heroComponent.down = action.starting;
                break;
            case LEFT:
                heroComponent.left = action.starting;
                break;
            case RIGHT:
                heroComponent.right = action.starting;
                break;
            case SWING_SWORD:
                heroComponent.swordAttack = action.starting;
                break;
            default:
                super.doAction(action);
        }
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
        actionMap.put(Input.Keys.Z, Action.SWING_SWORD);
    }

    private void spawnEntities() {
        var zIndex = 0f;

        for (MapObject object : map.getLayers().get("objects").getObjects()) {

            var transform = new Transform();
            transform.zIndex = zIndex++;
            var render = new Render();

            if (object instanceof TiledMapTileMapObject) {
                var obj = (TiledMapTileMapObject) object;
                var type = (String) obj.getTile().getProperties().get("type");
                transform.position.set(obj.getX(), obj.getY());

                switch (type) {
                    case "flyer":
                        spawnFlyer(obj.getX(), obj.getY());
                        break;

                    case "bush":
                    case "rock":
                        render.sprite = new Sprite(assets.get(type + ".png", Texture.class));
                        engine.addEntity(new Entity()
                            .add(new VisibleSolid(type))
                            .add(render)
                            .add(transform));
                        break;

                    case "treasure":
                    case "heart-icon":
                    case "arrow-icon":
                    case "coin":
                        render.sprite = new Sprite(assets.get(type + ".png", Texture.class));
                        engine.addEntity(new Entity()
                            .add(new Reward(type))
                            .add(render)
                            .add(transform));
                        break;

                    case "npc":
                        render.sprite = new Sprite(assets.get(obj.getName() + ".png", Texture.class));
                        engine.addEntity(new Entity()
                            .add(new Npc(obj.getName()))
                            .add(render)
                            .add(transform));
                        break;
                }
            } else {
                var type = (String) object.getProperties().get("type");
                var x = (float) object.getProperties().get("x");
                var y = (float) object.getProperties().get("y");

                switch (type) {
                    case "hero":
                        spawnHero(x, y);
                        break;

                    case "solid":
                        var width = (float) object.getProperties().get("width");
                        var height = (float) object.getProperties().get("height");
                        render.sprite.setPosition(x, y);
                        render.sprite.setSize(width, height);
                        engine.addEntity(new Entity()
                            .add(new InvisibleSolid())
                            .add(render)
                            .add(transform));
                        break;
                }
            }
        }
    }

    private float flyerIndex = -0.1f;

    private void spawnFlyer(float x, float y) {
        var animationBag = generateAnimationBag(
            assets.get("flyer.png", Texture.class),
            4,
            1,
            0.1f,
            true,
            ANIMATION_ENEMY_FLYER);

        var speed = (float) new Random().ints(50, 80).findFirst().getAsInt();
        var angle = (float) new Random().ints(0, 360).findFirst().getAsInt();

        var transform = new Transform();
        transform.position.set(x, y);
        transform.zIndex = flyerIndex;
        transform.maxSpeed = 80f;
        transform.setSpeed(speed);
        transform.setMotionAngle(angle);

        engine.addEntity(new Entity()
            .add(new Flyer())
            .add(new Render())
            .add(transform)
            .add(animationBag));

        flyerIndex -= 0.1f;
    }

    private void spawnHero(float x, float y) {
        var animationBag = generateAnimationBag(
            assets.get("hero.png", Texture.class),
            4,
            4,
            0.2f,
            true,
            ANIMATION_HERO_SOUTH, ANIMATION_HERO_WEST, ANIMATION_HERO_EAST, ANIMATION_HERO_NORTH);

        var transform = new Transform();
        transform.position.set(x, y);
        transform.acceleration = 800f;
        transform.deceleration = 800f;
        transform.maxSpeed = 150f;

        hero = new Entity()
            .add(new Hero())
            .add(new Render())
            .add(transform)
            .add(animationBag);

        engine.addEntity(hero);
    }

    private void spawnSword() {
        var transform = new Transform();
        var render = new Render();
        render.sprite = new Sprite(assets.get("sword.png", Texture.class));
        render.visible = false;

        engine.addEntity(new Entity()
            .add(new Sword())
            .add(transform)
            .add(render));
    }
}
