package dev.mariorez.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import dev.mariorez.component.PlayerComponent;
import dev.mariorez.util.Mappers;
import dev.mariorez.Sizes;

public class CameraSystem extends IteratingSystem {

    private final OrthographicCamera camera;
    private final float minWidth;
    private final float minHeight;
    private final float maxWidth;
    private final float maxHeight;

    public CameraSystem(OrthographicCamera camera, Sizes world) {
        super(Family.all(PlayerComponent.class).get());
        this.camera = camera;
        minWidth = camera.viewportWidth / 2;
        minHeight = camera.viewportHeight / 2;
        maxWidth = world.worldWidth - minWidth;
        maxHeight = world.worldHeight - minHeight;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        var playerPosition = Mappers.transform.get(entity).position;
        camera.position.x = MathUtils.clamp(playerPosition.x, minWidth, maxWidth);
        camera.position.y = MathUtils.clamp(playerPosition.y, minHeight, maxHeight);
        camera.update();
    }
}
