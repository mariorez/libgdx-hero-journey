package dev.mariorez;

import com.badlogic.ashley.core.Entity;
import dev.mariorez.component.Transform;

import java.util.Comparator;

public class TransformComparator implements Comparator<Entity> {
    @Override
    public int compare(Entity e1, Entity e2) {

        var transform1 = e1.getComponent(Transform.class);
        var transform2 = e2.getComponent(Transform.class);

        return transform1.compareTo(transform2);
    }
}
