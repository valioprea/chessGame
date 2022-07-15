import javax.swing.*;

public class Bishop extends Piece{
    public Bishop(String name, Icon icon, GameLogic gameLogic, boolean isWhite, Square[][] allSquares, int rowPosition, int columnPosition) {
        super(name, icon, gameLogic, isWhite, allSquares, rowPosition, columnPosition);
    }
}
