package com.pipai.shmup.artemis.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.managers.TagManager;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.pipai.shmup.ShmupGame;
import com.pipai.shmup.Utils;
import com.pipai.shmup.artemis.components.*;

public class EnemyShotAiSystem extends IteratingSystem {

    private ComponentMapper<XyComponent> mXy;
    private ComponentMapper<EnemyShotAiComponent> mShotAi;
    private ComponentMapper<StaticSpriteComponent> mStaticSprite;
    private ComponentMapper<MovementComponent> mMovement;
    private ComponentMapper<BulletComponent> mBullet;
    private ComponentMapper<OutOfScreenDestroyComponent> mOutOfScreenDestroy;
    private ComponentMapper<CollisionBoxComponent> mCollision;

    private TagManager sTags;

    private ShmupGame game;

    public EnemyShotAiSystem(ShmupGame game) {
        super(Aspect.all(XyComponent.class, EnemyShotAiComponent.class));
        this.game = game;
    }

    @Override
    protected void process(int entityId) {
        EnemyShotAiComponent cShotAi = mShotAi.get(entityId);

        if (cShotAi.initialDelay > 0) {
            cShotAi.initialDelay--;
            return;
        }

        cShotAi.loopTimer++;
        if (cShotAi.loopTimer >= cShotAi.loopDelay) {
            cShotAi.loopTimer = 0;
            cShotAi.loopAmount--;
            if (cShotAi.loopAmount <= 0) {
                mShotAi.remove(entityId);
            }
            XyComponent cXy = mXy.get(entityId);

            float randomSameDirection = Utils.RNG.nextFloat() * 2 * (float) Math.PI;
            for (CircularShotType shotType : cShotAi.shotTypes) {
                switch (shotType.aimType) {
                    case RANDOM: {
                        float primaryDirection = Utils.RNG.nextFloat() * 2 * (float) Math.PI;
                        float directionDelta = shotType.spreadAngle / shotType.amount;
                        for (int i = 0; i < shotType.amount; i++) {
                            createBullet(cXy.x, cXy.y, primaryDirection + i * directionDelta, shotType.speed, shotType.spriteFilename);
                        }
                        break;
                    }
                    case RANDOM_SAME:
                        float directionDelta = shotType.spreadAngle / shotType.amount;
                        for (int i = 0; i < shotType.amount; i++) {
                            createBullet(cXy.x, cXy.y, randomSameDirection + i * directionDelta, shotType.speed, shotType.spriteFilename);
                        }
                    default:
                        break;
                }
            }
        }
    }

    private void createBullet(float x, float y, float direction, float speed, String spriteFilename) {
        int bulletId = world.create();

        XyComponent cXy = mXy.create(bulletId);
        cXy.x = x;
        cXy.y = y;

        StaticSpriteComponent cSprite = mStaticSprite.create(bulletId);
        cSprite.sprite = new Sprite(game.getAssetManager().get(spriteFilename, Texture.class));

        BulletComponent cBullet = mBullet.create(bulletId);
        cBullet.isPlayerBullet = false;

        MovementComponent cMovement = mMovement.create(bulletId);
        cMovement.direction = direction;
        cMovement.speed = speed;

        CollisionBoxComponent cCollision = mCollision.create(bulletId);
        cCollision.set(12f, 12f, cSprite.sprite.getWidth() - 24f, cSprite.sprite.getHeight() - 24f);

        mOutOfScreenDestroy.create(bulletId);
    }

}
