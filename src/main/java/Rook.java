import javax.swing.*;

public class Rook extends Piece{
    public Rook(String name, Icon icon, GameLogic gameLogic, boolean isWhite, Square[][] allSquares, int rowPosition, int columnPosition) {
        super(name, icon, gameLogic, isWhite, allSquares, rowPosition, columnPosition);
    }

    boolean rookHasMovedBeforeCastling;

    public boolean isRookHasMovedBeforeCastling() {
        return rookHasMovedBeforeCastling;
    }

    public void setRookHasMovedBeforeCastling(boolean rookHasMovedBeforeCastling) {
        this.rookHasMovedBeforeCastling = rookHasMovedBeforeCastling;
    }
}
