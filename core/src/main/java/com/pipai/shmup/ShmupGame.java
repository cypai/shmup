package com.pipai.shmup;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pipai.shmup.artemis.Configuration;
import com.pipai.shmup.artemis.screens.MainLevelScreen;

public class ShmupGame extends Game {

    private Configuration configuration;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private AssetManager assetManager;

    @Override
    public void create() {
        configuration = new Configuration();
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        assetManager = new AssetManager();
        loadSprites();
        setScreen(new MainLevelScreen(this));
    }

    private void loadSprites() {
        assetManager.load("data/ship.png", Texture.class);
        assetManager.load("data/enemy.png", Texture.class);
        assetManager.load("data/red_bullet.png", Texture.class);
        assetManager.load("data/blue_bullet.png", Texture.class);
        assetManager.finishLoading();
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

    public Configuration getConfiguration() {
        return configuration;
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

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
