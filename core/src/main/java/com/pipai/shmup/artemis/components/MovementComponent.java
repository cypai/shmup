package com.pipai.shmup.artemis.components;

import com.artemis.Component;

public class MovementComponent extends Component {
    public float direction;
    public float speed;

    public void setDirectionDegrees(float degrees) {
        direction = (float) (degrees / 180 * Math.PI);
    }
}
