package com.example.stphanie.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {
    static final int EASY_PUZZLE = 1;
    static final int MEDIUM_PUZZLE = 2;
    static final int HARD_PUZZLE = 3;
    static final int NIGHTMARE_PUZZLE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    private void startPuzzle(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void experimentLayout(View v){
        Intent intent = new Intent(this, ExperimentActivity.class);
        startActivity(intent);
    }

    public void onClick(View v){
        startPuzzle();
    }

    public void onEasyClick(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("DIFFICULTY_LEVEL", 1);
        startActivityForResult(intent, EASY_PUZZLE);
    }

    public void onMediumClick(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("DIFFICULTY_LEVEL", 2);
        startActivityForResult(intent, MEDIUM_PUZZLE);
    }

    public void onHardClick(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("DIFFICULTY_LEVEL", 3);
        startActivityForResult(intent, HARD_PUZZLE);
    }

    public void onNightmareClick(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("DIFFICULTY_LEVEL", 4);
        startActivityForResult(intent, MEDIUM_PUZZLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Check the difficulty level
        if (requestCode == EASY_PUZZLE) {
            //Check if the puzzle was solved
            if (resultCode == RESULT_OK) {
                //TODO Add score to highscore
            }
        }


    }


}
