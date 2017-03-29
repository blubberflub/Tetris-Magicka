/*
 * TCSS 305
 */

package view;

import java.awt.EventQueue;

/**
 * Runs Tetris by instantiating and starting the TetrisGUI.
 * 
 * @author bkim22
 * @version Winter 2015
 */
public final class TetrisMain
{

    /**
     * Private constructor, to prevent instantiation of this class.
     */
    private TetrisMain()
    {
        throw new IllegalStateException();
    }

    /**
     * The main method, invokes the Tetris GUI. Command line arguments are
     * ignored.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new TetrisGUI();
            }
        });
    }
}
