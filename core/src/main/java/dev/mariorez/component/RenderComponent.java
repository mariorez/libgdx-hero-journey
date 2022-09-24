package dev.mariorez.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RenderComponent implements Component {
    public static final ComponentMapper<RenderComponent> mapper = ComponentMapper.getFor(RenderComponent.class);
    public Sprite sprite;

    public RenderComponent(Sprite sprite) {
        this.sprite = sprite;
    }
}
