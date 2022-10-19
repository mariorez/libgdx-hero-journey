package dev.mariorez.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Polygon;
import dev.mariorez.component.Flyer;
import dev.mariorez.component.Hero;
import dev.mariorez.component.InvisibleSolid;
import dev.mariorez.component.Npc;
import dev.mariorez.component.Render;
import dev.mariorez.component.Transform;
import dev.mariorez.component.VisibleSolid;

import static com.badlogic.ashley.core.Family.one;

public class CollisionSystem extends EntitySystem {

    private final MinimumTranslationVector mtv = new MinimumTranslationVector();
    private ImmutableArray<Entity> solidEntities;
    private ImmutableArray<Entity> movingEntities;

    @Override
    public void addedToEngine(Engine engine) {
        solidEntities = engine.getEntitiesFor(one(VisibleSolid.class, InvisibleSolid.class, Npc.class).get());
        movingEntities = engine.getEntitiesFor(one(Hero.class, Flyer.class).get());
    }

    @Override
    public void update(float deltaTime) {
        for (Entity solid : solidEntities) {
            var solidBox = solid.getComponent(Render.class).getPolygon();
            for (Entity moving : movingEntities) {
                var movingBox = moving.getComponent(Render.class).getPolygon(8);
                if (overlaps(movingBox, solidBox, mtv)) {
                    moving.getComponent(Transform.class).position.x += mtv.normal.x * mtv.depth;
                    moving.getComponent(Transform.class).position.y += mtv.normal.y * mtv.depth;
                }
            }
        }
    }

    private boolean overlaps(
        Polygon currentBox,
        Polygon otherBox,
        MinimumTranslationVector mtv) {

        if (currentBox.getBoundingRectangle().overlaps(otherBox.getBoundingRectangle())) {
            return Intersector.overlapConvexPolygons(currentBox, otherBox, mtv);
        }

        return false;
    }
}
