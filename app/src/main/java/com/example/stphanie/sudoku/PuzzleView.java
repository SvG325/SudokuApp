package com.example.stphanie.sudoku;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Stéphanie on 29-10-2017.
 */

public class PuzzleView extends View implements View.OnTouchListener{
    private Rect[][] board;
    private RectF[][] digits;
    private int selectedI, selectedJ, digitI, digitJ, digitWidth, selectedDigit;
    Grid grid;
    Paint paint;
    boolean blnEraser;
    boolean blnCellFirst, blnCellSelected;

    int canvasWidth, canvasHeight, puzzleWidth;
    int outerMargin, lineWidthA, lineWidthB;
    int cellWidth;


    public PuzzleView(Context context) {
        super(context);
        board = new Rect[9][9];
        digits = new RectF[2][6];
        paint = new Paint();
        selectedI = -1;
        selectedJ = -1;
        digitI = -1;
        digitJ = -1;
        selectedDigit = -1;
        blnCellSelected = false;
        blnCellFirst = true;
        blnEraser = false;
        setOnTouchListener(this);
    }

    public void onDraw(Canvas canvas){
        outerMargin = 5;
        lineWidthA = 3;
        lineWidthB = 1;
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        puzzleWidth = canvasWidth-2*outerMargin-2*lineWidthA;
        cellWidth = (puzzleWidth-2*lineWidthA-6*lineWidthB)/9;

        paintBoard(canvas);
        paintNumbers(canvas);
    }

    private void paintBoard(Canvas canvas){
        paint.setColor(Color.BLACK);
        canvas.drawRect(new android.graphics.Rect( outerMargin, outerMargin, canvasWidth-outerMargin, canvasHeight-outerMargin), paint);

        paint.setColor(Color.WHITE);
        int currX, currY;
        currX = outerMargin+lineWidthA;
        currY = currX;
        for(int i=0; i<9;i++){
            for(int j=0;j<9;j++){
                if(blnCellFirst) {
                    if ((selectedI != -1 && i == selectedI) || ((selectedJ != -1 && j == selectedJ))) {
                        paint.setColor(Color.YELLOW);
                    } else
                        paint.setColor(Color.WHITE);
                }else{
                    if(selectedDigit != -1 && grid.getCell(i,j).getValue() == selectedDigit)
                        paint.setColor(Color.YELLOW);
                    else
                        paint.setColor(Color.WHITE);
                }

                canvas.drawRect(new android.graphics.Rect(currX,currY,currX+cellWidth,currY+cellWidth), paint);


                Rect cell = new Rect(currX, currY, currX+cellWidth, currY+cellWidth);
                board[i][j] = cell;
                if (j==0) {
                    currX += cellWidth +lineWidthB;//Move 1 cell and 1 thin line
                }else if(j==2 || j == 5){
                    currX += cellWidth +lineWidthB + lineWidthA;//Move 1 cell and 1 thin line and 1 thick line
                }else if(j == 8){
                    currX = outerMargin+lineWidthA;//Reset for next row
                }else{
                    currX += cellWidth +2*lineWidthB;//Move 1 cell and 2 thin lines
                }
            }
            if (i==0) {
                currY += cellWidth +lineWidthB;//Move 1 cell and 1 thin line
            }else if(i==2 || i == 5){
                currY += cellWidth +lineWidthB + lineWidthA;//Move 1 cell and 1 thin line and 1 thick line
            }else if(i == 8){
                currY += outerMargin+lineWidthA;//Reset for next row
            }else{
                currY += cellWidth +2*lineWidthB;//Move 1 cell and 2 thin lines
            }
        }

        //Paint board for digits
        outerMargin = 8;
        int lineWidthDigits = 12;
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
        int innerAreaWidth = canvasWidth-4*outerMargin;
        int digitCellWidth = (innerAreaWidth-5*lineWidthDigits)/6;//5 equal lines between the 6 cells in each row
        currX = 2*outerMargin;
        currY += 3*outerMargin + cellWidth;
        digitWidth = digitCellWidth;

        paint.setColor(Color.WHITE);
        for(int i=0; i<2;i++){
            for(int j=0;j<6;j++){
                if((digitI != -1 && i == digitI) && ((digitJ != -1 && j == digitJ)) && (i == 0 || (j != 5 && j!=3))){
                    paint.setColor(Color.YELLOW);
                }else
                    paint.setColor(Color.WHITE);
                RectF r = new RectF(currX,currY,currX+digitCellWidth,currY+digitCellWidth);
                canvas.drawRoundRect(r, 15, 15, paint);
                digits[i][j] = r;
                if(j==5){
                    currX = 2*outerMargin; //Reset x for next row
                }else{
                    currX += digitCellWidth+lineWidthDigits;
                }
            }
            currY += digitCellWidth+2*outerMargin;
        }
    }

    private  void paintNumbers(Canvas canvas){
        if(grid == null)
            return;
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        sizeText(cellWidth);
        String text = "1";

        Rect r = new Rect();
        int yPos;
        for(int i=0; i<9;i++){
            for(int j=0;j<9;j++){
                Rect gc = board[i][j];
                paint.getTextBounds(text, 0, text.length(), r);
                yPos = gc.top + r.height() + ((cellWidth- r.height())/2);
                int value = grid.getCell(i,j).getValue();
                if(value != 0) {
                    if(grid.getCell(i,j).getPreset())
                        paint.setColor(Color.BLACK);
                    else
                        paint.setColor(Color.rgb(102,102,153));
                    canvas.drawText("" + value, gc.left + cellWidth / 2, yPos, paint);
                }
            }
        }
        int counter = 1;
        sizeText((int) digits[0][0].height());
        paint.setColor(Color.BLACK);
        for(int i=0; i<2;i++){
            for(int j=0;j<6;j++) {
                RectF digit = digits[i][j];
                paint.getTextBounds(text, 0, text.length(), r);
                yPos = (int) digit.top + r.height() + ((((int) digit.height())- r.height())/2);
                if(counter < 10)
                    canvas.drawText("" + counter, (int) digit.left+ (digitWidth)/2, yPos ,paint);
                else if(counter == 10){
                //Empty for now, room for a new option
                } else if(counter == 11){
                    Rect target = new Rect();
                    digit.round(target);
                    target.left = target.left + ((int) (digitWidth * 0.1));
                    target.top = target.top + ((int) (digitWidth * 0.1));
                    target.right = target.right - ((int) (digitWidth * 0.1));
                    target.bottom = target.bottom - ((int) (digitWidth * 0.1));

                    Drawable drwSquare = ResourcesCompat.getDrawable(getResources(), R.drawable.erase, null);
                    drwSquare.setBounds(target);
                    drwSquare.draw(canvas);
                }else if (counter == 12){
                    Rect target = new Rect();
                    digit.round(target);
                    target.left = target.left + ((int) (digitWidth * 0.1));
                    target.top = target.top + ((int) (digitWidth * 0.1));
                    target.right = target.right - ((int) (digitWidth * 0.1));
                    target.bottom = target.bottom - ((int) (digitWidth * 0.1));
                    Drawable drwSquare;
                    if(blnCellFirst)
                        drwSquare = ResourcesCompat.getDrawable(getResources(), R.drawable.arrowdown, null);
                    else
                        drwSquare = ResourcesCompat.getDrawable(getResources(), R.drawable.arrowup, null);
                    drwSquare.setBounds(target);
                    drwSquare.draw(canvas);
                }

                counter++;
            }
        }
    }

    private void sizeText(int height){
        paint.setTextSize(10);
        while(height > paint.getTextSize()+2){
            paint.setTextSize(paint.getTextSize()+1);
        }
    }

    public void setGrid(Grid g){
        grid = g;
    }

    public void setSelectedI(int I){
        selectedI = I;
    }
    public void setSelectedJ(int J){
        selectedJ = J;
    }
    public void setDigitI(int I){selectedI = I;}
    public void setDigitJ(int J){digitJ = J;}

    //OnTouchListener stuff
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            boolean end = false;
            boolean clickedBoardArea = false;
            boolean clickedDigitArea = false;
            for(int i=0;i<9;i++){
                for(int j=0; j<9;j++){
                    if(event.getX() >= board[i][j].left && event.getX() <= board[i][j].right
                            && event.getY() >= board[i][j].top && event.getY() <= board[i][j].bottom){
                        selectedI = i;
                        selectedJ = j;
                        blnCellSelected = true;
                        end = true;
                        clickedBoardArea = true;
                        break;
                    }
                }
                if(end)
                    break;
            }

            end = false;
            for(int i=0;i<2;i++) {
                for (int j = 0; j < 6; j++) {
                    if(event.getX() >= digits[i][j].left && event.getX() <= digits[i][j].right
                            && event.getY() >= digits[i][j].top && event.getY() <= digits[i][j].bottom){
                        digitI = i;
                        digitJ = j;
                        selectedDigit = 6*i + j + 1;
                        end = true;
                        clickedBoardArea = false;
                        clickedDigitArea = true;
                        if(i == 1 && j == 3) {
                            selectedDigit = -1;
                        } else if(i == 1 && j == 4){
                            blnEraser = true;
                            selectedDigit = -1;
                        } else if(i == 1 && j == 5) {
                            blnCellFirst = !blnCellFirst;
                            selectedDigit = -1;
                        }

                        break;
                    }
                }
                if(end)
                    break;
            }

            if(!blnCellFirst){
                if(blnCellSelected && clickedBoardArea){
                    Grid.Cell cell = grid.getCell(selectedI, selectedJ);

                    if (!cell.getPreset()) {
                        if(selectedDigit != -1 && grid.isValidValueForCell(cell, selectedDigit))
                            cell.setValue(selectedDigit);
                        else
                            cell.setValue(0);
                    }
                }
            }
            else{
                if(blnCellSelected && clickedDigitArea){
                    Grid.Cell cell = grid.getCell(selectedI, selectedJ);

                    if (!cell.getPreset()) {
                        if(selectedDigit != -1  && grid.isValidValueForCell(cell, selectedDigit))
                            cell.setValue(selectedDigit);
                        else
                            cell.setValue(0);
                    }
                }
            }
        }
        this.invalidate();
        return true;
    }
}

/**
 * TODO LIST:
 * - add option menu
 *          - draw preset numbers bold
 *          - refuse wrong answers(stron and weak version)
 *          - show hint color
 *          - reset selected digit/cell after filling in a value
 * - small numbers in the corners
 * - fix hint coloring
 * -improve graphics
 */
