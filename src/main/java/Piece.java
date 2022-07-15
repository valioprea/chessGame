import javax.swing.*;

public class Piece extends JLabel {

    public boolean canBeMoved;
    public Square[][] allSquares;
    private String name;
    public GameLogic gameLogic;
    public boolean isWhite;
    public boolean blocksCheck;
    public int rowPosition;
    public int columnPosition;

    public Piece(String name, Icon icon, GameLogic gameLogic, boolean isWhite, Square[][] allSquares, int rowPosition, int columnPosition, boolean firstMove) {
        super(icon);
        this.name = name;
        this.gameLogic = gameLogic;
        this.isWhite = isWhite;
        this.allSquares = allSquares;
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
        this.canBeMoved = firstMove;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    public boolean isCanBeMoved() {
        return canBeMoved;
    }

    public void setCanBeMoved(boolean canBeMoved) {
        this.canBeMoved = canBeMoved;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "name='" + name + '\'' +
                ", rowPosition=" + rowPosition +
                ", columnPosition=" + columnPosition +
                '}';
    }
}
