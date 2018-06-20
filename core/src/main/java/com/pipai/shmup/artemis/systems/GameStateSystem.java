package com.pipai.shmup.artemis.systems;

import com.artemis.BaseSystem;
import com.artemis.managers.TagManager;
import com.pipai.shmup.artemis.Tags;

public class GameStateSystem extends BaseSystem {

    private RenderingSystem sRender;
    private BulletCollisionSystem sBulletCollision;
    private ControlSystem sControl;
    private TagManager sTags;

    public int score;
    public int lives = 3;

    private boolean gameOver = false;

    @Override
    protected void processSystem() {
        if (!gameOver && lives <= 0) {
            sRender.gameEndString = "Oh no! Game Over";
            world.delete(sTags.getEntityId(Tags.PLAYER.toString()));
            sBulletCollision.setEnabled(false);
            sControl.setEnabled(false);
            gameOver = true;
        }
    }
}
