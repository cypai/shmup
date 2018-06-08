package com.pipai.shmup.artemis.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.pipai.shmup.artemis.components.MovementComponent;
import com.pipai.shmup.artemis.components.XyComponent;

public class MovementSystem extends IteratingSystem {

    private ComponentMapper<XyComponent> mXy;
    private ComponentMapper<MovementComponent> mMovement;

    public MovementSystem() {
        super(Aspect.all(XyComponent.class, MovementComponent.class));
    }

    @Override
    protected void process(int entityId) {
        XyComponent cXy = mXy.get(entityId);
        MovementComponent cMovement = mMovement.get(entityId);

        cXy.x += cMovement.speed * Math.cos(cMovement.direction);
        cXy.y += cMovement.speed * Math.sin(cMovement.direction);
    }

}
