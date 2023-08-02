package com.example.myapplication;
import android.graphics.RectF;

import java.util.Random;

public class FallingObject {
    private RectF rect;
    private float speed;

    public FallingObject(int objectSize, float screenX) {
        speed = 10;  // Adjust this to control the speed of the falling objects

        // Randomly position the object along the x-axis at the top of the screen
        float left = new Random().nextFloat() * (screenX - objectSize);
        rect = new RectF(left, -objectSize, left + objectSize, 0);
    }

    public void update() {
        // Move the object down the screen
        rect.offset(0, speed);
    }

    public RectF getRect() {
        return rect;
    }
}
