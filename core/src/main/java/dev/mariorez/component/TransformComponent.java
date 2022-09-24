package dev.mariorez.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.Vector2;

public class TransformComponent implements Component {
    public static final ComponentMapper<TransformComponent> mapper = ComponentMapper.getFor(TransformComponent.class);
    public Vector2 position;

    public TransformComponent(Vector2 position) {
        this.position = position;
    }
}
