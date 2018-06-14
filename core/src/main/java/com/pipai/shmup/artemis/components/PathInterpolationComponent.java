package com.pipai.shmup.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Interpolation;

public class PathInterpolationComponent extends Component {

    public Interpolation interpolation;
    public int t;
    public int tMax;
    public float xStart;
    public float yStart;
    public float xEnd;
    public float yEnd;

    public OnEndStrategy onEndStrategy = OnEndStrategy.REMOVE;
    public Runnable onEnd;

    public void setStart(XyComponent xy) {
        xStart = xy.x;
        yStart = xy.y;
    }

    public enum OnEndStrategy {
        DESTROY, REMOVE
    }

}
