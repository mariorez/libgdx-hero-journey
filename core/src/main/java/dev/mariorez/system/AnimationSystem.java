package dev.mariorez.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import dev.mariorez.component.AnimationBag;

import static dev.mariorez.Tools.animationMapper;
import static dev.mariorez.Tools.renderMapper;

public class AnimationSystem extends IteratingSystem {

    public AnimationSystem() {
        super(Family.all(AnimationBag.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        var animationBag = animationMapper.get(entity);

        var animate = animationBag.animations.get(animationBag.current);
        animate.stateTime += deltaTime;

        var textureRegion = animate.animation.getKeyFrame(animate.stateTime, animate.loop);

        var sprite = renderMapper.get(entity).sprite;
        sprite.setRegion(textureRegion);
        sprite.setSize(
            (float) textureRegion.getRegionWidth(),
            (float) textureRegion.getRegionHeight()
        );
    }
}
