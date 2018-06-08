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
        if (cXy.x < 0
                || cXy.x > Gdx.graphics.getWidth()
                || cXy.y < 0
                || cXy.y > Gdx.graphics.getHeight()) {

            world.delete(entityId);
        }
    }

}
