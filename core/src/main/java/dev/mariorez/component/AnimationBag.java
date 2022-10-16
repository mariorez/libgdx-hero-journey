package dev.mariorez.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class AnimationBag implements Component {

    public Map<String, Animate> animations = new HashMap<>();
    public String current = "";


    public static class Animate {
        public Animation<TextureRegion> animation;
        public float stateTime = 0f;
        public boolean loop = false;
    }
}
