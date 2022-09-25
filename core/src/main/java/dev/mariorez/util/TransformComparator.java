package dev.mariorez.util;

import com.badlogic.ashley.core.Entity;

import java.util.Comparator;

public class TransformComparator implements Comparator<Entity> {
    @Override
    public int compare(Entity e1, Entity e2) {

        var transform1 = Mappers.transform.get(e1);
        var transform2 = Mappers.transform.get(e2);

        return transform1.compareTo(transform2);
    }
}
