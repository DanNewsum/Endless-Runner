package com.example.myapplication;
import android.util.DisplayMetrics;
import android.graphics.RectF;

public class Player {
    private RectF rect; // The rectangle representing the player on screen
    private float speed; // The speed of the player
    private float screenX; // The width of the screen
    private boolean isJumping; // Flag to check if the player is jumping

    public Player(int playerSize, float screenX, float screenY) {
        speed = 0; // Initializing the speed of the player
        isJumping = false; // Setting the jumping status of the player to false
        this.screenX = screenX; // Storing the width of the screen

        // Positioning the player initially at the center of screen horizontally
        float left = (screenX - playerSize) / 2;

        // Positioning the player at the bottom of the screen vertically
        float top = screenY - playerSize;

        // Initializing the rectangle for the player
        rect = new RectF(left, top, left + playerSize, top + playerSize);
    }

    public void update() {
        // This is where you'd add any code for updating the state of the player
    }

    public void move(float x) {
        // Ensuring the player does not move off the screen to the left
        x = Math.max(x, 0);
        // Ensuring the player does not move off the screen to the right
        x = Math.min(x, this.screenX - rect.width());
        // Moving the player horizontally
        rect.offsetTo(x, rect.top);
    }

    public RectF getRect() {
        // Returning the rectangle representing the player
        return rect;
    }
}
