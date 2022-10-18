package dev.mariorez.component;

import com.badlogic.ashley.core.Component;

public class Hero implements Component {

    public boolean up = false;
    public boolean down = false;
    public boolean left = false;
    public boolean right = false;
    public boolean swingSword = false;

    public boolean isMoving() {
        return up || down || left || right;
    }

    public boolean isStopped() {
        return !isMoving();
    }
}
