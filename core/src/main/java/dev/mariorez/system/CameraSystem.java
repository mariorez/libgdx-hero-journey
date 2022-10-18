package dev.mariorez.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import dev.mariorez.Sizes;
import dev.mariorez.component.Hero;

import static com.badlogic.ashley.core.Family.all;
import static dev.mariorez.Tools.transformMapper;

public class CameraSystem extends IteratingSystem {

    private final OrthographicCamera camera;
    private final float minWidth;
    private final float minHeight;
    private final float maxWidth;
    private final float maxHeight;

    public CameraSystem(OrthographicCamera camera, Sizes world) {
        super(all(Hero.class).get());
        this.camera = camera;
        minWidth = camera.viewportWidth / 2;
        minHeight = camera.viewportHeight / 2;
        maxWidth = world.worldWidth - minWidth;
        maxHeight = world.worldHeight - minHeight;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        var heroPosition = transformMapper.get(entity).position;
        camera.position.x = MathUtils.clamp(heroPosition.x, minWidth, maxWidth);
        camera.position.y = MathUtils.clamp(heroPosition.y, minHeight, maxHeight);
        camera.update();
    }
}
