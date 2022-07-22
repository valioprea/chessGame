import javax.swing.*;

public class Piece{
    private final String name;
    private boolean canBeMoved;
    private final String color;
    public Position piecePosition;

    public Piece(String name,Position initialPosition, String color, boolean firstMove) {
        this.name = name;
        this.piecePosition = initialPosition;
        this.color = color;
        this.canBeMoved = firstMove;
    }

    public String getName() {
        return this.name;
    }
    public boolean isCanBeMoved() {
        return canBeMoved;
    }
    public void setCanBeMoved(boolean canBeMoved) {
        this.canBeMoved = canBeMoved;
    }
    public String getColor() {
        return this.color;
    }
    public Position getPiecePosition() {
        return piecePosition;
    }
    public void setPiecePosition(Position piecePosition) {
        this.piecePosition = piecePosition;
    }
    @Override
    public String toString() {
        return "Piece{" +
                "name='" + name + '\'' +
                ", piecePosition=" + piecePosition +
                '}';
    }

}
