package com.pipai.shmup.artemis.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.pipai.shmup.ShmupGame;
import com.pipai.shmup.Utils;
import com.pipai.shmup.artemis.Configuration;
import com.pipai.shmup.artemis.components.*;

public class EnemySimpleAiSystem extends IteratingSystem {

    private ComponentMapper<XyComponent> mXy;
    private ComponentMapper<EnemyComponent> mEnemy;
    private ComponentMapper<EnemySimpleAiComponent> mEnemySimpleAi;
    private ComponentMapper<StaticSpriteComponent> mStaticSprite;
    private ComponentMapper<MovementComponent> mMovement;
    private ComponentMapper<BulletComponent> mBullet;
    private ComponentMapper<OutOfScreenDestroyComponent> mOutOfScreenDestroy;
    private ComponentMapper<CollisionBoxComponent> mCollision;

    private ShmupGame game;

    public EnemySimpleAiSystem(ShmupGame game) {
        super(Aspect.all(EnemyComponent.class, XyComponent.class, EnemySimpleAiComponent.class));
        this.game = game;
    }

    @Override
    protected void process(int entityId) {
        XyComponent cXy = mXy.get(entityId);
        EnemyComponent cEnemy = mEnemy.get(entityId);
        EnemySimpleAiComponent cAi = mEnemySimpleAi.get(entityId);
        if (cAi.left) {
            if (cXy.x < 120) {
                cAi.left = false;
            } else {
                cXy.x -= 3;
            }
        } else {
            if (cXy.x > Configuration.LEVEL_RIGHT_BOUND - 120) {
                cAi.left = true;
            } else {
                cXy.x += 3;
            }
        }
        cAi.bulletTimer -= 1;
        if (cAi.bulletTimer <= 0) {
            cAi.bulletTimer = cAi.bulletDelay;
            for (int i = 0; i < 20; i += 1) {
                createBullet(cXy.x, cXy.y, Utils.RNG.nextFloat() * 2 * (float) Math.PI, 5f);
            }
        }
    }

    private void createBullet(float x, float y, float direction, float speed) {
        int bulletId = world.create();

        XyComponent cXy = mXy.create(bulletId);
        cXy.x = x;
        cXy.y = y;

        StaticSpriteComponent cSprite = mStaticSprite.create(bulletId);
        cSprite.sprite = new Sprite(game.getAssetManager().get("data/red_bullet.png", Texture.class));

        BulletComponent cBullet = mBullet.create(bulletId);
        cBullet.isPlayerBullet = false;

        MovementComponent cMovement = mMovement.create(bulletId);
        cMovement.direction = direction;
        cMovement.speed = speed;

        CollisionBoxComponent cCollision = mCollision.create(bulletId);
        cCollision.set(0f, 0f, cSprite.sprite.getWidth(), cSprite.sprite.getHeight());

        mOutOfScreenDestroy.create(bulletId);
    }

}
