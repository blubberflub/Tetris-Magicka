
package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import model.Board;

/**
 * My control panel.
 * 
 * @author bkim22
 * @version Winter 2015
 */
@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements KeyListener
{
    /**
     * My panel size.
     */
    private static final Dimension PANEL_SIZE = new Dimension(500, 150);

    /**
     * My board.
     */
    private Board myBoard;

    /**
     * Frame that panel is on.
     */
    private Frame myFrame;
    
    /**
     * array to store my key codes.
     */
    private int[] myKeyCodes = new int[]{KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_DOWN,
                                         KeyEvent.VK_SPACE, KeyEvent.VK_UP, KeyEvent.VK_Z, KeyEvent.VK_SHIFT};
    
    /**
     * array to store my key codes.
     */
    private String[] myStringReference = new String[]{"Right", "Left", "Down",
                                                      "Space", "Up", "Z", "Shift"};

    
    /**
     * Key code to move right. (default: right arrow key.)
     */
    private int myRightKey = KeyEvent.VK_RIGHT;
    
    /**
     * Key code to move left. (default: left arrow key.)
     */
    private int myLeftKey = KeyEvent.VK_LEFT;
    
    /**
     * Key code to move down. (default: down arrow key.)
     */
    private int myDownKey = KeyEvent.VK_DOWN;
    
    /**
     * Key code to drop block. (default: space key.)
     */
    private int myDropKey = KeyEvent.VK_SPACE;
    
    /**
     * Key code to rotate right. (default: up arrow key.)
     */
    private int myRotateRightKey = KeyEvent.VK_UP;
    
    /**
     * Key code to rotate right. (default: up arrow key.)
     */
    private int myRotateLeftKey = KeyEvent.VK_Z;
    
    /**
     * Key code to hold piece. (default: shift key.)
     */
    private int myHoldKey = KeyEvent.VK_SHIFT;
    
    /**
     * My buttons.
     */
    private final JButton[] myButtonList =
    {
        new JButton(myStringReference[0]),
        new JButton(myStringReference[1]),
        new JButton(myStringReference[2]),
        new JButton(myStringReference[3]),
        new JButton(myStringReference[4]),
        new JButton(myStringReference[5]),   
        new JButton(myStringReference[6]),   
    };

    /**
     * Constructor for my control panel.
     * 
     * @param theBoard the Tetris board.
     * @param theFrame the frame.
     */
    public ControlPanel(final Board theBoard, final Frame theFrame)
    {
        myBoard = theBoard;
        myFrame = theFrame;

        setLayout(new BorderLayout());
        setPreferredSize(PANEL_SIZE);

        addPanels();
    }

    /**
     * Create panels inside to organize my gui.
     */
    private void addPanels()
    {
        final int rows = 4;

        final JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new GridLayout(rows, 0));
        upperPanel.setBorder(BorderFactory.createLoweredBevelBorder());

        final JPanel bottom = new JPanel();

        final JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent theEvent)
            {
                myFrame.dispose();
                                
                for (int i = 0; i < myButtonList.length; i++)
                {
                    myButtonList[i].setText(myStringReference[i]);
                }
            }
        });

        final JButton okay = new JButton("Ok");
        okay.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent theEvent)
            {
                initializeKeys();
                
                for (int i = 0; i < myButtonList.length; i++)
                {
                    myStringReference[i] = myButtonList[i].getText();
                }
               
                myFrame.dispose();
            }
        });
        
        bottom.add(cancel);
        bottom.add(okay);

        add(upperPanel);
        addComponents(upperPanel);

        add(bottom, BorderLayout.SOUTH);
    }

    /**
     * Creates my labels and buttons.
     * 
     * @param thePanel the panel.
     */
    private void addComponents(final JPanel thePanel)
    {
        final JLabel[] labelList =
        {
            new JLabel("Move Right: "),
            new JLabel("Move Left: "),
            new JLabel("Move Down: "),
            new JLabel("Drop:"),
            new JLabel("Rotate Right: "),
            new JLabel("Rotate Left: "),
            new JLabel("Hold Piece: ")
        };
        
        for (JLabel label : labelList)
        {
            final int spacing = 10;
            final int fontSize = 14;

            label.setBorder(new EmptyBorder(spacing, spacing, spacing, spacing));
            label.setFont(new Font("Arial", Font.PLAIN, fontSize));
        }
        
        
        //setup listeners for my buttons.
        final int rightButton = 0;
        final int leftButton = 1;
        final int downButton = 2;
        final int dropButton = 3;
        final int rotateRightButton = 4;
        final int rotateLeftButton = 5;
        final int holdButton = 6;
        
        setupButton(myButtonList[rightButton], rightButton);
        setupButton(myButtonList[leftButton], leftButton);
        setupButton(myButtonList[downButton], downButton);
        setupButton(myButtonList[dropButton], dropButton);
        setupButton(myButtonList[rotateRightButton], rotateRightButton);
        setupButton(myButtonList[rotateLeftButton], rotateLeftButton);
        setupButton(myButtonList[holdButton], holdButton);

        
        for (int i = 0; i < labelList.length; i++)
        {
            thePanel.add(labelList[i]);
            thePanel.add(myButtonList[i]);
        }
    }
    
    /**
     * Setup my button actions to in game controls.
     * @param theButton the button.
     * @param theKey the game control.
     */
    private void setupButton(final JButton theButton, final int theKey)
    {
        //Remove space select.
        final InputMap im = (InputMap) UIManager.get("Button.focusInputMap");
        im.put(KeyStroke.getKeyStroke("pressed SPACE"), "none");
        
        KeyAdapter buttonListeners = new KeyAdapter()
        {
            public void keyPressed(final KeyEvent theEvent)
            {
                if (theEvent.getKeyCode() == KeyEvent.VK_SPACE)
                {
                    theButton.setText("Space");
                    myKeyCodes[theKey] = theEvent.getKeyCode();
                    theButton.removeKeyListener(this);
                }
                else if (theEvent.getKeyCode() == KeyEvent.VK_UP)
                {
                    theButton.setText("Up");
                    myKeyCodes[theKey] = theEvent.getKeyCode();
                    theButton.removeKeyListener(this);
                }
                else if (theEvent.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    theButton.setText("Left");
                    myKeyCodes[theKey] = theEvent.getKeyCode();
                    theButton.removeKeyListener(this);
                }
                else if (theEvent.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    theButton.setText("Right");
                    myKeyCodes[theKey] = theEvent.getKeyCode();
                    theButton.removeKeyListener(this);
                }
                else if (theEvent.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    theButton.setText("Down");
                    myKeyCodes[theKey] = theEvent.getKeyCode();
                    theButton.removeKeyListener(this);
                }
                else if (theEvent.getKeyCode() == KeyEvent.VK_SHIFT)
                {
                    theButton.setText("Shift");
                    myKeyCodes[theKey] = theEvent.getKeyCode();
                    theButton.removeKeyListener(this);
                }
                else
                {                            
                    theButton.setText(" " + theEvent.getKeyChar());
                    myKeyCodes[theKey] = theEvent.getKeyCode();
                    theButton.removeKeyListener(this);
                }
            }                 
        };
        
        theButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent theEvent)
            {
                theButton.setText("...");
                theButton.addKeyListener(buttonListeners); 
            }         
        });
    }
    
    /**
     * Sets my keys.
     */
    private void initializeKeys()
    {
        myRightKey = myKeyCodes[0];
        myLeftKey = myKeyCodes[1];
        myDownKey = myKeyCodes[2];
        myDropKey = myKeyCodes[3];
        myRotateRightKey = myKeyCodes[4];
        myRotateLeftKey = myKeyCodes[5];
        myHoldKey = myKeyCodes[6];
    }

    /**
     * My Key pressed method.
     * 
     * @param theEvent The key event.
     */
    @Override
    public void keyPressed(final KeyEvent theEvent)
    {
        if (theEvent.getKeyCode() == myRightKey)
        {
            myBoard.right();
        }
        else if (theEvent.getKeyCode() == myLeftKey)
        {
            myBoard.left();
        }
        else if (theEvent.getKeyCode() == myDownKey)
        {
            myBoard.down();
        }
        else if (theEvent.getKeyCode() == myDropKey)
        {
            myBoard.drop();
        }
        else if (theEvent.getKeyCode() == myHoldKey)
        {
            myBoard.hold();
        }
        else if (theEvent.getKeyCode() == myRotateRightKey)
        {
            myBoard.rotateCW();
        }
        else if (theEvent.getKeyCode() == myRotateLeftKey)
        {
            myBoard.rotateCCW();
        }
    }

    /**
     * Actions if key is held.
     * 
     * @param theEvent The key event.
     */
    @Override
    public void keyTyped(final KeyEvent theEvent)
    {
        if (theEvent.getKeyCode() == myRightKey)
        {
            myBoard.right();
        }
        else if (theEvent.getKeyCode() == myLeftKey)
        {
            myBoard.left();
        }
        else if (theEvent.getKeyCode() == myDownKey)
        {
            myBoard.down();
        }
        else if (theEvent.getKeyCode() == myDropKey)
        {
            myBoard.drop();
        }
    }

    @Override
    public void keyReleased(final KeyEvent arg0)
    {
        // this method is not used.
    }
}
