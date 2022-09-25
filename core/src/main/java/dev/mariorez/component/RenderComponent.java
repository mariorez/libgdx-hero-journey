package dev.mariorez.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class RenderComponent implements Component {

    public Sprite sprite;

    public RenderComponent() {
        sprite = new Sprite();
    }

    public RenderComponent(Sprite sprite) {
        this.sprite = sprite;
    }
}
