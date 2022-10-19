package dev.mariorez.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import dev.mariorez.Sizes;
import dev.mariorez.component.Flyer;
import dev.mariorez.component.Hero;
import dev.mariorez.component.Render;
import dev.mariorez.component.Transform;

import static com.badlogic.ashley.core.Family.one;
import static com.badlogic.gdx.math.MathUtils.clamp;

public class BoundToWorldSystem extends IteratingSystem {

    private final Sizes sizes;

    public BoundToWorldSystem(Sizes sizes) {
        super(one(Hero.class, Flyer.class).get());
        this.sizes = sizes;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        var transform = entity.getComponent(Transform.class);
        var render = entity.getComponent(Render.class);
        transform.position.x = clamp(transform.position.x, 0f, sizes.worldWidth - render.sprite.getWidth());
        transform.position.y = clamp(transform.position.y, 0f, sizes.worldHeight - render.sprite.getHeight());
    }
}
