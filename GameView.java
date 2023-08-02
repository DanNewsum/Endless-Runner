package com.example.myapplication;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import android.graphics.RectF;
public class GameView extends SurfaceView implements Runnable {
    private Thread gameThread = null; // The game thread where the game runs
    private boolean gameOver = false; // Flag to check if the game is over
    private int fallingObjectSpawnTimer = 0; // Timer to spawn new FallingObjects

    private Player player;  // The player object
    private Paint paint;  // The paint object used to draw on the canvas
    private float screenY; // The screen's height
    private float screenX; // The screen's width
    private long startTime; // The start time of the game
    private List<FallingObject> fallingObjects = new ArrayList<>(); // List of all the FallingObjects
    private volatile boolean isPlaying; // Flag to check if the game is playing

    public GameView(Context context) {
        super(context);

        paint = new Paint(); // Initializing the paint object
        paint.setColor(Color.WHITE); // Setting the color of the paint

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenX = displayMetrics.widthPixels; // Getting the width of the screen
        screenY = displayMetrics.heightPixels; // Getting the height of the screen

        player = new Player(100,screenX,screenY-100); // Initializing the player object
        startTime = System.currentTimeMillis(); // Recording the start time of the game
    }

    @Override
    public void run() {
        while (isPlaying) {
            update(); // Updating the state of the game objects
            draw(); // Drawing the game objects
        }
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();

            canvas.drawColor(Color.BLACK); // Clearing the screen with black color

            if (player != null) {  // Make sure player exists before trying to draw
                paint.setColor(Color.WHITE);
                canvas.drawRect(player.getRect(), paint); // Drawing the player
            }

            for (FallingObject object : fallingObjects) {
                canvas.drawRect(object.getRect(), paint); // Drawing the FallingObjects
            }

            if (gameOver) { // If game is over
                paint.setColor(Color.RED);
                paint.setTextSize(100);
                canvas.drawText("Game Over", screenX / 2f, screenY / 2f, paint); // Display "Game Over"
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void update() {
        Iterator<FallingObject> iterator = fallingObjects.iterator();
        while (iterator.hasNext()) {
            FallingObject object = iterator.next();
            object.update(); // Updating the state of the FallingObject

            if (object.getRect().top > screenY) {
                iterator.remove(); // Removing the FallingObject if it is off screen
            }

            if (RectF.intersects(player.getRect(), object.getRect())) {
                isPlaying = false; // Stopping the game if the player collides with a FallingObject
                gameOver = true; // Marking the game as over
                System.out.println("Game Over"); // Logging "Game Over"
                break;
            }
        }

        if (System.currentTimeMillis() - startTime >= 1000) {
            fallingObjectSpawnTimer++;
            if (fallingObjectSpawnTimer >= 20) {  // Adjust this as needed
                for (int i = 0; i < 5; i++) {
                    fallingObjects.add(new FallingObject(50, screenX)); // Spawning new FallingObjects
                }
                fallingObjectSpawnTimer = 0; // Resetting the timer
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);  // This will aim for about 60 frames per second.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true; // Marking the game as playing
        gameThread = new Thread(this); // Creating a new thread for the game
        gameThread.start(); // Starting the game thread
    }

    public void pause() {
        isPlaying = false; // Marking the game as not playing
        try {
            gameThread.join(); // Waiting for the game thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                player.move(event.getX()); // Moving the player to the touch location
                break;
        }
        return true;
    }
}
