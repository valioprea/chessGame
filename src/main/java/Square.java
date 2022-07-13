import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Square extends JPanel {

    private GameLogic gameLogic;
    public char xPosition;
    public int yPosition;

    public Piece piece;

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    private boolean containsPiece=false;

    public void setxPosition(char xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public boolean getContainsPiece() {
        return containsPiece;
    }

    public void setContainsPiece(boolean containsPiece) {
        this.containsPiece = containsPiece;
    }

    public char getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public Square(GameLogic gameLogic){
        this.gameLogic = gameLogic;
        eventHandler();
    }

    @Override
    public String toString() {
        return "Square{" +
                "xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                '}';
    }

    public void eventHandler() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                gameLogic.whyAmIPressing(getxPosition(),getyPosition());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                gameLogic.ungrabPiece();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}
