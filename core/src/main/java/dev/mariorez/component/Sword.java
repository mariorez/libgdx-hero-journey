package dev.mariorez.component;

import com.badlogic.ashley.core.Component;

import static dev.mariorez.component.Sword.Direction.CLOCKWISE;

public class Sword implements Component {

    public float attackArc = 90;
    public float attackSpeed = 4f;
    public float startRotation = 0f;
    public float currentRotation = 0f;
    public Direction direction = CLOCKWISE;

    public enum Direction {
        CLOCKWISE,
        COUNTERCLOCKWISE;
    }

    public boolean reachedTheEnd() {
        return (currentRotation - startRotation) >= attackArc;
    }

    public boolean cameBackToTheStart() {
        return currentRotation <= startRotation;
    }

    public float rotate(Direction direction) {
        currentRotation = (CLOCKWISE == direction)
            ? currentRotation + attackSpeed
            : currentRotation - attackSpeed;

        return currentRotation;
    }
}
