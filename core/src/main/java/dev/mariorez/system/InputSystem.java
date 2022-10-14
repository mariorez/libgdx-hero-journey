package dev.mariorez.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import dev.mariorez.component.Player;

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

        if (playerInput.isStopped()) return;

        var transform = transformMapper.get(entity);

        var speed = speedUp.set(transform.acceleration, 0f);

        if (playerInput.right) transform.accelerator.add(speed.setAngleDeg(0f));
        if (playerInput.up) transform.accelerator.add(speed.setAngleDeg(90f));
        if (playerInput.left) transform.accelerator.add(speed.setAngleDeg(180f));
        if (playerInput.down) transform.accelerator.add(speed.setAngleDeg(270f));
    }
}
