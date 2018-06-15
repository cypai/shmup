package com.pipai.shmup.artemis.systems;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.pipai.shmup.HeldKeys;
import com.pipai.shmup.ShmupGame;
import com.pipai.shmup.artemis.Configuration;
import com.pipai.shmup.artemis.Tags;
import com.pipai.shmup.artemis.components.*;

public class ControlSystem extends BaseSystem implements InputProcessor {

    private ComponentMapper<XyComponent> mXy;
    private ComponentMapper<StaticSpriteComponent> mStaticSprite;
    private ComponentMapper<MovementComponent> mMovement;
    private ComponentMapper<BulletComponent> mBullet;
    private ComponentMapper<OutOfScreenDestroyComponent> mOutOfScreenDestroy;
    private ComponentMapper<CollisionBoxComponent> mCollision;

    private TagManager sTags;
    private RenderingSystem sRender;

    private ShmupGame game;

    private HeldKeys heldKeys = new HeldKeys();

    private int playerSpeed = 4;
    private int bulletTimer = 0;
    private int bulletDelay = 8;

    public ControlSystem(ShmupGame game) {
        this.game = game;
    }

    @Override
    protected void processSystem() {
        int playerId = sTags.getEntityId(Tags.PLAYER.toString());
        XyComponent cPlayerXy = mXy.get(playerId);
        StaticSpriteComponent cPlayerSprite = mStaticSprite.get(playerId);

        if (heldKeys.isDown(Input.Keys.RIGHT) && cPlayerXy.x < Configuration.LEVEL_RIGHT_BOUND - cPlayerSprite.sprite.getWidth()) {
            cPlayerXy.x += playerSpeed;
        }
        if (heldKeys.isDown(Input.Keys.LEFT) && cPlayerXy.x > 0) {
            cPlayerXy.x -= playerSpeed;
        }
        if (heldKeys.isDown(Input.Keys.UP) && cPlayerXy.y < Gdx.graphics.getHeight() - cPlayerSprite.sprite.getHeight()) {
            cPlayerXy.y += playerSpeed;
        }
        if (heldKeys.isDown(Input.Keys.DOWN) && cPlayerXy.y > 0) {
            cPlayerXy.y -= playerSpeed;
        }
        if (heldKeys.isDown(Input.Keys.Z) && bulletTimer <= 0) {
            createBullet(cPlayerXy.x + 4f, cPlayerXy.y, (float) Math.PI / 2, 3);
            createBullet(cPlayerXy.x + 18f, cPlayerXy.y, (float) Math.PI / 2, 3);
            bulletTimer = bulletDelay;
        }
        if (bulletTimer > 0) {
            bulletTimer -= 1;
        }
    }

    private void createBullet(float x, float y, float direction, int damage) {
        int bulletId = world.create();

        XyComponent cXy = mXy.create(bulletId);
        cXy.x = x;
        cXy.y = y;

        StaticSpriteComponent cSprite = mStaticSprite.create(bulletId);
        cSprite.sprite = new Sprite(game.getAssetManager().get("data/blue_bullet_player.png", Texture.class));

        BulletComponent cBullet = mBullet.create(bulletId);
        cBullet.damage = damage;
        cBullet.isPlayerBullet = true;

        MovementComponent cMovement = mMovement.create(bulletId);
        cMovement.direction = direction;
        cMovement.speed = 12;

        CollisionBoxComponent cCollision = mCollision.create(bulletId);
        cCollision.set(4f, 4f, cSprite.sprite.getWidth() - 8f, cSprite.sprite.getHeight() - 8f);

        mOutOfScreenDestroy.create(bulletId);
    }

    @Override
    public boolean keyDown(int keycode) {
        heldKeys.keyDown(keycode);
        if (keycode == Input.Keys.BACKSPACE) {
            sRender.debug = !sRender.debug;
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
