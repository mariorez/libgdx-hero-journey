package dev.mariorez;

import com.badlogic.ashley.core.Entity;

import java.util.Comparator;

import static dev.mariorez.Tools.transformMapper;

public class TransformComparator implements Comparator<Entity> {
    @Override
    public int compare(Entity e1, Entity e2) {

        var transform1 = transformMapper.get(e1);
        var transform2 = transformMapper.get(e2);

        return transform1.compareTo(transform2);
    }
}
