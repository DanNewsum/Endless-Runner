package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private GameView gameView; // GameView is your custom view for the game

    // This method is called when the activity is starting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Call the superclass method
        gameView = new GameView(this); // Initialize your custom game view
        setContentView(gameView); // Set the user interface layout for this activity as your custom game view
    }

    // This method is called when the activity will start interacting with the user
    @Override
    protected void onResume() {
        super.onResume(); // Call the superclass method
        gameView.resume(); // Resume the game (this is a method in your GameView class)
    }

    // This method is called when the system is about to start resuming another activity
    @Override
    protected void onPause() {
        super.onPause(); // Call the superclass method
        gameView.pause(); // Pause the game (this is a method in your GameView class)
    }
}
