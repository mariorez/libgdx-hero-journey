package dev.mariorez;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.F11;

public abstract class BaseScreen implements Screen {

    protected final SpriteBatch batch;
    protected final OrthographicCamera camera;
    private final Sizes sizes;
    protected boolean isFullscreen = true;
    protected Map<Integer, Action> actionMap = new HashMap<>();

    public BaseScreen(Sizes sizes) {
        this.sizes = sizes;
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera(sizes.windowWidth, sizes.windowHeight);
        this.camera.setToOrtho(false);

        actionMap.put(F11, Action.FULLSCREEN);
    }

    public void doAction(Action action) {
        if (action.starting) {
            switch (action) {
                case FULLSCREEN:
                    if (isFullscreen) {
                        isFullscreen = false;
                        Gdx.graphics.setWindowedMode((int) sizes.windowWidth, (int) sizes.windowHeight);
                    } else {
                        isFullscreen = true;
                        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                    }
                    break;
            }
        }
    }

    @Override
    public void show() {
        isFullscreen = true;
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
    }

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}