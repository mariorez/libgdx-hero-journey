package dev.mariorez.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Polygon;
import dev.mariorez.component.Solid;

import static dev.mariorez.Tools.renderMapper;
import static dev.mariorez.Tools.transformMapper;

public class CollisionSystem extends IteratingSystem {

    private final Entity player;

    public CollisionSystem(Entity player) {
        super(Family.all(Solid.class).get());
        this.player = player;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        var playerBox = renderMapper.get(player).getPolygon(8);
        var solidBox = renderMapper.get(entity).getPolygon();

        var mtv = new MinimumTranslationVector();
        if (!overlaps(playerBox, solidBox, mtv)) return;

        transformMapper.get(player).position.x += mtv.normal.x * mtv.depth;
        transformMapper.get(player).position.y += mtv.normal.y * mtv.depth;
    }

    private boolean overlaps(
        Polygon playerBox,
        Polygon otherBox,
        MinimumTranslationVector mtv) {

        if (playerBox.getBoundingRectangle().overlaps(otherBox.getBoundingRectangle())) {
            return Intersector.overlapConvexPolygons(playerBox, otherBox, mtv);
        }

        return false;
    }
}
