package com.example.stphanie.sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private PuzzleView puzzleView;
    private Grid grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Generator generator = new Generator();
        grid = generator.generate(42);
        puzzleView = new PuzzleView((this));
        puzzleView.setGrid(grid);
        setContentView(puzzleView);


    }
}
