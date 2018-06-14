package com.pipai.shmup.artemis;

public class Configuration {

    private float leftBound = 0;
    private float rightBound = 800 * 2 / 3;

    public float getLeftBound() {
        return leftBound;
    }

    public void setLeftBound(float leftBound) {
        this.leftBound = leftBound;
    }

    public float getRightBound() {
        return rightBound;
    }

    public void setRightBound(float rightBound) {
        this.rightBound = rightBound;
    }

    public float getUiWidth() {
        return 800 - rightBound;
    }

}
