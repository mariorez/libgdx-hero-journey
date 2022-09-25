package dev.mariorez.util;

import com.badlogic.ashley.core.ComponentMapper;
import dev.mariorez.component.RenderComponent;
import dev.mariorez.component.TransformComponent;

import static com.badlogic.ashley.core.ComponentMapper.getFor;

public class Mappers {
    public static final ComponentMapper<TransformComponent> transform = getFor(TransformComponent.class);
    public static final ComponentMapper<RenderComponent> render = getFor(RenderComponent.class);
}
