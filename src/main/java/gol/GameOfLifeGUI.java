package gol;

import java.awt.event.*;

import javax.swing.*;

import java.awt.*;
import java.io.*;

/**
 * The Class GameOfLifeGUI.
 */
public class GameOfLifeGUI implements ActionListener {

	/** The Constant ACTION_COMMAND_STOP. */
	private static final String ACTION_COMMAND_STOP   = "Stop";
	
	/** The Constant ACTION_COMMAND_CLEAR. */
	private static final String ACTION_COMMAND_CLEAR  = "Clear";
	
	/** The Constant ACTION_COMMAND_NEXT. */
	private static final String ACTION_COMMAND_NEXT   = "Next";
	
	/** The Constant ACTION_COMMAND_START. */
	private static final String ACTION_COMMAND_START  = "Start";
	
	/** The Constant ACTION_COMMAND_RESIZE. */
	private static final String ACTION_COMMAND_RESIZE = "Resize";
	
	/** The Constant ACTION_COMMAND_EXIT. */
	private static final String ACTION_COMMAND_EXIT   = "Exit";
	
	/** The Constant ACTION_COMMAND_SAVE. */
	private static final String ACTION_COMMAND_SAVE   = "Save";
	
	/** The Constant ACTION_COMMAND_LOAD. */
	private static final String ACTION_COMMAND_LOAD   = "Load";
	
	/** The Constant ACTION_COMMAND_FILE. */
	private static final String ACTION_COMMAND_FILE   = "File";
	
	/** The menu bar. */
	private JMenuBar	menuBar				= new JMenuBar();
	
	/** The menu. */
	private JMenu		menu				= new JMenu(ACTION_COMMAND_FILE);
	
	/** The menu item1. */
	private JMenuItem	menuItem1			= new JMenuItem(ACTION_COMMAND_LOAD);
	
	/** The menu item2. */
	private JMenuItem	menuItem2			= new JMenuItem(ACTION_COMMAND_SAVE);
	
	/** The menu item3. */
	private JMenuItem	menuItem3			= new JMenuItem(ACTION_COMMAND_EXIT);
	
	/** The frame. */
	private JFrame		frame				= new JFrame("Game of life");
	
	/** The cell panel. */
	private JPanel		cellPanel			= new JPanel();
	
	/** The info panel. */
	private JPanel		infoPanel			= new JPanel();
	
	/** The button panel. */
	private JPanel		buttonPanel			= new JPanel();
	
	/** The button clear. */
	private JButton		buttonClear			= new JButton(ACTION_COMMAND_CLEAR);
	
	/** The button next. */
	private JButton		buttonNext			= new JButton(ACTION_COMMAND_NEXT);
	
	/** The button start stop. */
	private JButton		buttonStartStop		= new JButton(ACTION_COMMAND_START);
	
	/** The button resize. */
	private JButton		buttonResize		= new JButton(ACTION_COMMAND_RESIZE);
	
	/** The text field speed. */
	private JTextField	textFieldSpeed		= new JTextField(3);
	
	/** The text field row. */
	private JTextField	textFieldRow		= new JTextField(2);
	
	/** The text field column. */
	private JTextField	textFieldColumn		= new JTextField(2);
	
	/** The label row. */
	private JLabel		labelRow			= new JLabel("  Rows: ");		;
	
	/** The label speed. */
	private JLabel		labelSpeed			= new JLabel("Speed (ms): ");
	
	/** The label column. */
	private JLabel		labelColumn			= new JLabel("  Cols: ");		;
	
	/** The label stop info. */
	private JLabel		labelStopInfo; 
	
	/** The label generation. */
	private JLabel      labelGeneration;
	
	/** The init size rows. */
	private int			initSizeRows		= 10;
	
	/** The init size columns. */
	private int			initSizeColumns		= 10;
	
	/** The size. */
	private int			size				= 10;
	
	/** The b start. */
	private boolean		bStart				= false;
	
	/** The b exit. */
	private boolean		bExit				= false;
	
	/** The grid. */
	private CellGrid		grid			= new CellGrid();
	
	
	private static final String GENERATION_PREFIX = "Generation: ";

	/**
	 * Creates a new instance of Game_of_life.
	 */
	public GameOfLifeGUI() {
	}

	/**
	 * Inits the.
	 */
	public void init() {
		 
		menu.add(menuItem1);
		menu.add(menuItem2);
		menu.add(menuItem3);
		menuBar.add(menu);
		menuItem1.addActionListener(this);
		menuItem2.addActionListener(this);
		menuItem3.addActionListener(this);
		frame.setJMenuBar(menuBar);

		cellPanel.setLayout(new GridLayout(initSizeRows, initSizeColumns));
		cellPanel.setPreferredSize(new Dimension(size * initSizeColumns, size * initSizeRows));

		// Creating panel for user informations
		infoPanel.setLayout(new BorderLayout());
		labelStopInfo = new JLabel("");
		labelGeneration = new JLabel(GENERATION_PREFIX + 0);
		infoPanel.add(labelStopInfo, BorderLayout.WEST);
		infoPanel.add(labelGeneration, BorderLayout.EAST);

		// Creating panel with user controls
		buttonPanel.setLayout(new FlowLayout());
		textFieldSpeed.setText("100");
		textFieldRow.setText("" + 0);
		textFieldColumn.setText("" + 0);
		buttonPanel.add(buttonClear);
		buttonPanel.add(buttonNext);
		buttonPanel.add(buttonStartStop);
		buttonPanel.add(labelSpeed);
		buttonPanel.add(textFieldSpeed);
		buttonPanel.add(labelRow);
		buttonPanel.add(textFieldRow);
		buttonPanel.add(labelColumn);
		buttonPanel.add(textFieldColumn);
		buttonPanel.add(buttonResize);
		buttonClear.addActionListener(this);
		buttonNext.addActionListener(this);
		buttonStartStop.addActionListener(this);
		buttonResize.addActionListener(this);

		// Completing frame
		frame.getContentPane().add(cellPanel, BorderLayout.NORTH);
		frame.getContentPane().add(infoPanel, BorderLayout.CENTER);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		// If window is closed, program should end
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
		// Removes any existing empty areas from frame
		frame.pack();
	}

	/**
	 * Inits the field.
	 */
	public void initField() {
		// Creating panel for buttons, which correspond to cells
		cellPanel.removeAll();
		cellPanel.setLayout(new GridLayout(grid.getRows(), grid.getCols()));
		cellPanel.setPreferredSize(new Dimension(size * grid.getCols(), size * grid.getRows()));
				
		//TODO Add the Observer Pattern here 
		//Buttons have to be added to the GUI and connected with the Cell
		//Do not use the observer interface of java
		
		
		cellPanel.updateUI();
		frame.pack();
		frame.setVisible(true);
	}


	/**
	 * Run.
	 */
	public void run() {
		int delay = 100;
		while (!bExit) {
			while (bStart) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException ex) {
					return;
				}
				nextGeneration();
				labelGeneration.setText(GENERATION_PREFIX+grid.getGeneration()+"");
				try {
					delay = Integer.parseInt(textFieldSpeed.getText());
				} catch (NumberFormatException nf) {
					delay = 100;
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				return;
			}
		}
	}

	/**
	 * Save file.
	 */
	private void saveFile() {
		JFileChooser saveChooser = new JFileChooser(new File("./fields"));
		int returnVal = saveChooser.showSaveDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = saveChooser.getSelectedFile();
			

			grid.saveGrid(file);
			
		} else {
			System.out.println("Save command cancelled by user");
		}
	}

	/**
	 * Load file.
	 */
	private void loadFile() {
		JFileChooser loadChooser = new JFileChooser(new File("./fields"));
		int returnVal = loadChooser.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = loadChooser.getSelectedFile();
			
			//TODO call the corresponding  method
			grid.loadGrid(file);
			
			
		} else {
			System.out.println("Load command cancelled by user");
		}
		initField();
	}

	/**
	 * Refrshes states of shown cells.
	 */
	void nextGeneration() {

		grid.nextGeneration();

		//TODO optional call check stable
		// grid.checkStable();
	}

	/** Resizes cellfield to given user values. */
	void resize() {
		int row    = Integer.valueOf(textFieldRow.getText());
		int column = Integer.valueOf(textFieldColumn.getText());
		cellPanel.setLayout(new GridLayout(initSizeRows, initSizeColumns));
		cellPanel.setPreferredSize(new Dimension(size * column, size * row));
		

		grid = new CellGrid(column, row);


		
		initSizeRows = row;
		initSizeColumns = column;
		initField();
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		String sAction = e.getActionCommand();
		if (ACTION_COMMAND_CLEAR.equals(sAction)) {
		    this.clear();
		    labelGeneration.setText(GENERATION_PREFIX+grid.getGeneration());
		} else if (ACTION_COMMAND_NEXT.equals(sAction)) {
			this.nextGeneration();
			labelGeneration.setText(GENERATION_PREFIX+grid.getGeneration());
		} else if (ACTION_COMMAND_START.equals(sAction)) {
			buttonStartStop.setText(ACTION_COMMAND_STOP);
			bStart = true;
		} else if (ACTION_COMMAND_STOP.equals(sAction)) {
			buttonStartStop.setText(ACTION_COMMAND_START);
			bStart = false;
		} else if (ACTION_COMMAND_RESIZE.equals(sAction)) {
			this.resize();
		} else if (ACTION_COMMAND_EXIT.equals(sAction)) {
			bExit = true;
			frame.dispose();
		} else if (ACTION_COMMAND_LOAD.equals(sAction)) {
			loadFile();
		} else if (ACTION_COMMAND_SAVE.equals(sAction)) {
			saveFile();
		} else {
            //TODO optional react on button events to switch the state of the button
            //this can be used for customizing the field
			System.out.println("Action not processed: "+sAction);
		}
	}

	/**
	 * Clear.
	 */
	private void clear() {

		grid.reset();

	}
}
