package com.pipai.shmup.artemis.components;

public class CircularShotType {
    public int amount;
    public float spreadAngle = (float) Math.PI * 2;
    public float speed;
    public AimType aimType;
    public float aimOffset;
    public float direction;
    public String spriteFilename;

    public CircularShotType withRandomSame(int amount, int speed, String spriteFilename) {
        this.amount = amount;
        this.speed = speed;
        this.spriteFilename = spriteFilename;
        this.aimType = AimType.RANDOM_SAME;
        return this;
    }

    public CircularShotType withAimed(int amount, int speed, String spriteFilename) {
        this.amount = amount;
        this.speed = speed;
        this.spriteFilename = spriteFilename;
        this.aimType = AimType.AIMED;
        return this;
    }

    public enum AimType {
        AIMED, RANDOM, RANDOM_SAME, DIRECTION
    }

}
