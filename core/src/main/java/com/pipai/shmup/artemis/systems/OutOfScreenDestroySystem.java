package com.pipai.shmup.artemis.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.pipai.shmup.artemis.components.OutOfScreenDestroyComponent;
import com.pipai.shmup.artemis.components.XyComponent;

public class OutOfScreenDestroySystem extends IteratingSystem {

    private ComponentMapper<XyComponent> mXy;

    public OutOfScreenDestroySystem() {
        super(Aspect.all(XyComponent.class, OutOfScreenDestroyComponent.class));
    }

    @Override
    protected void process(int entityId) {
        XyComponent cXy = mXy.get(entityId);
        if (cXy.x < -32
                || cXy.x > Gdx.graphics.getWidth() + 32
                || cXy.y < -32
                || cXy.y > Gdx.graphics.getHeight() + 32) {

            world.delete(entityId);
        }
    }

}
