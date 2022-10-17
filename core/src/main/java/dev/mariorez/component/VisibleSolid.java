package dev.mariorez.component;

import com.badlogic.ashley.core.Component;

public class VisibleSolid implements Component {

    public final String name;

    public VisibleSolid(String name) {
        this.name = name;
    }
}
