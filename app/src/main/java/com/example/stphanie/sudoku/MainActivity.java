package com.example.stphanie.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private PuzzleView puzzleView;
    private Grid grid;
    int difficultyLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int nrGaps = 0;

        Intent mIntent = getIntent();
        difficultyLevel = mIntent.getIntExtra("DIFFICULTY_LEVEL", 0);
        if(difficultyLevel == 1){
            nrGaps = 25;
        }
        else if (difficultyLevel == 2){
            nrGaps = 40;
        }
        else if (difficultyLevel == 3)
            nrGaps = 50;
        else
            nrGaps = 55;

        Generator generator = new Generator();
        grid = generator.generate(nrGaps);
        puzzleView = new PuzzleView((this));
        puzzleView.setGrid(grid);
        setContentView(puzzleView);

    }
}
