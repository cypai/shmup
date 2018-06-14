package com.pipai.shmup.artemis.screens.initializers;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.pipai.shmup.ShmupGame;
import com.pipai.shmup.artemis.Tags;
import com.pipai.shmup.artemis.components.*;

public class MainLevelScreenInitializer {

    private ShmupGame game;
    private World world;

    private ComponentMapper<XyComponent> mXy;
    private ComponentMapper<StaticSpriteComponent> mStaticSprite;
    private ComponentMapper<EnemyComponent> mEnemy;
    private ComponentMapper<CollisionBoxComponent> mCollision;
    private ComponentMapper<EnemySimpleAiComponent> mEnemySimpleAi;

    private TagManager sTags;

    public MainLevelScreenInitializer(ShmupGame game, World world) {
        this.game = game;
        this.world = world;
        world.inject(this);
    }

    public void initialize() {
        createPlayer();
    }

    private void createPlayer() {
        int playerId = world.create();
        XyComponent cPlayerXy = mXy.create(playerId);
        cPlayerXy.x = Gdx.graphics.getWidth() / 3;
        StaticSpriteComponent cPlayerSprite = mStaticSprite.create(playerId);
        cPlayerSprite.sprite = new Sprite(game.getAssetManager().get("data/ship.png", Texture.class));
        CollisionBoxComponent cCollision = mCollision.create(playerId);
        cCollision.set(16f, 12f, 16f, 16f);

        sTags.register(Tags.PLAYER.toString(), playerId);
    }

}
