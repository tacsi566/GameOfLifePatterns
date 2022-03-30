package gol;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Link to repo https://github.com/tacsi566/GameOfLifePatterns.git

public class CellGridTest {

    private static CellGrid cellGrid;

    @BeforeAll
    public static void setUp() {
        cellGrid = new CellGrid(10, 10);
    }

    @BeforeEach
    public void reset() {
        cellGrid.reset();
    }

    @Test
    public void testNewCalculatedCellState_isAlive_1() {
        int column = 0;
        int row = 0;
        int aliveNeighbours = 3;
        boolean isAlive = cellGrid.calculateNewCellState(column, row, aliveNeighbours);

        assertTrue(isAlive);
    }

    @Test
    public void testNewCalculatedCellState_isAlive_2() {
        cellGrid.setCellState(1, 0, true);
        cellGrid.setCellState(1, 1, true);
        cellGrid.setCellState(0, 1, true);
        int column = 0;
        int row = 0;
        int aliveNeighbours = cellGrid.getAliveNeighboursCount(column, row);
        boolean isAlive = cellGrid.calculateNewCellState(column, row, aliveNeighbours);

        assertTrue(isAlive);
    }

    @Test
    public void testNewCalculatedCellState_isNotAlive_1() {
        int column = 0;
        int row = 0;
        int aliveNeighbours = 0;
        boolean isAlive = cellGrid.calculateNewCellState(column, row, aliveNeighbours);

        assertFalse(isAlive);
    }

    @Test
    public void testNewCalculatedCellState_isNotAlive_2() {
        cellGrid.setCellState(0, 0, true);
        cellGrid.setCellState(1, 0, true);
        cellGrid.setCellState(1, 1, true);
        cellGrid.setCellState(0, 2, true);
        int column = 0;
        int row = 1;
        int aliveNeighbours = cellGrid.getAliveNeighboursCount(column, row);
        boolean isAlive = cellGrid.calculateNewCellState(column, row, aliveNeighbours);

        assertFalse(isAlive);
    }

    @Test
    public void testSetState() {
        int column = 0;
        int row = 0;
        cellGrid.setCellState(column, row, true);

        boolean getSetCellState = cellGrid.getCellState(column, row);
        assertTrue(getSetCellState);
    }

    @Test
    public void testGetAliveNeighboursCount_1() {
        cellGrid.setCellState(1, 0, true);
        cellGrid.setCellState(1, 1, true);
        cellGrid.setCellState(0, 1, true);
        int row = 0;
        int column = 0;
        int actualCount = cellGrid.getAliveNeighboursCount(column, row);
        int expectedCount = 3;

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testGetAliveNeighboursCount_2() {
        cellGrid.setCellState(1, 1, true);
        cellGrid.setCellState(0, 0, true);
        cellGrid.setCellState(2, 0, true);
        cellGrid.setCellState(1, 2, true);
        cellGrid.setCellState(2, 2, true);
        int row = 1;
        int column = 1;
        int actualCount = cellGrid.getAliveNeighboursCount(column, row);
        int expectedCount = 4;

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testGetAliveNeighboursCount_3() {
        cellGrid.setCellState(0, 0, true);
        cellGrid.setCellState(1, 0, true);
        cellGrid.setCellState(0, 1, true);
        cellGrid.setCellState(1, 1, true);
        int row = 0;
        int column = 0;
        int actualCount = cellGrid.getAliveNeighboursCount(column, row);
        int expectedCount = 3;

        assertEquals(expectedCount, actualCount);
    }

}
