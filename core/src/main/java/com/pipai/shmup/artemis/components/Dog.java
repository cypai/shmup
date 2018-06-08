package com.pipai.shmup.artemis.components;

public class Dog {

    public Dog(String name) {
        this.name = name;
    }

    private String name;
    public float x;
    public float y;

    public void walkTo(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void bark() {
        System.out.println("Woof!");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
