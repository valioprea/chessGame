import javax.swing.*;

public class Piece extends JLabel {

    public boolean canBeMoved;
    private String name;
    public GameLogic gameLogic;
    public boolean isWhite;
    public Position piecePosition;

    public Piece(String name,Position initialPosition, Icon icon, GameLogic gameLogic, boolean isWhite, boolean firstMove) {
        super(icon);
        this.name = name;
        this.piecePosition = initialPosition;
        this.gameLogic = gameLogic;
        this.isWhite = isWhite;
        this.canBeMoved = firstMove;
    }

    public boolean isWhite() {
        return isWhite;
    }
    public boolean isCanBeMoved() {
        return canBeMoved;
    }
    public void setCanBeMoved(boolean canBeMoved) {
        this.canBeMoved = canBeMoved;
    }

    public Position getPiecePosition() {
        return piecePosition;
    }

    public void setPiecePosition(Position piecePosition) {
        this.piecePosition = piecePosition;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "name='" + name + '\'' +
                ", piecePosition=" + piecePosition +
                '}';
    }
}
