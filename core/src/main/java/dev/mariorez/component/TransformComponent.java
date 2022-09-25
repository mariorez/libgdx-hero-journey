package dev.mariorez.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TransformComponent implements Component, Poolable, Comparable<TransformComponent> {

    public Vector2 position;
    public float zIndex = 0f;
    public Vector2 velocity = new Vector2();
    public Vector2 accelerator = new Vector2();
    public float acceleration = 0f;
    public float deceleration = 0f;
    public float maxSpeed = 0f;
    public float rotation = 0f;

    public TransformComponent() {
        position = new Vector2();
    }

    public TransformComponent(float x, float y) {
        position = new Vector2(x, y);
    }

    @Override
    public void reset() {
        position.set(0, 0);
        zIndex = 0f;
        velocity.set(0, 0);
        accelerator.set(0, 0);
        acceleration = 0f;
        deceleration = 0f;
        maxSpeed = 0f;
        rotation = 0f;
    }

    @Override
    public int compareTo(TransformComponent o) {
        return Float.compare(o.zIndex, zIndex);
    }
}
