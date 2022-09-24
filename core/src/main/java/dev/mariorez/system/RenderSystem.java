package dev.mariorez.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import dev.mariorez.component.RenderComponent;
import dev.mariorez.component.TransformComponent;

public class RenderSystem extends IteratingSystem {

    private Batch batch;
    private Camera camera;

    public RenderSystem(Batch batch, Camera camera) {
        super(Family.all(RenderComponent.class, TransformComponent.class).get());
        this.batch = batch;
        this.camera = camera;
    }

    @Override
    public void update(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        var sprite = RenderComponent.mapper.get(entity).sprite;
        var transform = TransformComponent.mapper.get(entity);

        sprite.setBounds(
                transform.position.x,
                transform.position.y,
                sprite.getWidth(),
                sprite.getHeight()
        );

        sprite.draw(batch);
    }
}
