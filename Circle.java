package com.example.myapplication;

public class Circle {
    private float x;  // x and y represent the center of the circle.
    private float y;
    private float radius;
    private float speed;

    public Circle(float x, float y, float radius, float speed) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.speed = speed;
    }

    public void update() {
        y += speed;  // Move the circle down by its speed

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }
}
