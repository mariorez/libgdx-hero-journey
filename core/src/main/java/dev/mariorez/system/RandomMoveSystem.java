package dev.mariorez.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import dev.mariorez.component.Flyer;
import dev.mariorez.component.Transform;

import java.util.Random;

public class RandomMoveSystem extends IteratingSystem {

    public RandomMoveSystem() {
        super(Family.all(Flyer.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        var test = new Random().nextInt(121);
        if (test != 0) return;

        var angle = (float) new Random().nextInt(361);

        entity.getComponent(Transform.class).setMotionAngle(angle);
    }
}
