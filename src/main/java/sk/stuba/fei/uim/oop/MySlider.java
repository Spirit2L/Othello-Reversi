package sk.stuba.fei.uim.oop;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MySlider extends JSlider implements ChangeListener {

    private MyFrame frame;
    private int previousValue;

    public MySlider(int orientation, int min, int max, int value) {
        super(orientation, min, max, value);
        previousValue = value;
        this.setMajorTickSpacing(2);
        setPaintLabels(true);
        setSnapToTicks(true);
        addChangeListener(this);

    }

    public void add(MyFrame frame){
        this.frame = frame;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int size = ((MySlider)e.getSource()).getValue();
        if (size != previousValue){
            previousValue = ((MySlider)e.getSource()).getValue();
            switch (((MySlider)e.getSource()).getValue()){
                case (6):
                case (8):
                case (10):
                case (12):
                    frame.remove(frame.getBoard());
                    frame.setBoard(new Board(size, frame));
                    frame.pack();
                    break;
            }
        }
    }
}
