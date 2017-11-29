package com.example.stphanie.sudoku;

import java.util.concurrent.TimeUnit;

/**
 * Created by Stéphanie on 28-11-2017.
 */

public class Puzzle {
    Grid grid;
    int nrOfGaps, difficulty;
    long startTime, savedTime;
    String strTimer;

    public Puzzle(int difficulty){
        this.difficulty = difficulty;
        strTimer = "";
        if(difficulty == 1){
            nrOfGaps = 25;
        }
        else if (difficulty == 2){
            nrOfGaps = 40;
        }
        else if (difficulty == 3)
            nrOfGaps = 50;
        else
            nrOfGaps = 55;

        Generator generator = new Generator();
        grid = generator.generate(nrOfGaps);
        savedTime = 0;
    }

    public void saveGame(){
        long now = System.currentTimeMillis();
        savedTime += now - startTime;
    }

    public void startPuzzle(){
        startTime = System.currentTimeMillis();
    }

    private long getTotalTimePassed(){
        return savedTime + (System.currentTimeMillis()-startTime);
    }

    public Grid getGrid(){
        return grid;
    }

    public Grid.Cell getCell(int row, int column) {
        return grid.getCell(row,column);
    }

    public void fillInCell(int row, int column, int value){
        Grid.Cell cell = this.getCell(row,column);
        if(!cell.getPreset() &&  grid.isValidValueForCell(cell, value)){
            cell.setValue(value);
            nrOfGaps--;
        }
    }

    public void eraseCell(int row, int column){
        Grid.Cell cell = this.getCell(row,column);
        if(!cell.getPreset()){
            cell.setValue(0);
            nrOfGaps++;
        }
    }

    public String getDifficulty(){
        if(difficulty == 1)
            return "Easy";
        else if (difficulty == 2)
            return "Medium";
        else if(difficulty == 3)
            return "Hard";
        else
            return "Nightmare";
    }

    public String getStrTimer(){
        long totalTimePassed = getTotalTimePassed();
        strTimer = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(totalTimePassed),
                TimeUnit.MILLISECONDS.toMinutes(totalTimePassed) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalTimePassed)),
                TimeUnit.MILLISECONDS.toSeconds(totalTimePassed) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTimePassed)));
        return strTimer;
    }

    public boolean isSolved(){
        return nrOfGaps == 0;
    }
}
