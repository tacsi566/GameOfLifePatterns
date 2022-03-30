package gol;

/**
 * The Class Cell.
 */
public class Cell extends Subject{

    /** The state. */
    private boolean state;

    public static final String ALIVE = "X";
    public static final String DEAD  = "O";

    /**
     * Instantiates a new cell.
     */
    public Cell(){
    }

    /**
     * Instantiates a new cell.
     *
     * @param state the state
     */
    public Cell(boolean state){
        this.setState(state);
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public boolean getState(){
        return this.state;
    }

    /**
     * Sets the state.
     *
     * @param state the new state
     */
    public void setState(boolean state){
        this.state = state;
        notifyUpdate(this);
    }

    @Override
    public String toString() {
        return (state ? ALIVE : DEAD);
    }
}
