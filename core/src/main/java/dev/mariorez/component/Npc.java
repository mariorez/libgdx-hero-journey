package dev.mariorez.component;

import com.badlogic.ashley.core.Component;

public class Npc implements Component {

    public final String name;

    public Npc(String name) {
        this.name = name;
    }
}
