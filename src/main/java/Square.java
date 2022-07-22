import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

public class Square {
    private int rowPosition; //(ROW, COLUMN)
    private int columnPosition; //(ROW, COLUMN)

    private Piece piece;

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void eliminatePiece(Piece piece) {
        this.piece = null;
    }

    @Override
    public String toString() {
        return "Square{" +
                "xPosition=" + rowPosition +
                ", yPosition=" + columnPosition +
                '}';
    }
}
