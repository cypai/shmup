package com.pipai.shmup.artemis.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.managers.TagManager;
import com.artemis.systems.IteratingSystem;
import com.pipai.shmup.Utils;
import com.pipai.shmup.artemis.Tags;
import com.pipai.shmup.artemis.components.BulletComponent;
import com.pipai.shmup.artemis.components.CollisionBoxComponent;
import com.pipai.shmup.artemis.components.EnemyComponent;
import com.pipai.shmup.artemis.components.XyComponent;

import java.util.List;

public class BulletCollisionSystem extends IteratingSystem {

    private ComponentMapper<BulletComponent> mBullet;
    private ComponentMapper<XyComponent> mXy;
    private ComponentMapper<CollisionBoxComponent> mCollision;
    private ComponentMapper<EnemyComponent> mEnemy;

    private TagManager sTags;
    private GameStateSystem sGameState;
    private RenderingSystem sRender;

    public BulletCollisionSystem() {
        super(Aspect.all(BulletComponent.class, XyComponent.class, CollisionBoxComponent.class));
    }

    @Override
    protected void process(int entityId) {
        BulletComponent cBullet = mBullet.get(entityId);
        XyComponent cXy = mXy.get(entityId);
        CollisionBoxComponent cCollision = mCollision.get(entityId);

        if (cBullet.isPlayerBullet) {
            List<Integer> enemyIds = Utils.fetchEntities(world, Aspect.all(EnemyComponent.class, XyComponent.class, CollisionBoxComponent.class));
            for (int enemyId : enemyIds) {
                EnemyComponent cEnemy = mEnemy.get(enemyId);
                XyComponent cEnemyXy = mXy.get(enemyId);
                CollisionBoxComponent cEnemyCollision = mCollision.get(enemyId);
                if (Utils.collides(cXy, cCollision, cEnemyXy, cEnemyCollision)) {
                    world.delete(entityId);
                    sGameState.score += 10;
                    cEnemy.hp -= cBullet.damage;
                    if (cEnemy.hp <= 0) {
                        world.delete(enemyId);
                        sGameState.score += 100;
                    }
                }
            }
        } else {
            int playerId = sTags.getEntityId(Tags.PLAYER.toString());
            XyComponent cPlayerXy = mXy.get(playerId);
            CollisionBoxComponent cPlayerCollision = mCollision.get(playerId);
            if (Utils.collides(cXy, cCollision, cPlayerXy, cPlayerCollision)) {
                System.out.println("Player was hit!");
                world.delete(entityId);
            }
        }
    }

}
