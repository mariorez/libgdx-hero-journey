package dev.mariorez.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import dev.mariorez.component.Hero;
import dev.mariorez.component.Sword;
import dev.mariorez.component.Transform;

import static dev.mariorez.Tools.ANIMATION_HERO_EAST;
import static dev.mariorez.Tools.ANIMATION_HERO_NORTH;
import static dev.mariorez.Tools.ANIMATION_HERO_SOUTH;
import static dev.mariorez.Tools.ANIMATION_HERO_WEST;
import static dev.mariorez.Tools.animationMapper;
import static dev.mariorez.Tools.heroMapper;
import static dev.mariorez.Tools.renderMapper;
import static dev.mariorez.Tools.transformMapper;
import static dev.mariorez.component.Sword.Direction.CLOCKWISE;
import static dev.mariorez.component.Sword.Direction.COUNTERCLOCKWISE;

public class SwordAttackSystem extends EntitySystem {

    private final RenderSystem renderSystem;
    private Entity hero;
    private Entity sword;
    private Vector2 offset = new Vector2();

    public SwordAttackSystem(RenderSystem renderSystem) {
        this.renderSystem = renderSystem;
    }

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

        var swordComponent = sword.getComponent(Sword.class);
        var swordSprite = renderMapper.get(sword).sprite;
        swordSprite.setOrigin(0, swordSprite.getOriginY());
        var swordTransform = transformMapper.get(sword);
        var newRotation = 0f;

        switch (heroFacing) {
            case ANIMATION_HERO_EAST:
                newRotation = 0f - (swordComponent.attackArc / 2);
                if (newRotation != swordComponent.startRotation) {
                    offset.set(0.55f, 0.30f);
                    swordComponent.startRotation = newRotation;
                    swordComponent.currentRotation = newRotation;
                    exchange(heroTransform, swordTransform);
                }
                break;
            case ANIMATION_HERO_NORTH:
                newRotation = 90f - (swordComponent.attackArc / 2);
                if (newRotation != swordComponent.startRotation) {
                    offset.set(0.70f, 0.50f);
                    swordComponent.startRotation = newRotation;
                    swordComponent.currentRotation = newRotation;
                    exchange(heroTransform, swordTransform);
                }
                break;
            case ANIMATION_HERO_WEST:
                newRotation = 180f - (swordComponent.attackArc / 2);
                if (newRotation != swordComponent.startRotation) {
                    offset.set(0.25f, 0.30f);
                    swordComponent.startRotation = newRotation;
                    swordComponent.currentRotation = newRotation;
                    exchange(swordTransform, heroTransform);
                }
                break;
            case ANIMATION_HERO_SOUTH:
                newRotation = 270f - (swordComponent.attackArc / 2);
                if (newRotation != swordComponent.startRotation) {
                    offset.set(0.30f, 0.25f);
                    swordComponent.startRotation = newRotation;
                    swordComponent.currentRotation = newRotation;
                    exchange(swordTransform, heroTransform);
                }
                break;
        }

        if (swordComponent.reachedTheEnd()) {
            swordComponent.direction = COUNTERCLOCKWISE;
        }

        if (swordComponent.cameBackToTheStart()) {
            swordComponent.direction = CLOCKWISE;
        }

        swordTransform.rotation = swordComponent.rotate(swordComponent.direction);

        swordTransform.position.set(
            heroTransform.position.x + (offset.x * heroSprite.getWidth()),
            heroTransform.position.y + (offset.y * heroSprite.getHeight())
        );

        renderMapper.get(sword).visible = true;
    }

    private void exchange(Transform toFront, Transform toBack) {
        if (toFront.zIndex >= toBack.zIndex) {
            toFront.zIndex = toBack.zIndex - 0.1f;
            renderSystem.forceSort();
        }
    }
}
