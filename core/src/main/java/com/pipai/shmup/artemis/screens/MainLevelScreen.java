package com.pipai.shmup.artemis.screens;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.pipai.shmup.ShmupGame;
import com.pipai.shmup.Utils;
import com.pipai.shmup.artemis.screens.initializers.MainLevelScreenInitializer;
import com.pipai.shmup.artemis.systems.*;
import com.pipai.shmup.artemis.systems.input.ExitInputProcessor;
import net.mostlyoriginal.api.event.common.EventSystem;

public class MainLevelScreen implements Screen {

    private World world;

    public MainLevelScreen(ShmupGame game) {
        System.out.println("Calling the example function.");
        int returnValue = Utils.example(1);
        System.out.println("Return value of the example function: " + returnValue);

        System.out.println("Now calling the object example function.");
        Utils.objectExample();

        WorldConfiguration config = new WorldConfigurationBuilder()
                .with(
                        new TagManager(),
                        new GroupManager(),
                        new EventSystem(),

                        new ControlSystem(game),
                        new GameStateSystem(),
                        new MovementSystem(),

                        new InputProcessingSystem(),
                        new RenderingSystem(game))
                .build();

        world = new World(config);

        InputProcessingSystem inputProcessingSystem = world.getSystem(InputProcessingSystem.class);
        inputProcessingSystem.addProcessor(world.getSystem(ControlSystem.class));
        inputProcessingSystem.addProcessor(new ExitInputProcessor());

        new MainLevelScreenInitializer(game, world).initialize();
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
