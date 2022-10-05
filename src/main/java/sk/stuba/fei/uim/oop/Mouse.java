package sk.stuba.fei.uim.oop;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse extends JPanel implements MouseListener{

    private final Board board;
    private int playableTiles;
    public Mouse(Board board){
        this.board = board;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Tile tile = ((Tile)e.getSource());
        if (tile.isPlayable()){
            tile.setBackground(tile.getBackground().brighter());
            tile.placeStone(board.getPlayer());
            tile.capturePieces(board.getPlayer());
            do{
                boolean playedPC = enemyTurn(board);
                setPlayableTiles(board);
                if (!playedPC && playableTiles == 0){
                    board.setWinner();
                    board.printWinner();
                    break;
                }
                board.repaint();
            }while (playableTiles == 0);
        }
    }

    private boolean enemyTurn(Board board){
        board.setPlayer(2);
        Tile bestTile = null;
        int maxStones = 0;
        for (int i = 0; i < board.getSizeBoard(); i++){
            for (int j = 0; j < board.getSizeBoard(); j++){
                board.get(i, j).setPlayable(board.getPlayer());
                board.get(i, j).setNumberOfStones(board.getPlayer());
                if (board.get(i, j).getNumberOfStones() > maxStones){
                    maxStones = board.get(i, j).getNumberOfStones();
                    bestTile = board.get(i, j);
                }
            }
        }
        board.repaint();
        if (bestTile != null){
            bestTile.placeStone(board.getPlayer());
            bestTile.capturePieces(board.getPlayer());
        }
        return (bestTile != null);
    }

    private void setPlayableTiles(Board board){
        board.setPlayer(1);
        playableTiles = 0;
        for (int i = 0; i < board.getSizeBoard(); i++){
            for (int j = 0; j < board.getSizeBoard(); j++){
                board.get(i, j).setPlayable(board.getPlayer());
                if (board.get(i,j).isPlayable()){
                    playableTiles++;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Tile tile = ((Tile)e.getSource());
        if (tile.isPlayable()){
             tile.setBackground(tile.getBackground().darker());
        }

    }
    @Override
    public void mouseExited(MouseEvent e) {
        Tile tile = ((Tile)e.getSource());
        if (tile.isPlayable()){
            tile.setBackground(tile.getBackground().brighter());
        }

    }
}