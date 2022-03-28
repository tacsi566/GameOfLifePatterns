package gol;

/**
 * The Class GameOfLife.
 * This class is the command line starter for the GoL
 */
public class GameOfLife {
	
	/** The gol grid. */
	CellGrid golGrid;

	/**
	 * Inits the gol grid.
	 *
	 * @param cols the cols
	 * @param rows the rows
	 */
	private void initGOLGrid(int cols, int rows) {
		this.golGrid = new CellGrid(cols, rows);
		this.golGrid.randomize();
	}

	/**
	 * Start gol.
	 */
	private void startGOL() {
		this.golGrid.nextGeneration();
	}
	
	/**
	 * Prints the.
	 */
	private void print(){
		System.out.println(golGrid);
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		GameOfLife gol = new GameOfLife();
		gol.initGOLGrid(10, 10);
		for(int i= 0; i<= 10; i++){
			gol.startGOL();
			gol.print();
		}
	}
}
