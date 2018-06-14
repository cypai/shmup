package com.pipai.shmup.artemis.systems;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.pipai.shmup.ShmupGame;
import com.pipai.shmup.artemis.Configuration;
import com.pipai.shmup.artemis.components.*;

import java.util.ArrayList;

public class LevelSystem extends BaseSystem {

    private ComponentMapper<XyComponent> mXy;
    private ComponentMapper<StaticSpriteComponent> mStaticSprite;
    private ComponentMapper<EnemyComponent> mEnemy;
    private ComponentMapper<CollisionBoxComponent> mCollision;
    private ComponentMapper<PathInterpolationComponent> mInterpolation;
    private ComponentMapper<EnemyShotAiComponent> mShotAi;

    private ShmupGame game;

    private int timer;

    public LevelSystem(ShmupGame game) {
        this.game = game;
    }

    @Override
    protected void processSystem() {
        timer++;

        if (timer == 60) {
            int enemyId = createRightMovingEnemy(480, 10);
            giveStandardRandom5TripleShot(enemyId);
        } else if (timer == 90) {
            int enemyId = createRightMovingEnemy(480, 10);
            giveStandardRandom5TripleShot(enemyId);
        } else if (timer == 120) {
            int enemyId = createRightMovingEnemy(480, 10);
            giveStandardRandom5TripleShot(enemyId);
        } else if (timer == 200) {
            int enemyId = createLeftMovingEnemy(500, 10);
            giveStandardRandom5TripleShot(enemyId);
        } else if (timer == 230) {
            int enemyId = createLeftMovingEnemy(500, 10);
            giveStandardRandom5TripleShot(enemyId);
        } else if (timer == 260) {
            int enemyId = createLeftMovingEnemy(500, 10);
            giveStandardRandom5TripleShot(enemyId);
        }
    }

    private void giveStandardRandom5TripleShot(int enemyId) {
        EnemyShotAiComponent cShotAi = mShotAi.create(enemyId);
        cShotAi.initialDelay = 30;
        cShotAi.loopAmount = 3;
        cShotAi.loopDelay = 60;
        cShotAi.shotTypes = new ArrayList<>();
        CircularShotType shot1 = new CircularShotType().withRandomSame(5, 4, "data/red_bullet_large.png");
        CircularShotType shot2 = new CircularShotType().withRandomSame(5, 6, "data/red_bullet_large.png");
        CircularShotType shot3 = new CircularShotType().withRandomSame(5, 8, "data/red_bullet_large.png");
        cShotAi.shotTypes.add(shot1);
        cShotAi.shotTypes.add(shot2);
        cShotAi.shotTypes.add(shot3);
    }

    private int createRightMovingEnemy(float y, int hp) {
        int enemyId = createGenericEnemy(-32, y, hp);
        XyComponent cEnemyXy = mXy.get(enemyId);
        PathInterpolationComponent cInterpolation = mInterpolation.create(enemyId);
        cInterpolation.setStart(cEnemyXy);
        cInterpolation.onEndStrategy = PathInterpolationComponent.OnEndStrategy.DESTROY;
        cInterpolation.interpolation = Interpolation.linear;
        cInterpolation.tMax = 280;
        cInterpolation.xEnd = Configuration.LEVEL_RIGHT_BOUND + 32;
        cInterpolation.yEnd = y;
        return enemyId;
    }

    private int createLeftMovingEnemy(float y, int hp) {
        int enemyId = createGenericEnemy(Configuration.LEVEL_RIGHT_BOUND + 32, y, hp);
        XyComponent cEnemyXy = mXy.get(enemyId);
        PathInterpolationComponent cInterpolation = mInterpolation.create(enemyId);
        cInterpolation.setStart(cEnemyXy);
        cInterpolation.onEndStrategy = PathInterpolationComponent.OnEndStrategy.DESTROY;
        cInterpolation.interpolation = Interpolation.linear;
        cInterpolation.tMax = 280;
        cInterpolation.xEnd = -32;
        cInterpolation.yEnd = y;
        return enemyId;
    }

    private int createGenericEnemy(float x, float y, int hp) {
        int enemyId = world.create();
        XyComponent cEnemyXy = mXy.create(enemyId);
        cEnemyXy.x = x;
        cEnemyXy.y = y;
        StaticSpriteComponent cEnemySprite = mStaticSprite.create(enemyId);
        cEnemySprite.sprite = new Sprite(game.getAssetManager().get("data/enemy.png", Texture.class));
        EnemyComponent cEnemy = mEnemy.create(enemyId);
        cEnemy.hp = hp;
        CollisionBoxComponent cCollision = mCollision.create(enemyId);
        cCollision.set(16f, 20f, 16f, 16f);
        return enemyId;
    }
}

