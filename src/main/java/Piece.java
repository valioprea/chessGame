import javax.swing.*;

public class Piece extends JLabel{
    private final String name;
    private boolean canBeMoved;

    private final String color;
    public Position piecePosition;

    public Piece(String name,Position initialPosition, Icon icon, String color, boolean firstMove) {
        super(icon);
        this.name = name;
        this.piecePosition = initialPosition;
        this.color = color;
        this.canBeMoved = firstMove;
    }
    @Override
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
