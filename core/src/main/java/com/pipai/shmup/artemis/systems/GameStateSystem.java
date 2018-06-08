package com.pipai.shmup.artemis.systems;

import com.artemis.BaseSystem;

public class GameStateSystem extends BaseSystem {

    public int score;
    public int lives = 3;

    @Override
    protected void processSystem() {
        if (lives == 0) {
            System.out.println("Oh no! Game over. Your final score was: " + score);
        }
    }
}
