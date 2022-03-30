package gol.observer;


import gol.Cell;
import gol.Observer;

import javax.swing.*;
import java.awt.*;

/**
 * The Class MyJButton.
 */

// TODO erweitern sie den Java Button JButton und machen sie diesen zu einem Observer
// Java bietet dafür ein Interface
public class MyJButton extends JButton implements Observer {


    @Override
    public void update(Cell cell) {
        this.setText(cell.toString());
        this.setBackground(cell.toString().equals("X") ? Color.red : Color.LIGHT_GRAY);
        this.setForeground(cell.toString().equals("X") ? Color.red : Color.LIGHT_GRAY);
    }
}
