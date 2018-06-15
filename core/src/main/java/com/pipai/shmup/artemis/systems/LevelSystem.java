package com.pipai.shmup.artemis.systems;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.pipai.shmup.ShmupGame;
import com.pipai.shmup.artemis.Configuration;
import com.pipai.shmup.artemis.components.*;

import java.util.ArrayList;
import java.util.function.Consumer;

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
            giveStandardRandomTripleShot(enemyId, 5);
        } else if (timer == 90) {
            int enemyId = createRightMovingEnemy(480, 10);
            giveStandardRandomTripleShot(enemyId, 5);
        } else if (timer == 120) {
            int enemyId = createRightMovingEnemy(480, 10);
            giveStandardRandomTripleShot(enemyId, 5);
        } else if (timer == 200) {
            int enemyId = createLeftMovingEnemy(500, 10);
            giveStandardRandomTripleShot(enemyId, 5);
        } else if (timer == 230) {
            int enemyId = createLeftMovingEnemy(500, 10);
            giveStandardRandomTripleShot(enemyId, 5);
        } else if (timer == 260) {
            int enemyId = createLeftMovingEnemy(500, 10);
            giveStandardRandomTripleShot(enemyId, 5);
        } else if (timer == 360) {
            int enemyId1 = createTopJumpEnemy(200, 520, 14, standardExit());
            giveStandardAimedTripleShot(enemyId1, 3, 6, 60);
            int enemyId2 = createTopJumpEnemy(240, 520, 14, standardExit());
            giveStandardAimedTripleShot(enemyId2, 3, 6, 60);
            int enemyId3 = createTopJumpEnemy(280, 520, 14, standardExit());
            giveStandardAimedTripleShot(enemyId3, 3, 6, 60);
        }
    }

    private Consumer<PathInterpolationComponent> standardExit() {
        return it -> {
            it.onEndStrategy = PathInterpolationComponent.OnEndStrategy.DESTROY;
            it.delay = 360;
            it.t = 0;
            it.tMax = 60;
            it.xStart = it.xEnd;
            it.yStart = it.yEnd;
            it.yEnd = Gdx.graphics.getHeight() + 32;
            it.interpolation = Interpolation.sineIn;
            it.onEnd = null;
        };
    }

    private void giveStandardRandomTripleShot(int enemyId, int amount) {
        EnemyShotAiComponent cShotAi = mShotAi.create(enemyId);
        cShotAi.initialDelay = 30;
        cShotAi.loopAmount = 3;
        cShotAi.loopDelay = 60;
        cShotAi.shotTypes = new ArrayList<>();
        CircularShotType shot1 = new CircularShotType().withRandomSame(amount, 4, "data/red_bullet_large.png");
        CircularShotType shot2 = new CircularShotType().withRandomSame(amount, 6, "data/red_bullet_large.png");
        CircularShotType shot3 = new CircularShotType().withRandomSame(amount, 8, "data/red_bullet_large.png");
        cShotAi.shotTypes.add(shot1);
        cShotAi.shotTypes.add(shot2);
        cShotAi.shotTypes.add(shot3);
    }

    private void giveStandardAimedTripleShot(int enemyId, int amount, int loopAmount, int loopDelay) {
        EnemyShotAiComponent cShotAi = mShotAi.create(enemyId);
        cShotAi.initialDelay = 30;
        cShotAi.loopAmount = loopAmount;
        cShotAi.loopDelay = loopDelay;
        cShotAi.shotTypes = new ArrayList<>();
        CircularShotType shot1 = new CircularShotType().withAimed(amount, 4, "data/blue_bullet_large.png");
        CircularShotType shot2 = new CircularShotType().withAimed(amount, 6, "data/blue_bullet_large.png");
        CircularShotType shot3 = new CircularShotType().withAimed(amount, 8, "data/blue_bullet_large.png");
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

    private int createTopJumpEnemy(float x, float endY, int hp, Consumer<PathInterpolationComponent> onEnd) {
        int enemyId = createGenericEnemy(x, Gdx.graphics.getHeight() + 32, hp);
        XyComponent cEnemyXy = mXy.get(enemyId);
        PathInterpolationComponent cInterpolation = mInterpolation.create(enemyId);
        cInterpolation.setStart(cEnemyXy);
        cInterpolation.interpolation = Interpolation.pow2;
        cInterpolation.tMax = 60;
        cInterpolation.xEnd = x;
        cInterpolation.yEnd = endY;
        cInterpolation.onEnd = onEnd;
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

