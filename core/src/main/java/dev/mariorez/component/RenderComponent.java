package dev.mariorez.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Pool.Poolable;

public class RenderComponent implements Component, Poolable {

    public Sprite sprite = new Sprite();
    public Polygon polygon;

    public Polygon getPolygon() {
        if (polygon != null) {
            polygon.setPosition(sprite.getX(), sprite.getY());
            polygon.setOrigin(sprite.getOriginX(), sprite.getOriginY());
            polygon.setRotation(sprite.getRotation());
            polygon.setScale(sprite.getScaleX(), sprite.getScaleY());
            return polygon;
        }

        float[] vertices = {
            0f, 0f,
            sprite.getWidth(), 0f,
            sprite.getWidth(), sprite.getHeight(),
            0f, sprite.getHeight()
        };

        polygon = new Polygon(vertices);

        return getPolygon();
    }

    public Polygon getPolygon(int sides) {
        if (polygon != null) {
            return getPolygon();
        }

        float[] vertices = new float[2 * sides];
        for (int i = 0; i < sides; i++) {
            float angle = i * 6.28f / sides;
            // x-coordinate
            vertices[2 * i] = sprite.getWidth() / 2 * MathUtils.cos(angle) + sprite.getWidth() / 2;
            // y-coordinate
            vertices[2 * i + 1] = sprite.getHeight() / 2 * MathUtils.sin(angle) + sprite.getHeight() / 2;
        }

        polygon = new Polygon(vertices);

        return getPolygon();
    }

    @Override
    public void reset() {
        sprite = new Sprite();
    }
}
