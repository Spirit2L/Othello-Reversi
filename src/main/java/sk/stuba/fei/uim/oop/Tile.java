package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel{
    private final int row;
    private final int column;
    private int player;
    private boolean playable;
    private Tile[] neighbours;
    private int numberOfStones = 0;

    public Tile(int row, int column){
        super();
        this.row = row;
        this.column = column;
        this.player = 0;
        this.revalidate();
//        JLabel label = new JLabel(row + " " + column);
//        this.add(label);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (player == 1){
            g.setColor(Color.black);
            g.fillOval(0,0, getWidth(), getHeight());
        }
        if (player == 2){
            g.setColor(Color.white);
            g.fillOval(0,0, getWidth(), getHeight());
        }
        if (playable){
            g.setColor(Color.white);
            g.drawOval(0,0, getWidth(), getHeight());
        }
    }

    public void placeStone(int player){
        this.player = player;
        repaint();
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public Tile[] getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Board board) {
        int[] steps = {-1, 1, 0};
        int i = 0;
        neighbours = new Tile[8];
        for (int stepX : steps){
            for (int stepY : steps){
                if (i == 8) break;
                try{
                    neighbours[i] = board.get(getRow() + stepX, getColumn() + stepY);
                }
                catch (IndexOutOfBoundsException e) {
                    neighbours[i] = null;
                }
                i++;
            }
        }
    }

    public boolean isPlayable(){
        return this.playable;
    }

    public void setPlayable(int player){
        Tile currentTile = this;
        boolean sawOther = false;
        if (this.player != 0){
            playable = false;
            return;
        }
        for (int i = 0; i < 8; i++){
            if(lookAhead(currentTile, i, sawOther, player)){
                playable = true;
                return;
            }
        }
        playable = false;
    }

    public void capturePieces(int player){
        boolean sawOther = false;
        for (int i = 0; i < 8; i++){
            Tile currentTile = this;
            if(lookAhead(currentTile, i, sawOther, player)) {
                while (currentTile.neighbours[i].getPlayer() != player){
                    currentTile.neighbours[i].setPlayer(player);
                    currentTile = currentTile.neighbours[i];
                }
            }
        }
    }

    private boolean lookAhead(Tile tile, int direction, boolean sawOther, int player){
        if (tile.getNeighbours()[direction] == null || (tile.getNeighbours()[direction].getPlayer() == 0)){
            return false;
        }
        else if (tile.getNeighbours()[direction].getPlayer() == - player + 3){
            return lookAhead(tile.getNeighbours()[direction], direction, true, player);
        }
        else return sawOther;
    }

    public void setNumberOfStones(int player){
        numberOfStones = 0;
        if (playable){
            boolean sawOther = false;
            for (int i = 0; i < 8; i++){
                Tile currentTile = this;
                if(lookAhead(currentTile, i, sawOther, player)) {
                    while (currentTile.neighbours[i].getPlayer() != player){
                        numberOfStones++;
                        currentTile = currentTile.neighbours[i];
                    }
                }
            }
        }
    }

    public int getNumberOfStones() {
        return numberOfStones;
    }
}
