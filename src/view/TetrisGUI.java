/*
 * TCSS 305 - Tetris
 */

package view;

import com.sun.glass.events.KeyEvent;

import controller.ControlPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;


import model.Board;
import model.Board.GameStatus;

/**
 * A demonstration of a composite layout.
 * 
 * @author bkim22
 * @version Winter 2015
 */
@SuppressWarnings("serial")
public final class TetrisGUI extends JFrame implements Observer
{
    /**
     * Fixed dimensions for my game window.
     */
    private static final Dimension WINDOW_SIZE = new Dimension(600, 700);
    
    /**
     * A Kyubey icon from an image file.
     */
    private static final Icon PANE_ICON = new ImageIcon("images/kyubey.jpg");

    /**
     * Screen key name.
     */
    private static final String SCREEN_ONE = "1";

    /**
     * Screen key name.
     */
    private static final String SCREEN_TWO = "2";

    /**
     * My application icon.
     */
    private static final Image ICON = Toolkit.getDefaultToolkit().getImage("images/icon.png");

    /**
     * My control panel icon.
     */
    private static final Image CONTROL_ICON = Toolkit.getDefaultToolkit()
                    .getImage("images/Gamepad-icon.png");

    /**
     * My menu bar.
     */
    private final JMenuBar myMenuBar = new JMenuBar();

    /**
     * My swing/animation timer.
     */
    private final Timer myTimer = new Timer(1000, new ActionListener()
    {
        public void actionPerformed(final ActionEvent theEvent)
        {
            myBoard.step();
        }
    });

    /**
     * My Tetris board.
     */
    private Board myBoard = new Board();

    /**
     * Panel layout for my master panel.
     */
    private CardLayout myScreen = new CardLayout();

    /**
     * My master panel.
     */
    private JPanel myMasterPanel;

    /**
     * My next piece drawing.
     */
    private GameBackground myBackground;

    /**
     * Board display.
     */
    private BoardDisplay myBoardUi = new BoardDisplay(myBoard);

    /**
     * My pause screen.
     */
    private PauseScreen myPauseScreen = new PauseScreen();

    /**
     * Checks to pause screen.
     */
    private boolean myPauseCheck;

    /**
     * Playing boolean.
     */
    private boolean myCurrentlyPlaying;

    /**
     * Game over boolean.
     */
    private boolean myGameOver;

    /**
     * My music player.
     */
    private MusicPlayer myPlayer;
    
    /**
     * My sound player.
     */
    private SoundPlayer mySounds = new SoundPlayer();
    
    
    /**
     * String file path.
     */
    private String myGameOverSound = "bgm/death.mid";

    /**
     * Control frame.
     */
    private JFrame myControlFrame = new JFrame("Controls");

    /**
     * Key panel.
     */
    private final ControlPanel myControlPanel = new ControlPanel(myBoard, myControlFrame);

    /**
     * JMenu button end game.
     */
    private JMenuItem myEndGameMenu;

    /**
     * JMenu button new game.
     */
    private JMenuItem myNewGameMenu;

    /**
     * Game status boolean.
     */
    private GameStatus myGameStatus;

    /**
     * Constructor for my TetrisGUI.
     */
    public TetrisGUI()
    {
        super("Tetris Magica");
        myBoard.addObserver(this);

        setIconImage(ICON);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_SIZE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        addComponents();
    }

    /**
     * Adds components to my frame.
     */
    public void addComponents()
    {
        createFileMenuBar();
        createOptionsMenuBar();
        createHelpMenuBar();
        add(myMenuBar, BorderLayout.NORTH);

        drawScreens();
        myBoard.clear();        
    }

    /**
     * Sets up keys and displays the GUI.
     */
    public void startNewGame()
    {
        addKeyListener(myControlPanel);
        myBoard.clear();
        myBackground.setPlaying(true);
        myBackground.resetCounters();
        myBackground.resetHold();
        myCurrentlyPlaying = true;
        myTimer.start();
        initializeAudio();
        myScreen.show(myMasterPanel, SCREEN_ONE);
    }

    /**
     * Ends the game.
     */
    public void endGame()
    {
        removeKeyListener(myControlPanel);
        myCurrentlyPlaying = false;
        myTimer.stop();
        myGameStatus.setGameOver(true);
    }

    /**
     * Pauses my game.
     */
    public void stop()
    {
        myTimer.stop();
        removeKeyListener(myControlPanel);
    }

    /**
     * Resumes my game.
     */
    public void resume()
    {
        myTimer.start();
        addKeyListener(myControlPanel);
    }

    /**
     * Play my background music.
     */
    public void initializeAudio()
    {

        final File[] files = new File[1];
        files[0] = new File("bgm/bg.wav");

        myPlayer = new MusicPlayer();
        myPlayer.newList(files);
        myPlayer.play();
        
        mySounds.preLoad(myGameOverSound);
        
    }

    /**
     * Creates the different panels.
     */
    private void drawScreens()
    {
        // In game screen
        final JPanel gameScreen = new JPanel();

        // Piece
        myBackground = new GameBackground(myBoard, myTimer);
        myBackground.setLayout(new BorderLayout());

        // Board
        final int emptyYSpace = 50;
        final int emptyXSpace = 170;

        // North padding.
        final JPanel northPad = new JPanel();
        northPad.setPreferredSize(new Dimension(0, emptyYSpace));
        northPad.setOpaque(false);

        // Left padding
        final JPanel westPad = new JPanel();
        westPad.setLayout(new BorderLayout());
        westPad.setPreferredSize(new Dimension(emptyXSpace, 0));   
        
        final int characterLocation = 280;
        
        final JPanel northWestPad = new JPanel();
        northWestPad.setPreferredSize(new Dimension(0, characterLocation)); 
        northWestPad.setOpaque(false);
        
        // Character gif
        final Icon icon = new ImageIcon("images/madoka.gif");
        final JLabel label = new JLabel(icon);
        
        westPad.add(northWestPad, BorderLayout.NORTH);
        westPad.add(label, BorderLayout.CENTER);
        westPad.setOpaque(false);

        myBoardUi.setOpaque(false);

        myBackground.add(northPad, BorderLayout.NORTH);
        myBackground.add(westPad, BorderLayout.WEST);
        myBackground.add(myBoardUi, BorderLayout.CENTER);
        gameScreen.add(myBackground);

        // Pause screen
        final JPanel pauseScreen = new JPanel();
        pauseScreen.add(myPauseScreen);

        // Master panel
        myMasterPanel = new JPanel();
        myMasterPanel.setLayout(myScreen);
        myMasterPanel.add(gameScreen, SCREEN_ONE);
        myMasterPanel.add(pauseScreen, SCREEN_TWO);

        add(myMasterPanel);

        // Default screen on startup
        myScreen.show(myMasterPanel, SCREEN_ONE);

        // Switches to pause on key press.
        switchScreen();
    }

    /**
     * This method adds a keybinding to my panel to pause the game.
     */
    public void switchScreen()
    {
        final String switchKey = "switch";

        myMasterPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                        .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), switchKey);
        myMasterPanel.getActionMap().put(switchKey, new AbstractAction()
        {

            @Override
            public void actionPerformed(final ActionEvent arg0)
            {
                pause();
            }
        });
    }

    /**
     * Pauses the game.
     */
    private void pause()
    {
        if (myCurrentlyPlaying && !myGameOver)
        {
            myPauseCheck = !myPauseCheck;
            myPlayer.togglePause();

            if (myPauseCheck)
            {
                myScreen.show(myMasterPanel, SCREEN_TWO);
                stop();
            }
            else
            {
                myScreen.show(myMasterPanel, SCREEN_ONE);
                resume();
            }
        }
    }
    
    /**
     * If game over, stop music and enable new game.
     */
    private void gameOver()
    {
        if (myGameOver)
        {
            myPlayer.stopPlay();
            mySounds.play(myGameOverSound);
            myNewGameMenu.setEnabled(true);
            myEndGameMenu.setEnabled(false);
        }
    }

    /**
     * Action when you click new game.
     */
    private void newGameAction()
    {
        removeKeyListener(myControlPanel);

        final int reply =
                        JOptionPane.showConfirmDialog(null, "Start a new game?", "New Game",
                                                      JOptionPane.YES_NO_OPTION, 0, PANE_ICON);
        if (reply == JOptionPane.YES_OPTION)
        {      
            myBackground.setPlaying(true);
            startNewGame();
            myBoard.clear();
            myNewGameMenu.setEnabled(false);
            myEndGameMenu.setEnabled(true);
        }
        else
        {
            if (myCurrentlyPlaying)
            {
                addKeyListener(myControlPanel);
            }
        }
    }

    /**
     * Action when you click end game.
     */
    private void endGameAction()
    {
        removeKeyListener(myControlPanel);

        final int reply =
                        JOptionPane.showConfirmDialog(null, "End current game?", "End Game",
                                                      JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION)
        {
            endGame();
            myNewGameMenu.setEnabled(true);
            myEndGameMenu.setEnabled(false);
        }
        else
        {
            addKeyListener(myControlPanel);
        }
    }

    /**
     * This method creates the file menu.
     */
    private void createFileMenuBar()
    {

        // File
        final JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        // New game
        myNewGameMenu = new JMenuItem("New Game");

        myNewGameMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                                                            ActionEvent.CTRL_MASK));
        myNewGameMenu.addActionListener(new ActionListener()
        {
            public void actionPerformed(final ActionEvent theEvent)
            {
                newGameAction();
            }
        });
        fileMenu.add(myNewGameMenu);

        // End game
        myEndGameMenu = new JMenuItem("End Game");
        myEndGameMenu.setEnabled(false);
        myEndGameMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                                                            ActionEvent.CTRL_MASK));
        myEndGameMenu.addActionListener(new ActionListener()
        {
            public void actionPerformed(final ActionEvent theEvent)
            {
                endGameAction();
            }
        });
        fileMenu.add(myEndGameMenu);

        // Exit
        final JMenuItem exit = new JMenuItem("Exit");
        exit.setMnemonic(KeyEvent.VK_X);
        exit.addActionListener(new ActionListener()
        {
            public void actionPerformed(final ActionEvent theEvent)
            {
                myPlayer.stopPlay();
                dispose();
            }
        });

        fileMenu.add(exit);
        myMenuBar.add(fileMenu);
    }

    /**
     * Create my options menu bar.
     */
    private void createOptionsMenuBar()
    {
        // Options
        final JMenu optionsMenu = new JMenu("Options");
        optionsMenu.setMnemonic(KeyEvent.VK_O);
        myMenuBar.add(optionsMenu);

        // controls
        final JMenuItem controls = new JMenuItem("Set Controls...");
        controls.setMnemonic(KeyEvent.VK_C);
        optionsMenu.add(controls);
        controls.addActionListener(new ActionListener()
        {
            public void actionPerformed(final ActionEvent arg0)
            {
                if (!myPauseCheck)
                {
                    pause();
                }
                myControlFrame.setVisible(true);

                myControlFrame.setIconImage(CONTROL_ICON);
                myControlFrame.add(myControlPanel);
                myControlFrame.pack();
                myControlFrame.setLocationRelativeTo(null);
            }
        });
    }

    /**
     * Create my help menu bar.
     */
    private void createHelpMenuBar()
    {
        // Help
        final JMenu help = new JMenu("Help");
        help.setMnemonic(KeyEvent.VK_H);

        // About
        final JMenuItem about = new JMenuItem("About...");
        about.addActionListener(new ActionListener()
        {
            public void actionPerformed(final ActionEvent theEvent)
            {
                new JOptionPane();
                JOptionPane.showMessageDialog(null, "SCORING SYSTEM: \n" + "\n"
                                                    + "Single  =  40 points \n"
                                                    + "Double =  100 points \n"
                                                    + "Triple   =  300 points \n"
                                                    + "Tetris   =  1200 points");
            }
        });
        help.add(about);

        myMenuBar.add(help);
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
            if (arg1 instanceof GameStatus)
            {
                myGameStatus = (GameStatus) arg1;
                myGameOver = ((GameStatus) arg1).isGameOver();
                gameOver();
            }
        }
    }
}
