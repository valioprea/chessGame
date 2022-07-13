import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Square extends JPanel {

    private GameLogic gameLogic;
    private char xPosition;
    private int yPosition;

    private boolean containsPiece=false;

    public void setxPosition(char xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public boolean isContainsPiece() {
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
                System.out.println(e.getComponent());
                System.out.println(gameLogic.getPiece());
                add(gameLogic.getPiece());

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

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
