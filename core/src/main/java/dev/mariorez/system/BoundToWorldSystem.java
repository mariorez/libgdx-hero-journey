package dev.mariorez.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import dev.mariorez.Sizes;
import dev.mariorez.component.Flyer;
import dev.mariorez.component.Player;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static dev.mariorez.Tools.renderMapper;
import static dev.mariorez.Tools.transformMapper;

public class BoundToWorldSystem extends IteratingSystem {

    private final Sizes sizes;

    public BoundToWorldSystem(Sizes sizes) {
        super(Family.one(Player.class, Flyer.class).get());
        this.sizes = sizes;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        var transform = transformMapper.get(entity);
        var render = renderMapper.get(entity);
        transform.position.x = clamp(transform.position.x, 0f, sizes.worldWidth - render.sprite.getWidth());
        transform.position.y = clamp(transform.position.y, 0f, sizes.worldHeight - render.sprite.getHeight());
    }
}
