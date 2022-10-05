package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyFrame extends JFrame implements ActionListener, KeyListener{

    private Board board;
    private final MySlider slider;

        public MyFrame() throws HeadlessException {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        JButton button = new JButton("RESET");
        button.addActionListener(this);
        slider = new MySlider(JSlider.HORIZONTAL, 6, 12, 6);
        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());
        menu.add(slider, BorderLayout.WEST);
        menu.add(button, BorderLayout.EAST);
        this.add(menu, BorderLayout.PAGE_START);
        board = new Board(slider.getValue(), this);
        this.add(board, BorderLayout.CENTER);
        slider.add(this);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setVisible(true);
        this.setResizable(false);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.remove(board);
        board = new Board(slider.getValue(), this);
        this.add(board);
        this.revalidate();
    }

    public Board getBoard(){
        return board;
    }

    public void setBoard(Board board){
        this.board = board;
        this.add(board);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'r'){
            this.remove(board);
            board = new Board(slider.getValue(), this);
            this.add(board);
            this.revalidate();
        }
        if (e.getKeyChar() == 27){
            this.dispose();
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
