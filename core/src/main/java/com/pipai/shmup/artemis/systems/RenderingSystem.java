package com.pipai.shmup.artemis.systems;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pipai.shmup.ShmupGame;
import com.pipai.shmup.Utils;
import com.pipai.shmup.artemis.components.StaticSpriteComponent;
import com.pipai.shmup.artemis.components.XyComponent;

import java.util.List;

public class RenderingSystem extends BaseSystem {

    private ComponentMapper<XyComponent> mXy;
    private ComponentMapper<StaticSpriteComponent> mStaticSprite;

    private SpriteBatch spr;

    public RenderingSystem(ShmupGame game) {
        spr = game.getSpriteBatch();
    }

    @Override
    protected void processSystem() {
        renderStaticSprites();
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
}
