import javax.swing.*;

public class Rook extends Piece{
    public Rook(String name, Icon icon, GameLogic gameLogic, boolean isWhite, Square[][] allSquares, int squaresArrayColumnPosition, int squaresArrayRowPosition) {
        super(name, icon, gameLogic, isWhite, allSquares, squaresArrayColumnPosition, squaresArrayRowPosition);
    }
}
