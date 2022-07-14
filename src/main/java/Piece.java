import javax.swing.*;

public class Piece extends JLabel {

    public Square[][] allSquares;
    private String name;
    public GameLogic gameLogic;
    public boolean isWhite;
    public boolean blocksCheck;
    public int rowPosition;
    public int columnPosition;

    public Piece(String name, Icon icon, GameLogic gameLogic, boolean isWhite, Square[][] allSquares, int rowPosition, int columnPosition) {
        super(icon);
        this.name = name;
        this.gameLogic = gameLogic;
        this.isWhite = isWhite;
        this.allSquares = allSquares;
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
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

    @Override
    public String getName() {
        return this.name;
    }

}
