package dev.mariorez;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static dev.mariorez.Sizes.WINDOW;

public abstract class BaseScreen implements Screen {

    protected SpriteBatch batch;
    protected OrthographicCamera camera;

    public BaseScreen() {
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera(WINDOW.width(), WINDOW.height());
        this.camera.setToOrtho(false);
    }

    @Override
    public void show() {
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
    }
}