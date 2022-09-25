package dev.mariorez.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pool.Poolable;

public class RenderComponent implements Component, Poolable {

    public Sprite sprite = new Sprite();

    @Override
    public void reset() {
        sprite = new Sprite();
    }
}
