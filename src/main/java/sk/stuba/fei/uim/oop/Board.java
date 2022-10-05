package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel{

    private final ArrayList<ArrayList<Tile>> board = new ArrayList<>();
    private final int size;
    private int player;
    private int winner;
    private final JFrame frame;

    public Board(int size, JFrame frame){
        this.frame = frame;
        this.winner = 0;
        this.size = size;
        this.player = 1;
        this.setLayout(new GridLayout(size, size));
        for (int i = 0; i < size; i++) {
            this.board.add(new ArrayList<>());
            for (int j = 0; j < size; j++) {
                Tile tile = new Tile(i, j);
                if ((j + i) % 2 == 0) {
                    tile.setBackground(new Color(77, 161, 2));
                } else {
                    tile.setBackground(new Color(36, 86, 2));
                }
                if ((i == size/2-1 && j == size/2-1) || (i == size/2 && j == size/2)){
                    tile.setPlayer(1);
                }
                if ((i == size/2-1 && j == size/2) || (i == size/2 && j == size/2-1)){
                    tile.setPlayer(2);
                }
                tile.setPreferredSize(new Dimension(60, 60));
                this.board.get(i).add(tile);
                this.add(tile);
                tile.addMouseListener(new Mouse(this));
            }
        }
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                get(i,j).setNeighbours(this);
            }
        }
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                get(i, j).setPlayable(this.player);
                get(i, j).setNumberOfStones(this.player);
            }
        }
    }
    public Tile get(int row, int column){
        return board.get(row).get(column);
    }


    public int getSizeBoard(){
        return size;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void printWinner(){
        JLabel winnerLabel = new JLabel();
        if (winner == 1){
            winnerLabel.setText("Black player won!");
        }
        else if (winner == 2){
            winnerLabel.setText("White player won!");
        }
        else{
            winnerLabel.setText("Tie");
        }
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JDialog dialog = new JDialog(frame);
        dialog.add(winnerLabel);
        dialog.setSize(200,80);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    public void setWinner(){
        int playerOneStones = 0, playerTwoStones = 0;
        for (int i = 0; i < size; i ++){
            for (int j = 0; j < size; j++){
                if (get(i, j).getPlayer() == 1){
                    playerOneStones++;
                }
                else if (get(i, j).getPlayer() == 2){
                    playerTwoStones++;
                }
            }
        }
        if (playerOneStones > playerTwoStones){
            winner = 1;
        }
        else if (playerTwoStones > playerOneStones){
            winner = 2;
        }
    }
}
