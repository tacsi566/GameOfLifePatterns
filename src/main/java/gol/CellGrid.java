package gol;

import java.util.*;
import java.io.*;

/**
 * The Class CellGrid.
 */
public class CellGrid {

	/** The grid. */
	private Cell[][] grid;

	/** The cols. */
	private int cols;

	/** The rows. */
	private int rows;

	/** The generation. */
	private int generation;

	/**
	 * Instantiates a new cell grid.
	 */
	public CellGrid() {
	}

	/**
	 * Instantiates a new cell grid.
	 *
	 * @param cols the cols
	 * @param rows the rows
	 */
	public CellGrid(int cols, int rows) {
		this.initGrid(cols, rows);
	}

	/**
	 * Sets the cell state.
	 *
	 * @param col the col
	 * @param row the row
	 * @param cellValue the cell value
	 */
	public void setCellState(int col, int row, boolean cellValue) {
		this.grid[col][row].setState(cellValue);
	}

	/**
	 * Gets the cell state.
	 *
	 * @param col the col
	 * @param row the row
	 * @return the cell state
	 */
	public boolean getCellState(int col, int row) {
		return this.grid[col][row].getState();
	}

	/**
	 * Sets the cols.
	 *
	 * @param cols the new cols
	 */
	private void setCols(int cols) {
		this.cols = cols;
	}

	/**
	 * Gets the cols.
	 *
	 * @return the cols
	 */
	public int getCols() {
		return this.cols;
	}

	/**
	 * Sets the rows.
	 *
	 * @param rows the new rows
	 */
	private void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	public int getRows() {
		return this.rows;
	}

	/**
	 * Sets the generation.
	 *
	 * @param generation the new generation
	 */
	public void setGeneration(int generation) {
		this.generation = generation;
	}

	/**
	 * Gets the generation.
	 *
	 * @return the generation
	 */
	public int getGeneration() {
		return this.generation;
	}

	/**
	 * Next generation.
	 */
	public void nextGeneration() {
		Cell[][] tempGrid = new Cell[this.getCols()][this.getRows()];

		for (int i = 0; i < this.getCols(); i++) {
			for (int j = 0; j < this.getRows(); j++) {
				tempGrid[i][j] = new Cell(this.calculateNewCellState(i, j, this.getAliveNeighboursCount(i, j)));
			}
		}
		for (int i = 0; i < this.getCols(); i++) {
			for (int j = 0; j < this.getRows(); j++) {
				grid[i][j].setState(tempGrid[i][j].getState());
			}
		}
		this.setGeneration(this.getGeneration() + 1);
	}


	/**
	 * Gets the alive neighbours count.
	 *
	 * @param col the col
	 * @param row the row
	 * @return the alive neighbours count
	 */
	public int getAliveNeighboursCount(int col, int row) {
		int aliveNeighboursCount = 0;
		for (int i = col - 1; i < col + 2; i++) {
			for (int j = row - 1; j < row + 2; j++) {
				if (i >= 0 && j >= 0 && i < this.getCols() && j < this.getRows() && (i != col || j != row)) {
					if (this.grid[i][j].getState())
						aliveNeighboursCount++;
				}
			}
		}
		return aliveNeighboursCount;
	}

	/**
	 * Calculate new cell state.
	 *
	 * @param col the col
	 * @param row the row
	 * @param aliveNeighboursCount the alive neighbours count
	 * @return true, if successful
	 */
	boolean calculateNewCellState(int col, int row, int aliveNeighboursCount) {
		if (this.grid[col][row].getState() && aliveNeighboursCount != 2 && aliveNeighboursCount != 3)
			return false;
		if (!this.grid[col][row].getState() && aliveNeighboursCount == 3)
			return true;

		return this.grid[col][row].getState();
	}

	/**
	 * Reset.
	 */
	public void reset() {
		this.initGrid(this.getCols(), this.getRows());
	}


	/**
	 * Randomize.
	 */
	public void randomize() {
		Random generator = new Random();
		for (int i = 0; i < this.getCols(); i++) {
			for (int j = 0; j < this.getRows(); j++) {
				this.grid[i][j].setState(generator.nextBoolean());
			}
		}

		this.setGeneration(0);
	}

	/**
	 * Inits the grid.
	 *
	 * @param cols the cols
	 * @param rows the rows
	 */
	private void initGrid(int cols, int rows) {
		this.setCols(cols);
		this.setRows(rows);
		this.setGeneration(0);

		this.grid = new Cell[this.getCols()][this.getRows()];

		for (int i = 0; i < this.getCols(); i++) {
			for (int j = 0; j < this.getRows(); j++) {
				this.grid[i][j] = new Cell(false);
			}
		}
	}

	public String toString() {
		String matrix = "";
		for (int i = 0; i < this.getRows(); i++) {
			for (int j = 0; j < this.getCols(); j++) {
				matrix = matrix+(this.grid[j][i].getState() ? Cell.ALIVE : Cell.DEAD);
			}
			matrix = matrix+"\n";
		}
		//matrix = matrix+"\n";
		return matrix;
	}

	/**
	 * Checks if is alive.
	 *
	 * @param row the row
	 * @param col the col
	 * @return true, if is alive
	 */
	public boolean isAlive(int row, int col) {
		return this.grid[col][row].getState();
	}
	
	/**
	 * Gets the cell.
	 *
	 * @param row the row
	 * @param col the col
	 * @return the cell
	 */
	public Cell getCell(int row, int col){
		return this.grid[col][row];
	}
	
	/**
	 * Load grid.
	 *
	 * @param file the file
	 */
	public boolean loadGrid(File file) {
		boolean ok = true;
		String line;
		Vector<String> fileGrid = new Vector<String>();
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(file));
			while ((line = fileReader.readLine()) != null)
				fileGrid.add(line);
			fileReader.close();
		} catch (Exception e) {
			ok = false;
		}

		this.setCols(fileGrid.get(0).length());
		this.setRows(fileGrid.size());
		this.setGeneration(0);

		this.grid = new Cell[this.getCols()][this.getRows()];

		for (int i = 0; i < this.getCols(); i++) {
			for (int j = 0; j < this.getRows(); j++) {
				String fieldValue = fileGrid.elementAt(j).substring(i, i + 1);

				//TODO replace with factory pattern (optional)

				boolean state = fieldValue.equals(Cell.ALIVE);
				this.grid[i][j] = new Cell(state);
			}
		}
		return false;
	}
	
	/**
	 * Save grid.
	 *
	 * @param file the file
	 */
	public boolean saveGrid(File file) {
		boolean ok = true;
		try {
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < this.getRows(); i++) {
				for (int j = 0; j < this.getCols(); j++) {
					fileWriter.write((this.grid[j][i].getState()) ? Cell.ALIVE : Cell.DEAD);
				}
				fileWriter.newLine();
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (Exception e) {
			ok = false;
		}
		return ok;
	}

	public boolean checkStable() {
		boolean same = false;

		//TODO implement pattern to find stable

		return same;
	}
}