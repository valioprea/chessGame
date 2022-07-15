import javax.swing.*;

public class Rook extends Piece{

    public Rook(String name, Icon icon, GameLogic gameLogic, boolean isWhite, Square[][] allSquares, int rowPosition, int columnPosition, boolean firstMove) {
        super(name, icon, gameLogic, isWhite, allSquares, rowPosition, columnPosition, firstMove);
    }

}
