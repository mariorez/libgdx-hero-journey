package dev.mariorez.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class Transform implements Component, Comparable<Transform> {

    public Vector2 position = new Vector2();
    public float zIndex = 0f;
    public Vector2 velocity = new Vector2();
    public Vector2 accelerator = new Vector2();
    public float acceleration = 0f;
    public float deceleration = 0f;
    public float maxSpeed = 0f;
    public float rotation = 0f;

    public void setSpeed(float speed) {
        if (velocity.len() == 0f) velocity.set(speed, 0f);
        else velocity.setLength(speed);
    }

    public void setMotionAngle(float angle) {
        velocity.setAngleDeg(angle);
    }

    public float getMotionAngle() {
        return velocity.angleDeg();
    }

    public void rotateBy(float degrees) {
        if (degrees != 0f) rotation = (rotation + degrees) % 360;
    }

    @Override
    public int compareTo(Transform o) {
        return Float.compare(o.zIndex, zIndex);
    }
}
