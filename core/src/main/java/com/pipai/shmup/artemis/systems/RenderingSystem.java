package com.pipai.shmup.artemis.systems;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pipai.shmup.ShmupGame;
import com.pipai.shmup.Utils;
import com.pipai.shmup.artemis.Configuration;
import com.pipai.shmup.artemis.components.StaticSpriteComponent;
import com.pipai.shmup.artemis.components.XyComponent;

import java.util.List;

public class RenderingSystem extends BaseSystem {

    private ComponentMapper<XyComponent> mXy;
    private ComponentMapper<StaticSpriteComponent> mStaticSprite;

    private GameStateSystem sGameState;

    private Configuration config;

    private SpriteBatch spr;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    public RenderingSystem(ShmupGame game) {
        config = game.getConfiguration();
        spr = game.getSpriteBatch();
        shapeRenderer = game.getShapeRenderer();
        font = game.getFont();
    }

    @Override
    protected void processSystem() {
        renderStaticSprites();
        renderUi();
    }

    private void renderStaticSprites() {
        List<Integer> entities = Utils.fetchEntities(world, Aspect.all(XyComponent.class, StaticSpriteComponent.class));

        spr.begin();
        for (Integer entity : entities) {
            XyComponent cXy = mXy.get(entity);
            StaticSpriteComponent cStaticSprite = mStaticSprite.get(entity);
            cStaticSprite.sprite.setPosition(cXy.x, cXy.y);
            cStaticSprite.sprite.draw(spr);
        }
        spr.end();
    }

    private void renderUi() {
        float uiLeft = config.getRightBound();
        float uiWidth = config.getUiWidth();
        float uiHeight = Gdx.graphics.getHeight();
        float padding = 36f;

        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(uiLeft, 0, uiWidth, uiHeight);
        shapeRenderer.end();

        float lineHeight = font.getLineHeight();
        spr.begin();
        font.draw(spr, "Score: " + sGameState.score, uiLeft + padding, uiHeight - padding);
        font.draw(spr, "Lives: " + sGameState.lives, uiLeft + padding, uiHeight - padding - lineHeight);
        spr.end();
    }
}
