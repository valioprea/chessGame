import javax.swing.*;

public class Queen extends Piece{
    public Queen(String name, Icon icon, GameLogic gameLogic, boolean isWhite, Square[][] allSquares, int rowPosition, int columnPosition) {
        super(name, icon, gameLogic, isWhite, allSquares, rowPosition, columnPosition);
    }
}
