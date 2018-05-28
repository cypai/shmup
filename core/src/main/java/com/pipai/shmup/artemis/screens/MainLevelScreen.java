package com.pipai.shmup.artemis.screens;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.pipai.shmup.ShmupGame;
import com.pipai.shmup.artemis.components.StaticSpriteComponent;
import com.pipai.shmup.artemis.components.XyComponent;
import com.pipai.shmup.artemis.systems.InputProcessingSystem;
import com.pipai.shmup.artemis.systems.RenderingSystem;
import com.pipai.shmup.artemis.systems.input.ControlInputProcessor;
import com.pipai.shmup.artemis.systems.input.ExitInputProcessor;
import net.mostlyoriginal.api.event.common.EventSystem;

public class MainLevelScreen implements Screen {

    private World world;

    public MainLevelScreen(ShmupGame game) {
        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(
                        new TagManager(),
                        new GroupManager(),
                        new EventSystem(),

                        new InputProcessingSystem(),
                        new RenderingSystem(game))
                .build();

        world = new World(config);

        InputProcessingSystem inputProcessingSystem = world.getSystem(InputProcessingSystem.class);
        inputProcessingSystem.addProcessor(new ControlInputProcessor());
        inputProcessingSystem.addProcessor(new ExitInputProcessor());

        int playerId = world.create();
        XyComponent cPlayerXy = world.getMapper(XyComponent.class).create(playerId);
        StaticSpriteComponent cPlayerSprite = world.getMapper(StaticSpriteComponent.class).create(playerId);
        cPlayerSprite.sprite = new Sprite(game.getAssetManager().get("data/ship.png", Texture.class));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        world.setDelta(delta);
        world.process();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        world.dispose();
    }
}
