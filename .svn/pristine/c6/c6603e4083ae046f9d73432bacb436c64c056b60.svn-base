/*
 * TCSS 305 - Tetris
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Jpanel pause screen.
 * @author bkim22
 * @version Winter 2015
 */
@SuppressWarnings("serial")
public class PauseScreen extends JPanel
{
    /**
     * Constant for my pause screen size.
     */
    private static final Dimension PANEL_SIZE = new Dimension(600, 700);

    /**
     * Constructor for my pause screen.
     */
    public PauseScreen()
    {
        setPreferredSize(PANEL_SIZE);
        repaint();
    }
    
    /**
     * Draws my pause screen.
     * @param theGraphics The graphics.
     */
    @Override
    public void paintComponent(final Graphics theGraphics)
    {
        super.paintComponent(theGraphics);
        
        final int xLocation = 280;
        final int yLocation = 300;
        
        theGraphics.setColor(Color.RED);
        theGraphics.drawString("PAUSED", xLocation, yLocation);
    }
}
