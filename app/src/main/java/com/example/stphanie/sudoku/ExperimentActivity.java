package com.example.stphanie.sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ExperimentActivity extends AppCompatActivity {
    private ExperimentView experimentView;
    private Grid grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Generator generator = new Generator();
        grid = generator.generate(42);
        experimentView = new ExperimentView((this));
        experimentView.setGrid(grid);
        setContentView(experimentView);


    }
}
