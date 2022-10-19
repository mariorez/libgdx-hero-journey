package dev.mariorez.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import dev.mariorez.component.AnimationBag;
import dev.mariorez.component.Render;

public class AnimationSystem extends IteratingSystem {

    public AnimationSystem() {
        super(Family.all(AnimationBag.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        var animationBag = entity.getComponent(AnimationBag.class);
        var sprite = entity.getComponent(Render.class).sprite;

        var animate = animationBag.animations.get(animationBag.current);
        animate.stateTime += deltaTime;

        var textureRegion = animate.animation.getKeyFrame(animate.stateTime, animate.loop);

        sprite.setRegion(textureRegion);
        sprite.setSize(
            (float) textureRegion.getRegionWidth(),
            (float) textureRegion.getRegionHeight()
        );
    }
}
