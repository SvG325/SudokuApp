package com.example.stphanie.sudoku;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private PuzzleView puzzleView;
    private Puzzle puzzle;
    int difficultyLevel;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent mIntent = getIntent();
        difficultyLevel = mIntent.getIntExtra("DIFFICULTY_LEVEL", 0);
        puzzle = new Puzzle(difficultyLevel);
        puzzle.startPuzzle();
        puzzleView = new PuzzleView(this, puzzle);

        setContentView(puzzleView);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                puzzleView.invalidate();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000);

    }
}
