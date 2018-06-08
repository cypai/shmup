package com.pipai.shmup.artemis.systems;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.pipai.shmup.HeldKeys;
import com.pipai.shmup.artemis.Tags;
import com.pipai.shmup.artemis.components.StaticSpriteComponent;
import com.pipai.shmup.artemis.components.XyComponent;

public class ControlSystem extends BaseSystem implements InputProcessor {

    private static int BULLET_TIME = 5;

    private ComponentMapper<XyComponent> mXy;
    private ComponentMapper<StaticSpriteComponent> mStaticSprite;

    private TagManager sTags;

    private HeldKeys heldKeys = new HeldKeys();

    private int bulletTimer;

    private int playerSpeed = 4;

    @Override
    protected void processSystem() {
        XyComponent cPlayerXy = mXy.get(sTags.getEntityId(Tags.PLAYER.toString()));

        if (heldKeys.isDown(Input.Keys.RIGHT)) {
            cPlayerXy.x += playerSpeed;
        }
        if (heldKeys.isDown(Input.Keys.LEFT)) {
            System.out.println("You are holding LEFT!");
        }
        if (heldKeys.isDown(Input.Keys.UP)) {
        }
        if (heldKeys.isDown(Input.Keys.DOWN)) {
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        heldKeys.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        heldKeys.keyUp(keycode);
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
