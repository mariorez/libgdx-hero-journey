package dev.mariorez.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import dev.mariorez.component.Player;

import static dev.mariorez.Tools.ANIMATION_HERO_EAST;
import static dev.mariorez.Tools.ANIMATION_HERO_NORTH;
import static dev.mariorez.Tools.ANIMATION_HERO_SOUTH;
import static dev.mariorez.Tools.ANIMATION_HERO_WEST;
import static dev.mariorez.Tools.animationMapper;
import static dev.mariorez.Tools.playerMapper;
import static dev.mariorez.Tools.transformMapper;

public class InputSystem extends IteratingSystem {

    public InputSystem() {
        super(Family.all(Player.class).get());
    }

    private final Vector2 speedUp = new Vector2();


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        var playerInput = playerMapper.get(entity);
        var animation = animationMapper.get(entity);
        var animate = animation.animations.get(animation.current);

        if (playerInput.isStopped()) {
            animate.loop = false;
            return;
        }

        animate.loop = true;

        var transform = transformMapper.get(entity);

        var speed = speedUp.set(transform.acceleration, 0f);

        if (playerInput.right) transform.accelerator.add(speed.setAngleDeg(0f));
        if (playerInput.up) transform.accelerator.add(speed.setAngleDeg(90f));
        if (playerInput.left) transform.accelerator.add(speed.setAngleDeg(180f));
        if (playerInput.down) transform.accelerator.add(speed.setAngleDeg(270f));

        var angleDeg = transform.velocity.angleDeg();
        if (angleDeg >= 0.0 && angleDeg <= 70.0) animation.current = ANIMATION_HERO_EAST;
        if (angleDeg >= 70.1 && angleDeg <= 110.0) animation.current = ANIMATION_HERO_NORTH;
        if (angleDeg >= 110.1 && angleDeg <= 250.0) animation.current = ANIMATION_HERO_WEST;
        if (angleDeg >= 250.1 && angleDeg <= 290.0) animation.current = ANIMATION_HERO_SOUTH;
        if (angleDeg >= 290.1 && angleDeg <= 360.0) animation.current = ANIMATION_HERO_EAST;
    }
}
