package dev.mariorez.component;

import com.badlogic.ashley.core.Component;

public class Solid implements Component {

    public final String name;

    public Solid(String name) {
        this.name = name;
    }
}
