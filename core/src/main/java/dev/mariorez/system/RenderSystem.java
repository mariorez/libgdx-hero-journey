package dev.mariorez.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import dev.mariorez.TransformComparator;
import dev.mariorez.component.Render;
import dev.mariorez.component.Transform;

import static dev.mariorez.Tools.renderMapper;
import static dev.mariorez.Tools.transformMapper;

public class RenderSystem extends SortedIteratingSystem {

    private final Batch batch;
    private final OrthographicCamera camera;
    private final OrthoCachedTiledMapRenderer mapRenderer;

    public RenderSystem(Batch batch,
                        OrthographicCamera camera,
                        OrthoCachedTiledMapRenderer mapRenderer) {
        super(
            Family.all(Render.class, Transform.class).get(),
            new TransformComparator());
        this.batch = batch;
        this.camera = camera;
        this.mapRenderer = mapRenderer;
    }

    @Override
    public void update(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        var sprite = renderMapper.get(entity).sprite;
        var transform = transformMapper.get(entity);

        sprite.setBounds(
            transform.position.x,
            transform.position.y,
            sprite.getWidth(),
            sprite.getHeight());

        sprite.draw(batch);
    }
}
