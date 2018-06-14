package com.pipai.shmup.artemis.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.pipai.shmup.artemis.components.PathInterpolationComponent;
import com.pipai.shmup.artemis.components.XyComponent;

public class PathInterpolationSystem extends IteratingSystem {

    private ComponentMapper<XyComponent> mXy;
    private ComponentMapper<PathInterpolationComponent> mInterpolation;

    public PathInterpolationSystem() {
        super(Aspect.all(XyComponent.class, PathInterpolationComponent.class));
    }

    @Override
    protected void process(int entityId) {
        XyComponent cXy = mXy.get(entityId);
        PathInterpolationComponent cInterpolation = mInterpolation.get(entityId);

        cInterpolation.t += 1;
        if (cInterpolation.t > cInterpolation.tMax) {
            mInterpolation.remove(entityId);
            switch (cInterpolation.onEndStrategy) {
                case REMOVE:
                    mInterpolation.remove(entityId);
                    break;
                case DESTROY:
                    world.delete(entityId);
                    break;
            }
            if (cInterpolation.onEnd != null) {
                cInterpolation.onEnd.run();
            }
        }

        float a = (float) cInterpolation.t / (float) cInterpolation.tMax;
        cXy.x = cInterpolation.interpolation.apply(cInterpolation.xStart, cInterpolation.xEnd, a);
        cXy.y = cInterpolation.interpolation.apply(cInterpolation.yStart, cInterpolation.yEnd, a);
    }

}
