package com.pipai.shmup.artemis.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.pipai.shmup.ShmupGame;
import com.pipai.shmup.artemis.Configuration;
import com.pipai.shmup.artemis.components.EnemyComponent;
import com.pipai.shmup.artemis.components.EnemySimpleAiComponent;
import com.pipai.shmup.artemis.components.XyComponent;

public class EnemySimpleAiSystem extends IteratingSystem {

    private ComponentMapper<XyComponent> mXy;
    private ComponentMapper<EnemyComponent> mEnemy;
    private ComponentMapper<EnemySimpleAiComponent> mEnemySimpleAi;

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
    }

}
