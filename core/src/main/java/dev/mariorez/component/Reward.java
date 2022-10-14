package dev.mariorez.component;

import com.badlogic.ashley.core.Component;

public class Reward implements Component {

    public final String name;

    public Reward(String name) {
        this.name = name;
    }
}
