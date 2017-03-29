/*
 * TCSS 305 - Tetris
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Board;
import model.Board.BoardData;
import model.Board.GameStatus;

/**
 * This class creates my Board graphics.
 * 
 * @author bkim22
 * @version Winter 2015
 */
@SuppressWarnings("serial")
public class BoardDisplay extends JPanel implements Observer
{
    /**
     * Boards width.
     */
    private static final int BOARD_WIDTH = 250;

    /**
     * Boards height.
     */
    private static final int BOARD_HEIGHT = 500;

    /**
     * Board dimensions.
     */
    private static final Dimension PANEL_SIZE = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);

    /**
     * My Tetris board.
     */
    private Board myBoard;

    /**
     * My board data.
     */
    private List<Color[]> myBoardData;

    /**
     * My death check.
     */
    private boolean myGameOver;

    /**
     * Constructor for my Board GUI.
     * 
     * @param theBoard the Tetris board.
     */
    public BoardDisplay(final Board theBoard)
    {
        myBoard = theBoard;
        myBoard.addObserver(this);
        setPreferredSize(PANEL_SIZE);
    }

    /**
     * Draws the grid and tetrominoes.
     * 
     * @param theGraphics The graphics.
     */
    @Override
    public void paintComponent(final Graphics theGraphics)
    {
        super.paintComponent(theGraphics);

        final int boardDivisor = 10;
        final int cellSize = BOARD_WIDTH / boardDivisor;
        final int startingY = 475;
        final int startingRowBuffer = 5;

        for (int i = myBoardData.size() - startingRowBuffer; i >= 0; i--)
        {
            final Color[] column = myBoardData.get(i);

            for (int j = 0; j < column.length; j++)
            {
                // draw my rows
                theGraphics.setColor(Color.GRAY);

                theGraphics.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);

                if (column[j] != null)
                {
                    if (myGameOver)
                    {
                        theGraphics.setColor(Color.GRAY);
                    }
                    else
                    {
                        theGraphics.setColor(column[j]);
                    }
                    theGraphics.fill3DRect(j * cellSize, startingY - (i * cellSize), cellSize,
                                           cellSize, true);
                }
            }
        }

        if (myGameOver)
        {
            final int xLocation = 50;
            final int yLocation = 300;
            final int fontSize = 30;

            theGraphics.setColor(Color.red);
            theGraphics.setFont(new Font("Small Fonts", Font.BOLD, fontSize));
            theGraphics.drawString("Game Over", xLocation, yLocation);
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
        if (theObservable instanceof Board)
        {
            if (arg1 instanceof BoardData)
            {
                myBoardData = ((BoardData) arg1).getBoardData();
            }

            if (arg1 instanceof GameStatus)
            {
                myGameOver = ((GameStatus) arg1).isGameOver();
            }
        }
    }
}
