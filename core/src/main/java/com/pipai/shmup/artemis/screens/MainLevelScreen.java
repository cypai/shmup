package com.pipai.shmup.artemis.screens;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.pipai.shmup.ShmupGame;
import net.mostlyoriginal.api.event.common.EventSystem;

public class MainLevelScreen implements Screen {

    private World world;

    public MainLevelScreen(ShmupGame game) {
        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(
                        new TagManager(),
                        new EventSystem())
                .build();

        world = new World(config);
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
