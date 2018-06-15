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

        if (cInterpolation.delay > 0) {
            cInterpolation.delay--;
            return;
        }

        cInterpolation.t += 1;

        float a = (float) cInterpolation.t / (float) cInterpolation.tMax;
        cXy.x = cInterpolation.interpolation.apply(cInterpolation.xStart, cInterpolation.xEnd, a);
        cXy.y = cInterpolation.interpolation.apply(cInterpolation.yStart, cInterpolation.yEnd, a);

        if (cInterpolation.t > cInterpolation.tMax) {
            if (cInterpolation.onEndStrategy != null) {
                switch (cInterpolation.onEndStrategy) {
                    case REMOVE:
                        mInterpolation.remove(entityId);
                        break;
                    case DESTROY:
                        world.delete(entityId);
                        break;
                }
            }
            if (cInterpolation.onEnd != null) {
                cInterpolation.onEnd.accept(cInterpolation);
            }
        }
    }

}
