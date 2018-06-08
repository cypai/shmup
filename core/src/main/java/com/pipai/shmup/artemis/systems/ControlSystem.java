package com.pipai.shmup.artemis.systems;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.pipai.shmup.HeldKeys;
import com.pipai.shmup.ShmupGame;
import com.pipai.shmup.artemis.Tags;
import com.pipai.shmup.artemis.components.BulletComponent;
import com.pipai.shmup.artemis.components.MovementComponent;
import com.pipai.shmup.artemis.components.StaticSpriteComponent;
import com.pipai.shmup.artemis.components.XyComponent;

public class ControlSystem extends BaseSystem implements InputProcessor {

    private static int BULLET_TIME = 5;

    private ComponentMapper<XyComponent> mXy;
    private ComponentMapper<StaticSpriteComponent> mStaticSprite;
    private ComponentMapper<MovementComponent> mMovement;
    private ComponentMapper<BulletComponent> mBullet;

    private TagManager sTags;

    private ShmupGame game;

    private HeldKeys heldKeys = new HeldKeys();

    private int bulletTimer;

    private int playerSpeed = 4;

    public ControlSystem(ShmupGame game) {
        this.game = game;
    }

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

    private void createBullet() {
        XyComponent cPlayerXy = mXy.get(sTags.getEntityId(Tags.PLAYER.toString()));

        int bulletId = world.create();

        XyComponent cXy = mXy.create(bulletId);
        cXy.x = cPlayerXy.x;
        cXy.y = cPlayerXy.y;

        StaticSpriteComponent cSprite = mStaticSprite.create(bulletId);
        cSprite.sprite = new Sprite(game.getAssetManager().get("data/blue_bullet.png", Texture.class));

        BulletComponent cBullet = mBullet.create(bulletId);
        cBullet.damage = 1;
        cBullet.isPlayerBullet = true;

        MovementComponent cMovement = mMovement.create(bulletId);
        cMovement.direction = (float) Math.PI / 2;
        cMovement.speed = 10;
    }

    @Override
    public boolean keyDown(int keycode) {
        heldKeys.keyDown(keycode);
        if (keycode == Input.Keys.Z) {
            createBullet();
        }
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
