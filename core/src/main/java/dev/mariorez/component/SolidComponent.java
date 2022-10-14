package dev.mariorez.component;

import com.badlogic.ashley.core.Component;

public class SolidComponent implements Component {

    public final String name;

    public SolidComponent(String name) {
        this.name = name;
    }
}
