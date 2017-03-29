/*
 * TCSS 305 - Tetris
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Board;
import model.Board.CompletedLines;
import model.Point;
import model.TetrisPiece;

/**
 * This class creates my Board graphics and handles my score/level/lines
 * cleared.
 * 
 * @author bkim22
 * @version Winter 2015
 */
@SuppressWarnings("serial")
public class GameBackground extends JPanel implements Observer
{
    /**
     * Size of game background panel.
     */
    private static final Dimension PANEL_SIZE = new Dimension(600, 650);

    /**
     * My Tetris board.
     */
    private Board myBoard;

    /**
     * Hold piece point data.
     */
    private Point[] myHoldPiece;

    /**
     * Next piece's color.
     */
    private Color myHoldPieceColor;

    /**
     * Next piece point data.
     */
    private Point[] myNextPiece;

    /**
     * Next piece's color.
     */
    private Color myNextPieceColor;

    /**
     * My timer.
     */
    private Timer myTimer;

    /**
     * Checks to see if playing.
     */
    private boolean myPlaying;

    /**
     * Cleared line update.
     */
    private List<Integer> myLineUpdate;

    /**
     * Cleared line count.
     */
    private int myClearedLines;

    /**
     * int to keep track of lines to next level.
     */
    private int myLevelLines;

    /**
     * Score.
     */
    private int myScore;

    /**
     * Level.
     */
    private int myLevel = 1;

    /**
     * Draws the background and next piece of my game.
     * 
     * @param theBoard the Tetris board.
     * @param theTimer delay between steps.
     */
    public GameBackground(final Board theBoard, final Timer theTimer)
    {
        myBoard = theBoard;
        myTimer = theTimer;
        myBoard.addObserver(this);
        setPreferredSize(PANEL_SIZE);
    }

    /**
     * Sets the playing value.
     * 
     * @param thePlaying boolean currently playing.
     */
    public void setPlaying(final boolean thePlaying)
    {
        myPlaying = thePlaying;
        repaint();
    }

    /**
     * Reset my hold piece.
     */
    public void resetHold()
    {
        myHoldPiece = null;
    }

    /**
     * reset my numbers.
     */
    public void resetCounters()
    {
        myClearedLines = 0;
        myLevelLines = 0;
        myScore = 0;
    }

    /**
     * Retrieves my current level.
     * 
     * @return level.
     */
    public int getLevel()
    {
        return myLevel;
    }

    /**
     * Draws the next piece.
     * 
     * @param theGraphics The graphics.
     */
    @Override
    public void paintComponent(final Graphics theGraphics)
    {  
        super.paintComponent(theGraphics);

        // GameBackground
        BufferedImage background = null;
        
        try
        {
            background = ImageIO.read(new File("images/Background.png"));
        }
        catch (final IOException e)
        {
            System.out.println(e.getMessage());
        }

        theGraphics.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        // Lines cleared.
        final int fontSize = 18;
        final int textX = 87;
        final int linesY = 257;
        final int scoreY = 314;
        final int levelY = 373;

        theGraphics.setFont(new Font("Small Fonts", Font.BOLD, fontSize));
        theGraphics.setColor(Color.WHITE);
        theGraphics.drawString("" + myClearedLines, textX, linesY);

        // Score.
        theGraphics.drawString("" + myScore, textX, scoreY);

        // level
        theGraphics.drawString("" + myLevel, textX, levelY);

        // Draw my next piece

        final int cellSize = 20;
        final int xValueBuffer = 480;
        final int yValueBuffer = 130;
        final int otherXValueBuffer = 470;
        
        final int xHoldValueBuffer = 48;
        final int otherXHoldValueBuffer = 38;

        if (myPlaying)
        {
            for (int i = 0; i < myNextPiece.length; i++)
            {
                theGraphics.setColor(myNextPieceColor);

                if (!myNextPieceColor.equals(Color.CYAN)
                    && (!myNextPieceColor.equals(Color.YELLOW)))
                {
                    theGraphics.fill3DRect(xValueBuffer + (myNextPiece[i].x() * cellSize),
                                           yValueBuffer - (myNextPiece[i].y() * cellSize),
                                           cellSize, cellSize, true);
                }
                else
                {
                    theGraphics.fill3DRect(otherXValueBuffer + (myNextPiece[i].x() * cellSize),
                                           yValueBuffer - (myNextPiece[i].y() * cellSize),
                                           cellSize, cellSize, true);
                }
            }
            
            //draw my hold piece.
            if (myHoldPiece != null)
            {
                for (int i = 0; i < myHoldPiece.length; i++)
                {
                    theGraphics.setColor(myHoldPieceColor);

                    if (!myHoldPieceColor.equals(Color.CYAN)
                        && (!myHoldPieceColor.equals(Color.YELLOW)))
                    {
                        theGraphics.fill3DRect(xHoldValueBuffer + (myHoldPiece[i].x() * cellSize),
                                               yValueBuffer - (myHoldPiece[i].y() * cellSize),
                                               cellSize, cellSize, true);
                    }
                    else
                    {
                        theGraphics.fill3DRect(otherXHoldValueBuffer + (myHoldPiece[i].x() * cellSize),
                                               yValueBuffer - (myHoldPiece[i].y() * cellSize),
                                               cellSize, cellSize, true);
                    }
                }
            }
        }     
    }

    /**
     * Retrives data from the Board class.
     * 
     * @param theObservable The observable class.
     * @param arg1 The object being passed in.
     */
    @Override
    public void update(final Observable theObservable, final Object arg1)
    {
        repaint();

        if (arg1 instanceof CompletedLines)
        {
            myLineUpdate = ((CompletedLines) arg1).getCompletedLines();
            myClearedLines = myClearedLines + myLineUpdate.size();
            myLevelLines = myLevelLines + myLineUpdate.size();

            updateLevelAndScore();
        }

        if (arg1 instanceof TetrisPiece)
        {
            if (((TetrisPiece) arg1).isHoldPiece())
            {
                myHoldPiece = ((TetrisPiece) arg1).getPoints();
                myHoldPieceColor = ((TetrisPiece) arg1).getColor();
            }
            else
            {
                myNextPiece = ((TetrisPiece) arg1).getPoints();
                myNextPieceColor = ((TetrisPiece) arg1).getColor();
            }
        }
    }

    /**
     * Helper method to update level and score.
     */
    private void updateLevelAndScore()
    {

        final int maxLevel = 9;
        final int linesToLevelUp = 10;
        final int defaultTimerDelay = 1000;
        final int delayDecrease = 100;

        if (myLevel != maxLevel)
        {
            if (myLevelLines >= linesToLevelUp)
            {
                myLevel++;
                myTimer.setDelay(defaultTimerDelay - myLevel * delayDecrease);
                myLevelLines = myLevelLines % linesToLevelUp;
            }
        }

        final int single = 40;
        final int doubleVar = 100;
        final int triple = 300;
        final int tetris = 1200;

        switch (myLineUpdate.size())
        {
            case 1:
                myScore = myScore + single;
                break;
            case 2:
                myScore = myScore + doubleVar;
                break;
            case 3:
                myScore = myScore + triple;
                break;
            case 4:
                myScore = myScore + tetris;
                break;
            default:
                break;
        }
    }
}
