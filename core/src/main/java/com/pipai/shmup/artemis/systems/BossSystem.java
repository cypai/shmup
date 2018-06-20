package com.pipai.shmup.artemis.systems;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.managers.TagManager;
import com.pipai.shmup.ShmupGame;
import com.pipai.shmup.Utils;
import com.pipai.shmup.artemis.Tags;
import com.pipai.shmup.artemis.components.*;

import java.util.ArrayList;

public class BossSystem extends BaseSystem {

    private ComponentMapper<XyComponent> mXy;
    private ComponentMapper<StaticSpriteComponent> mStaticSprite;
    private ComponentMapper<EnemyComponent> mEnemy;
    private ComponentMapper<CollisionBoxComponent> mCollision;
    private ComponentMapper<PathInterpolationComponent> mInterpolation;
    private ComponentMapper<EnemyShotAiComponent> mShotAi;

    private TagManager sTags;

    private ShmupGame game;

    private int timer;
    private int pattern;
    private final float PI = (float) Math.PI;

    private float patternDirection;

    public BossSystem(ShmupGame game) {
        this.game = game;
    }

    @Override
    protected void initialize() {
        super.initialize();
        this.setEnabled(false);
    }

    @Override
    protected void processSystem() {
        int bossId = sTags.getEntityId(Tags.BOSS.toString());
        int hp = mEnemy.get(bossId).hp;

        if (hp > 400) {
            timer++;
            if (timer < 120 && timer % 15 == 0) {
                giveStandardDirectionShot(bossId, 6, 6, timer / 5, "data/blue_bullet_large.png");
            } else if (timer >= 120 && timer < 140 && timer % 10 == 0) {
                giveAimedTripleShot(bossId, 3, PI / 4, "data/red_bullet_large.png");
            } else if (timer >= 140 && timer < 260 && timer % 15 == 0) {
                giveStandardDirectionShot(bossId, 6, 6, -timer / 5, "data/blue_bullet_large.png");
            } else if (timer >= 260 && timer < 280 && timer % 10 == 0) {
                giveAimedTripleShot(bossId, 4, PI / 3, "data/yellow_bullet_large.png");
            } else if (timer > 280) {
                timer = 0;
            }
        } else if (hp > 200) {
            if (pattern == 0) {
                pattern = 1;
                timer = 0;
            }
            timer++;
            if (timer % 10 == 0) {
                patternDirection += (float) Math.sin((float) timer / 600 * 2 * PI) / 5;
                giveStandardDirectionShot(bossId, 7, 7, patternDirection, "data/purple_bullet_large.png");
            }
        } else {
            if (pattern == 1) {
                pattern = 2;
                timer = 0;
            }
            timer++;
            if (timer < 60 && timer % 10 == 0) {
                giveAimedTripleShot(bossId, 5, PI * 2 / 3, "data/red_bullet_large.png");
            } else if (timer >= 80 && timer < 170 && timer % 30 == 0) {
                if (timer == 90) {
                    patternDirection = Utils.RNG.nextFloat();
                }
                giveManyShot(bossId, 8, 6, patternDirection + (PI / 12) * (timer - 90) / 3, "data/green_bullet_large.png");
            } else if (timer >= 200 && timer < 260 && timer % 10 == 0) {
                giveAimedTripleShot(bossId, 4, PI / 2, "data/yellow_bullet_large.png");
            } else if (timer >= 280 && timer < 370 && timer % 30 == 0) {
                patternDirection = Utils.RNG.nextFloat();
                if (timer % 60 == 0) {
                    giveManyShotCurved(bossId, 8, 6, patternDirection, "data/blue_bullet_large.png");
                } else {
                    giveManyShotInverseCurved(bossId, 8, 6, patternDirection, "data/purple_bullet_large.png");
                }
            } else if (timer > 400) {
                timer = 0;
            }
        }
    }

    private void giveStandardDirectionShot(int enemyId, int amount, int speed, float direction, String sprite) {
        EnemyShotAiComponent cShotAi = mShotAi.create(enemyId);
        cShotAi.loopAmount = 1;
        cShotAi.shotTypes = new ArrayList<>();
        CircularShotType shot = new CircularShotType().withDirection(amount, speed, direction, sprite);
        cShotAi.shotTypes.add(shot);
    }

    private void giveAimedTripleShot(int enemyId, int amount, float spreadAngle, String sprite) {
        EnemyShotAiComponent cShotAi = mShotAi.create(enemyId);
        cShotAi.loopAmount = 1;
        cShotAi.shotTypes = new ArrayList<>();
        CircularShotType shot1 = new CircularShotType().withAimed(amount, 6, sprite);
        shot1.spreadAngle = spreadAngle;
        CircularShotType shot2 = new CircularShotType().withAimed(amount, 8, sprite);
        shot2.spreadAngle = spreadAngle;
        CircularShotType shot3 = new CircularShotType().withAimed(amount, 10, sprite);
        shot3.spreadAngle = spreadAngle;
        cShotAi.shotTypes.add(shot1);
        cShotAi.shotTypes.add(shot2);
        cShotAi.shotTypes.add(shot3);
    }

    private void giveManyShot(int enemyId, int amount, int lineAmount, float direction, String sprite) {
        EnemyShotAiComponent cShotAi = mShotAi.create(enemyId);
        cShotAi.loopAmount = 1;
        cShotAi.shotTypes = new ArrayList<>();
        for (int i = 0; i < lineAmount; i++) {
            CircularShotType shot = new CircularShotType().withDirection(amount, (i + 1), direction, sprite);
            cShotAi.shotTypes.add(shot);
        }
    }

    private void giveManyShotCurved(int enemyId, int amount, int lineAmount, float direction, String sprite) {
        EnemyShotAiComponent cShotAi = mShotAi.create(enemyId);
        cShotAi.loopAmount = 1;
        cShotAi.shotTypes = new ArrayList<>();
        for (int i = 0; i < lineAmount; i++) {
            CircularShotType shot = new CircularShotType().withDirection(amount, (i + 1), direction + i * PI / 20, sprite);
            cShotAi.shotTypes.add(shot);
        }
    }
    private void giveManyShotInverseCurved(int enemyId, int amount, int lineAmount, float direction, String sprite) {
        EnemyShotAiComponent cShotAi = mShotAi.create(enemyId);
        cShotAi.loopAmount = 1;
        cShotAi.shotTypes = new ArrayList<>();
        for (int i = 0; i < lineAmount; i++) {
            CircularShotType shot = new CircularShotType().withDirection(amount, (i + 1), direction - i * PI / 20, sprite);
            cShotAi.shotTypes.add(shot);
        }
    }
}
