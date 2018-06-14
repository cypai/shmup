package com.pipai.shmup.artemis.components;

import com.artemis.Component;

public class CollisionBoxComponent extends Component {
    public float xOffset;
    public float yOffset;
    public float width;
    public float height;

    public void set(float xOffset, float yOffset, float width, float height) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
    }
}
