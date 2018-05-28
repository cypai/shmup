package com.pipai.shmup.artemis.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

public class InputProcessingSystem extends NoProcessingSystem {

    private InputMultiplexer multiplexer;

    @Override
    protected void initialize() {
        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void addProcessor(InputProcessor inputProcessor) {
        multiplexer.addProcessor(inputProcessor);
    }

    public void activateInput() {
        Gdx.input.setInputProcessor(multiplexer);
    }

}
