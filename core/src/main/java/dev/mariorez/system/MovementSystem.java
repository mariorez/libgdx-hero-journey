package dev.mariorez.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import dev.mariorez.component.Flyer;
import dev.mariorez.component.Hero;
import dev.mariorez.component.Transform;

import static com.badlogic.ashley.core.Family.one;

public class MovementSystem extends IteratingSystem {

    public MovementSystem() {
        super(one(Hero.class, Flyer.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        var transform = entity.getComponent(Transform.class);

        // apply acceleration
        transform.velocity.add(
            transform.accelerator.x * deltaTime,
            transform.accelerator.y * deltaTime
        );

        var speed = transform.velocity.len();

        // decrease speed (decelerate) when not accelerating
        if (transform.accelerator.len() == 0f) {
            speed -= transform.deceleration * deltaTime;
        }

        // keep speed within set bounds
        speed = MathUtils.clamp(speed, 0f, transform.maxSpeed);

        // update velocity
        transform.setSpeed(speed);

        // move by
        if (transform.velocity.x != 0f || transform.velocity.y != 0f) {
            transform.position.add(
                transform.velocity.x * deltaTime,
                transform.velocity.y * deltaTime
            );
        }

        // reset acceleration
        transform.accelerator.set(0f, 0f);
    }
}
