package com.pipai.shmup;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pipai.shmup.artemis.screens.MainLevelScreen;

public class ShmupGame extends Game {

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        setScreen(new MainLevelScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public BitmapFont getFont() {
        return font;
    }

}
