import java.util.Objects;

// (ROW, COLUMN)
public class Position {
    int rowPosition;
    int colPosition;

    public Position(int rowPosition, int colPosition) {
        this.rowPosition = rowPosition;
        this.colPosition = colPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public int getColPosition() {
        return colPosition;
    }

    public void setColPosition(int colPosition) {
        this.colPosition = colPosition;
    }

    @Override
    public String toString() {
        return "Position{" +
                "rowPosition=" + rowPosition +
                ", colPosition=" + colPosition +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return rowPosition == position.rowPosition && colPosition == position.colPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowPosition, colPosition);
    }
}
