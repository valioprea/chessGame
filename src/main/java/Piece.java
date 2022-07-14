import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Piece extends JLabel {

    public Square[][] allSquares;
    private String name;
    public GameLogic gameLogic;
    public boolean isWhite;
    public boolean blocksCheck;
    public int squaresArrayColumnPosition;
    public int squaresArrayRowPosition;

    public Piece(String name, Icon icon, GameLogic gameLogic, boolean isWhite, Square[][] allSquares, int squaresArrayColumnPosition, int squaresArrayRowPosition) {
        super(icon);
        this.name = name;
        this.gameLogic = gameLogic;
        this.isWhite = isWhite;
        this.allSquares = allSquares;
        this.squaresArrayColumnPosition = squaresArrayColumnPosition;
        this.squaresArrayRowPosition = squaresArrayRowPosition;
//        this.eventHandler();
    }

    public boolean isWhite() {
        return isWhite;
    }

    public int getSquaresArrayColumnPosition() {
        return squaresArrayColumnPosition;
    }

    public int getSquaresArrayRowPosition() {
        return squaresArrayRowPosition;
    }

    public void setSquaresArrayColumnPosition(int squaresArrayColumnPosition) {
        this.squaresArrayColumnPosition = squaresArrayColumnPosition;
    }

    public void setSquaresArrayRowPosition(int squaresArrayRowPosition) {
        this.squaresArrayRowPosition = squaresArrayRowPosition;
    }

    @Override
    public String getName() {
        return this.name;
    }

//    private void eventHandler(){
//        this.addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.out.println("clicked");
//                System.out.println(e.getComponent());
//                gameLogic.grabPiece( (Piece)e.getComponent() );
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//
//            }
//        });
//    };
}
