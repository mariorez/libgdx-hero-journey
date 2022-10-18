package dev.mariorez.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import dev.mariorez.component.Hero;
import dev.mariorez.component.Sword;

import static dev.mariorez.Tools.ANIMATION_HERO_EAST;
import static dev.mariorez.Tools.ANIMATION_HERO_NORTH;
import static dev.mariorez.Tools.ANIMATION_HERO_SOUTH;
import static dev.mariorez.Tools.ANIMATION_HERO_WEST;
import static dev.mariorez.Tools.animationMapper;
import static dev.mariorez.Tools.heroMapper;
import static dev.mariorez.Tools.renderMapper;
import static dev.mariorez.Tools.transformMapper;

public class SwingSwordSystem extends EntitySystem {

    private Entity hero;
    private Entity sword;

    @Override
    public void addedToEngine(Engine engine) {
        hero = engine.getEntitiesFor(Family.all(Hero.class).get()).get(0);
        sword = engine.getEntitiesFor(Family.all(Sword.class).get()).get(0);
    }

    @Override
    public void update(float deltaTime) {

        var heroComponent = heroMapper.get(this.hero);

        if (!heroComponent.swingSword) {
            renderMapper.get(sword).visible = false;
            return;
        }

        var heroSprite = renderMapper.get(hero).sprite;
        var heroTransform = transformMapper.get(this.hero);
        var heroFacing = animationMapper.get(this.hero).current;

        var swordTransform = transformMapper.get(sword);

        Vector2 offset = new Vector2();
        switch (heroFacing) {
            case ANIMATION_HERO_EAST:
                offset.set(0.50f, 0.20f);
                swordTransform.rotation = 0f;
                swordTransform.zIndex = 1f;
                break;
            case ANIMATION_HERO_NORTH:
                offset.set(0.10f, 0.90f);
                swordTransform.rotation = 90f;
                swordTransform.zIndex = 1f;
                break;
            case ANIMATION_HERO_WEST:
                offset.set(-0.50f, 0.20f);
                swordTransform.rotation = 180f;
                swordTransform.zIndex = -1f;
                break;
            case ANIMATION_HERO_SOUTH:
                offset.set(0.10f, -0.10f);
                swordTransform.rotation = 270f;
                swordTransform.zIndex = -1f;
                break;
        }

        swordTransform.position.set(
            heroTransform.position.x + (offset.x * heroSprite.getWidth()),
            heroTransform.position.y + (offset.y * heroSprite.getHeight())
        );

        renderMapper.get(sword).visible = true;
    }
}
