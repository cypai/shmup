package com.pipai.shmup.artemis.systems.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.pipai.shmup.ShmupGame;
import com.pipai.shmup.artemis.screens.MainLevelScreen;

public class ExitInputProcessor implements InputProcessor {

    private ShmupGame game;

    public ExitInputProcessor(ShmupGame game) {
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.ESCAPE) {
            Gdx.app.exit();
        } else if (keycode == Keys.R) {
            Screen currentScreen = game.getScreen();
            game.setScreen(new MainLevelScreen(game));
            currentScreen.dispose();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
